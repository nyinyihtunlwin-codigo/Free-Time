package projects.nyinyihtunlwin.zcar.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.ZCarApp;
import projects.nyinyihtunlwin.zcar.data.vo.GenreVO;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.events.RestApiEvents;
import projects.nyinyihtunlwin.zcar.network.MovieDataAgentImpl;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public class MovieModel {

    private static MovieModel objectInstance;
    private int mNowOnCinemaPageIndex = 1;
    private int mMostPopularPageIndex = 1;
    private int mUpcomingPageIndex = 1;
    private int mTopRatedPageIndex = 1;


    private List<MovieVO> mNowOnCinemaMovies, mMostPopularMovies, mUpcomingMovies, mTopRatedMovies;
    private List<GenreVO> mMovieGenres;


    private MovieModel() {
        EventBus.getDefault().register(this);
        mNowOnCinemaMovies = new ArrayList<>();
        mMostPopularMovies = new ArrayList<>();
        mUpcomingMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();
        mMovieGenres = new ArrayList<>();
    }

    public static MovieModel getInstance() {
        if (objectInstance == null) {
            objectInstance = new MovieModel();
        }
        return objectInstance;
    }

    public void startLoadingMovieGenres() {
        MovieDataAgentImpl.getObjectInstance().loadMovieGenres(AppConstants.API_KEY);
    }

    public void startLoadingMovies(Context context, String movieType) {
        loadMovies(context, movieType);
    }

    @Subscribe
    public void onMovieGenresLoaded(RestApiEvents.MovieGenresDataLoadedEvent event) {
        mMovieGenres.addAll(event.getGenres());
        Log.e(ZCarApp.LOG_TAG, String.valueOf(mMovieGenres.size()));
    }

    @Subscribe
    public void onNowOnCinemaMoviesLoaded(RestApiEvents.NowOnCinemaMoviesDataLoadedEvent event) {
        mNowOnCinemaMovies.addAll(event.getLoadedMovies());
        mNowOnCinemaPageIndex = event.getLoadedPageIndex() + 1;

        ContentValues[] movieCVS = new ContentValues[event.getLoadedMovies().size()];
        for (int index = 0; index < movieCVS.length; index++) {
            movieCVS[index] = event.getLoadedMovies().get(index).parseToContentValues();
        }

        int insertedRowCount = event.getContext().getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI, movieCVS);
        Log.d(ZCarApp.LOG_TAG, "Inserted row : " + insertedRowCount);
    }

    @Subscribe
    public void onPopularMoviesLoaded(RestApiEvents.PoputlarMoviesDataLoadedEvent event) {
        mMostPopularMovies.addAll(event.getLoadedMovies());
        mMostPopularPageIndex = event.getLoadedPageIndex() + 1;
    }

    @Subscribe
    public void onUpcomingMoviesLoaded(RestApiEvents.UpcomingMoviesDataLoadedEvent event) {
        mUpcomingMovies.addAll(event.getLoadedMovies());
        mUpcomingPageIndex = event.getLoadedPageIndex() + 1;
    }

    @Subscribe
    public void onTopRatedMoviesLoaded(RestApiEvents.TopRatedMoviesDataLoadedEvent event) {
        mTopRatedMovies.addAll(event.getLoadedMovies());
        mTopRatedPageIndex = event.getLoadedPageIndex() + 1;
    }


    public void loadMoreMovies(Context context, String movieType) {
        loadMovies(context, movieType);
    }


    public void forceRefreshMovies(Context context, String movieType) {
        switch (movieType) {
            case AppConstants.MOVIE_NOW_ON_CINEMA:
                mNowOnCinemaMovies = new ArrayList<>();
                mNowOnCinemaPageIndex = 1;
                break;
            case AppConstants.MOVIE_MOST_POPULAR:
                mMostPopularMovies = new ArrayList<>();
                mMostPopularPageIndex = 1;
                break;
            case AppConstants.MOVIE_UPCOMING:
                mUpcomingMovies = new ArrayList<>();
                mUpcomingPageIndex = 1;
                break;
            case AppConstants.MOVIE_TOP_RATED:
                mTopRatedMovies = new ArrayList<>();
                mTopRatedPageIndex = 1;
                break;
        }
        loadMovies(context, movieType);
    }


    public List<MovieVO> getNowOnCinemaMovies() {
        return mNowOnCinemaMovies;
    }

    public List<MovieVO> getMostPopularMovies() {
        return mMostPopularMovies;
    }

    public List<MovieVO> getUpcomingMovies() {
        return mUpcomingMovies;
    }

    public List<MovieVO> getTopRatedMovies() {
        return mTopRatedMovies;
    }

    private void loadMovies(Context context, String movieType) {
        switch (movieType) {
            case AppConstants.MOVIE_NOW_ON_CINEMA:
                MovieDataAgentImpl.getObjectInstance().loadNowOnCinemaMovies(AppConstants.API_KEY, mNowOnCinemaPageIndex, "US", context);
                break;
            case AppConstants.MOVIE_UPCOMING:
                MovieDataAgentImpl.getObjectInstance().loadUpcomingMovies(AppConstants.API_KEY, mUpcomingPageIndex, "US", context);
                break;
            case AppConstants.MOVIE_MOST_POPULAR:
                MovieDataAgentImpl.getObjectInstance().loadPopularMovies(AppConstants.API_KEY, mMostPopularPageIndex, "US", context);
                break;
            case AppConstants.MOVIE_TOP_RATED:
                MovieDataAgentImpl.getObjectInstance().loadTopRatedMovies(AppConstants.API_KEY, mTopRatedPageIndex, "US", context);
                break;
        }
    }
}
