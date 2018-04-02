package projects.nyinyihtunlwin.zcar.mvp.presenters;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.models.MovieModel;
import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.events.ConnectionEvent;
import projects.nyinyihtunlwin.zcar.events.MoviesiEvents;
import projects.nyinyihtunlwin.zcar.mvp.views.MovieNowOnCinemaView;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 2/9/2018.
 */

public class MovieNowOnCinemaPresenter extends BasePresenter<MovieNowOnCinemaView> {

    private Context mContext;

    public MovieNowOnCinemaPresenter(Context context) {
        this.mContext = context;
        // check offline data storage
        MovieModel.getInstance().checkForOfflineCache(mContext, AppConstants.MOVIE_NOW_ON_CINEMA);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        List<MovieVO> movieList = MovieModel.getInstance().getNowOnCinemaMovies();
        if (!movieList.isEmpty()) {
            mView.displayMoviesList(movieList);
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
            MovieModel.getInstance().startLoadingMovies(mContext, AppConstants.MOVIE_NOW_ON_CINEMA);
        }
    }

    public void onTapMovie(String movieId) {
        mView.navigateToMovieDetails(movieId);
    }

    public void onMovieListEndReached(Context context) {
        MovieModel.getInstance().loadMoreMovies(context, AppConstants.MOVIE_NOW_ON_CINEMA);
    }

    public void onForceRefresh(Context context) {
        MovieModel.getInstance().forceRefreshMovies(context, AppConstants.MOVIE_NOW_ON_CINEMA);
    }

    @Subscribe
    public void onConnectionError(ConnectionEvent event) {
        mView.onConnectionError(event.getMessage(),event.getType());
    }

    @Subscribe
    public void onApiError(MoviesiEvents.ErrorInvokingAPIEvent event) {
        mView.onApiError(event.getErrorMsg());
    }

}
