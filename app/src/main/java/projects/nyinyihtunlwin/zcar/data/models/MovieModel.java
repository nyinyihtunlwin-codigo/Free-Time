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
    private int moviePageIndex = 1;


    private List<MovieVO> mMovies;


    private MovieModel() {
        EventBus.getDefault().register(this);
        mMovies = new ArrayList<>();
    }

    public static MovieModel getInstance() {
        if (objectInstance == null) {
            objectInstance = new MovieModel();
        }
        return objectInstance;
    }

    public void startLoadingMovies(String movieType) {
        switch (movieType) {
            case AppConstants.MOVIE_NOW_ON_CINEMA:
                MovieDataAgentImpl.getObjectInstance().loadNowOnCinemaMovies(AppConstants.API_KEY, moviePageIndex, "US");
                break;
            case AppConstants.MOVIE_UPCOMING:
                MovieDataAgentImpl.getObjectInstance().loadUpcomingMovies(AppConstants.API_KEY, moviePageIndex, "US");
                break;
            case AppConstants.MOVIE_MOST_POPULAR:
                MovieDataAgentImpl.getObjectInstance().loadPopularMovies(AppConstants.API_KEY, moviePageIndex, "US");
                break;
            case AppConstants.MOVIE_TOP_RATED:
                MovieDataAgentImpl.getObjectInstance().loadTopRatedMovies(AppConstants.API_KEY, moviePageIndex, "US");
                break;
        }

    }

    @Subscribe
    public void onMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mMovies.addAll(event.getLoadedMovies());
        moviePageIndex = event.getLoadedPageIndex() + 1;
    }

    public void loadMoreMovies() {
        MovieDataAgentImpl.getObjectInstance().loadTopRatedMovies(AppConstants.API_KEY, moviePageIndex, "US");
    }

    public void forceRefreshMovies() {
        mMovies = new ArrayList<>();
        moviePageIndex = 1;
        startLoadingMovies(AppConstants.MOVIE_MOST_POPULAR);
    }

    public List<MovieVO> getMovies() {
        return mMovies;
    }
}
