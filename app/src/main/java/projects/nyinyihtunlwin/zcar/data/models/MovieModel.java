package projects.nyinyihtunlwin.zcar.data.models;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.events.RestApiEvents;
import projects.nyinyihtunlwin.zcar.network.MovieDataAgentImpl;
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


    private MovieModel() {
        EventBus.getDefault().register(this);
        mNowOnCinemaMovies = new ArrayList<>();
        mMostPopularMovies = new ArrayList<>();
        mUpcomingMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();
    }

    public static MovieModel getInstance() {
        if (objectInstance == null) {
            objectInstance = new MovieModel();
        }
        return objectInstance;
    }

    public void startLoadingMovies(String movieType) {
        loadMovies(movieType);
    }

    @Subscribe
    public void onMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        switch (event.getMoviesForScreen()) {
            case AppConstants.MOVIE_NOW_ON_CINEMA:
                mNowOnCinemaMovies.addAll(event.getLoadedMovies());
                mNowOnCinemaPageIndex = event.getLoadedPageIndex() + 1;
                break;
            case AppConstants.MOVIE_MOST_POPULAR:
                mMostPopularMovies.addAll(event.getLoadedMovies());
                mMostPopularPageIndex = event.getLoadedPageIndex() + 1;
                break;
            case AppConstants.MOVIE_UPCOMING:
                mUpcomingMovies.addAll(event.getLoadedMovies());
                mUpcomingPageIndex = event.getLoadedPageIndex() + 1;
                break;
            case AppConstants.MOVIE_TOP_RATED:
                mTopRatedMovies.addAll(event.getLoadedMovies());
                mTopRatedPageIndex = event.getLoadedPageIndex() + 1;
                break;
        }
    }

    public void loadMoreMovies(String movieType) {
        loadMovies(movieType);
    }


    public void forceRefreshMovies(String movieType) {
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
        loadMovies(movieType);
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

    private void loadMovies(String movieType) {
        switch (movieType) {
            case AppConstants.MOVIE_NOW_ON_CINEMA:
                MovieDataAgentImpl.getObjectInstance().loadNowOnCinemaMovies(AppConstants.API_KEY, mNowOnCinemaPageIndex, "US");
                break;
            case AppConstants.MOVIE_UPCOMING:
                MovieDataAgentImpl.getObjectInstance().loadUpcomingMovies(AppConstants.API_KEY, mUpcomingPageIndex, "US");
                break;
            case AppConstants.MOVIE_MOST_POPULAR:
                MovieDataAgentImpl.getObjectInstance().loadPopularMovies(AppConstants.API_KEY, mMostPopularPageIndex, "US");
                break;
            case AppConstants.MOVIE_TOP_RATED:
                MovieDataAgentImpl.getObjectInstance().loadTopRatedMovies(AppConstants.API_KEY, mTopRatedPageIndex, "US");
                break;
        }
    }
}
