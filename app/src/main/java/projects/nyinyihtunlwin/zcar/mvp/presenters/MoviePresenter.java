package projects.nyinyihtunlwin.zcar.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.models.MovieModel;
import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.events.ConnectionEvent;
import projects.nyinyihtunlwin.zcar.events.MoviesiEvents;
import projects.nyinyihtunlwin.zcar.mvp.views.MovieView;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

public class MoviePresenter extends BasePresenter<MovieView> {
    private Context mContext;
    private String mCurrentScreen = null;

    public MoviePresenter(Context context, int screenId) {
        this.mContext = context;
        //check offline data storage
        switch (screenId) {
            case 0:
                mCurrentScreen = AppConstants.MOVIE_NOW_ON_CINEMA;
                break;
            case 1:
                mCurrentScreen = AppConstants.MOVIE_UPCOMING;
                break;
            case 2:
                mCurrentScreen = AppConstants.MOVIE_MOST_POPULAR;
                break;
            case 3:
                mCurrentScreen = AppConstants.MOVIE_TOP_RATED;
                break;
        }
        MovieModel.getInstance().checkForOfflineCache(mContext, mCurrentScreen);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        List<MovieVO> movieList = null;
        if (mCurrentScreen != null) {
            switch (mCurrentScreen) {
                case AppConstants.MOVIE_NOW_ON_CINEMA:
                    movieList = MovieModel.getInstance().getNowOnCinemaMovies();
                    break;
                case AppConstants.MOVIE_UPCOMING:
                    movieList = MovieModel.getInstance().getUpcomingMovies();
                    break;
                case AppConstants.MOVIE_MOST_POPULAR:
                    movieList = MovieModel.getInstance().getMostPopularMovies();
                    break;
                case AppConstants.MOVIE_TOP_RATED:
                    movieList = MovieModel.getInstance().getTopRatedMovies();
                    break;
            }
            if (movieList != null && !movieList.isEmpty()) {
                mView.displayMoviesList(movieList);
            }
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    public void onDataLoaded(Context context, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<MovieVO> movieList = new ArrayList<>();
            do {
                MovieVO newsVO = MovieVO.parseFromCursor(context, data);
                movieList.add(newsVO);
            } while (data.moveToNext());
            mView.displayMoviesList(movieList);
        } else {
            mView.showLoding();
            MovieModel.getInstance().startLoadingMovies(mContext, mCurrentScreen);
        }
    }

    public void onTapMovie(String movieId) {
        mView.navigateToMovieDetails(movieId);
    }

    public void onMovieListEndReached(Context context) {
        MovieModel.getInstance().loadMoreMovies(context, mCurrentScreen);
    }

    public void onForceRefresh(Context context) {
        MovieModel.getInstance().forceRefreshMovies(context, mCurrentScreen);
    }


    @Subscribe
    public void onConnectionError(ConnectionEvent event) {
        mView.onConnectionError(event.getMessage(), event.getType());
    }

    @Subscribe
    public void onApiError(MoviesiEvents.ErrorInvokingAPIEvent event) {
        mView.onApiError(event.getErrorMsg());
    }
}
