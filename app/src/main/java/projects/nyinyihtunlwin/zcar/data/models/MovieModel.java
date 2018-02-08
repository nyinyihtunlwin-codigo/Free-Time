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

    public void startLoadingMovieDetails(String movieId) {
        MovieDataAgentImpl.getObjectInstance().loadMovieDetails(Integer.parseInt(movieId), AppConstants.API_KEY);
    }

    public void startLoadingMovieTrailers(String movieId) {
        MovieDataAgentImpl.getObjectInstance().loadMovieTrailers(Integer.parseInt(movieId), AppConstants.API_KEY);
    }

    public void startLoadingMovieReviews(String movieId) {
        MovieDataAgentImpl.getObjectInstance().loadMovieReviews(Integer.parseInt(movieId), AppConstants.API_KEY);
    }

    public void startLoadingMovieCredits(String movieId){
        MovieDataAgentImpl.getObjectInstance().loadMovieCredits(Integer.parseInt(movieId),AppConstants.API_KEY);
    }

    public void startLoadingMovieGenres(Context context) {
        MovieDataAgentImpl.getObjectInstance().loadMovieGenres(AppConstants.API_KEY, context);
    }

    public void startLoadingMovies(Context context, String movieType) {
        loadMovies(context, movieType);
    }

    @Subscribe
    public void onMovieGenresLoaded(RestApiEvents.MovieGenresDataLoadedEvent event) {
        mMovieGenres.addAll(event.getGenres());
        Log.e(ZCarApp.LOG_TAG, String.valueOf(mMovieGenres.size()));
        ContentValues[] genreCVs = new ContentValues[event.getGenres().size()];
        for (int index = 0; index < genreCVs.length; index++) {
            GenreVO genreVO = event.getGenres().get(index);
            genreCVs[index] = genreVO.parseToContentValues();
        }
        int insertedRowCount = event.getContext().getContentResolver().bulkInsert(MovieContract.GenreEntry.CONTENT_URI, genreCVs);
        Log.d(ZCarApp.LOG_TAG, "Inserted genres : " + insertedRowCount);
    }

    @Subscribe
    public void onNowOnCinemaMoviesLoaded(RestApiEvents.NowOnCinemaMoviesDataLoadedEvent event) {
        mNowOnCinemaMovies.addAll(event.getLoadedMovies());
        mNowOnCinemaPageIndex = event.getLoadedPageIndex() + 1;
        saveDataForOfflineMode(event, AppConstants.MOVIE_NOW_ON_CINEMA);
    }

    @Subscribe
    public void onPopularMoviesLoaded(RestApiEvents.PoputlarMoviesDataLoadedEvent event) {
        mMostPopularMovies.addAll(event.getLoadedMovies());
        mMostPopularPageIndex = event.getLoadedPageIndex() + 1;
        saveDataForOfflineMode(event, AppConstants.MOVIE_MOST_POPULAR);
    }

    @Subscribe
    public void onUpcomingMoviesLoaded(RestApiEvents.UpcomingMoviesDataLoadedEvent event) {
        mUpcomingMovies.addAll(event.getLoadedMovies());
        mUpcomingPageIndex = event.getLoadedPageIndex() + 1;
        saveDataForOfflineMode(event, AppConstants.MOVIE_UPCOMING);
    }

    @Subscribe
    public void onTopRatedMoviesLoaded(RestApiEvents.TopRatedMoviesDataLoadedEvent event) {
        mTopRatedMovies.addAll(event.getLoadedMovies());
        mTopRatedPageIndex = event.getLoadedPageIndex() + 1;
        saveDataForOfflineMode(event, AppConstants.MOVIE_TOP_RATED);
    }

    private void saveDataForOfflineMode(RestApiEvents.MoviesDataLoadedEvent event, String screenName) {
        ContentValues[] movieCVS = new ContentValues[event.getLoadedMovies().size()];
        List<ContentValues> genreCVList = new ArrayList<>();
        List<ContentValues> movieInScreenCVList = new ArrayList<>();

        for (int index = 0; index < movieCVS.length; index++) {
            MovieVO movieVO = event.getLoadedMovies().get(index);
            movieCVS[index] = movieVO.parseToContentValues();

            for (int genreId : movieVO.getGenreIds()) {
                ContentValues genreIdsInMovieCV = new ContentValues();
                genreIdsInMovieCV.put(MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID, movieVO.getId());
                genreIdsInMovieCV.put(MovieContract.MovieGenreEntry.COLUMN_GENRE_ID, genreId);
                genreCVList.add(genreIdsInMovieCV);
            }

            ContentValues movieInScreenCV = new ContentValues();
            movieInScreenCV.put(MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID, movieVO.getId());
            movieInScreenCV.put(MovieContract.MovieInScreenEntry.COLUMN_SCREEN, screenName);
            movieInScreenCVList.add(movieInScreenCV);

        }

        int insertedMovieGenre = event.getContext().getContentResolver().bulkInsert(MovieContract.MovieGenreEntry.CONTENT_URI,
                genreCVList.toArray(new ContentValues[0]));
        Log.d(ZCarApp.LOG_TAG, "Inserted Genres In Movies :" + insertedMovieGenre);

        int insertedMovieInScreen = event.getContext().getContentResolver().bulkInsert(MovieContract.MovieInScreenEntry.CONTENT_URI,
                movieInScreenCVList.toArray(new ContentValues[0]));
        Log.d(ZCarApp.LOG_TAG, "Inserted Movies In Screen :" + insertedMovieInScreen);

        int insertedRowCount = event.getContext().getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI, movieCVS);
        Log.d(ZCarApp.LOG_TAG, "Inserted row : " + insertedRowCount);
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
