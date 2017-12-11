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

    public void startLoadingPopularMovies() {
        MovieDataAgentImpl.getObjectInstance().loadPopularMovies(moviePageIndex, AppConstants.ACCESS_TOKEN);
    }

    @Subscribe
    public void onMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mMovies.addAll(event.getLoadedMovies());
        moviePageIndex = event.getLoadedPageIndex() + 1;
    }

    public void loadMoreMovies() {
        MovieDataAgentImpl.getObjectInstance().loadPopularMovies(moviePageIndex, AppConstants.ACCESS_TOKEN);
    }

    public void forceRefreshMovies() {
        mMovies=new ArrayList<>();
        moviePageIndex = 1;
        startLoadingPopularMovies();
    }

    public List<MovieVO> getMovies() {
        return mMovies;
    }
}
