package projects.nyinyihtunlwin.zcar.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.models.MovieModel;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.mvp.views.MovieTopRatedView;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 2/9/2018.
 */

public class MovieTopRatedPresenter extends BasePresenter<MovieTopRatedView> {

    private Context mContext;

    public MovieTopRatedPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void onStart() {
        List<MovieVO> movieList = MovieModel.getInstance().getTopRatedMovies();
        if (!movieList.isEmpty()) {
            mView.displayMoviesList(movieList);
        } else {
            mView.showLoding();
        //    MovieModel.getInstance().startLoadingMovies(mContext, AppConstants.MOVIE_TOP_RATED);
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
        }
    }

    public void onTapMovie(String movieId) {
        mView.navigateToMovieDetails(movieId);
    }

    public void onMovieListEndReached(Context context) {
        MovieModel.getInstance().loadMoreMovies(context, AppConstants.MOVIE_TOP_RATED);
    }

    public void onForceRefresh(Context context) {
        MovieModel.getInstance().forceRefreshMovies(context, AppConstants.MOVIE_TOP_RATED);
    }
}
