package projects.nyinyihtunlwin.zcar.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.models.MovieModel;
import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.mvp.views.MovieUpcomingView;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 2/9/2018.
 */

public class MovieUpcomingPresenter extends BasePresenter<MovieUpcomingView> {

    private Context mContext;

    public MovieUpcomingPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void onStart() {
        //check offline data storage
        MovieModel.getInstance().checkForOfflineCache(mContext,AppConstants.MOVIE_UPCOMING);

        List<MovieVO> movieList = MovieModel.getInstance().getUpcomingMovies();
        if (!movieList.isEmpty()) {
            mView.displayMoviesList(movieList);
        }
    }

    @Override
    public void onStop() {

    }

    public void onDataLoaded(Context context, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<MovieVO> movieList = new ArrayList<>();
            do {
                MovieVO newsVO = MovieVO.parseFromCursor(context, data);
                movieList.add(newsVO);
            } while (data.moveToNext());
            mView.displayMoviesList(movieList);
        }else{
            mView.showLoding();
            MovieModel.getInstance().startLoadingMovies(mContext, AppConstants.MOVIE_UPCOMING);
        }
    }

    public void onTapMovie(String movieId) {
        mView.navigateToMovieDetails(movieId);
    }

    public void onMovieListEndReached(Context context) {
        MovieModel.getInstance().loadMoreMovies(context, AppConstants.MOVIE_UPCOMING);
    }

    public void onForceRefresh(Context context) {
        MovieModel.getInstance().forceRefreshMovies(context, AppConstants.MOVIE_UPCOMING);
    }
}
