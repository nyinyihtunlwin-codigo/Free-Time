package projects.nyinyihtunlwin.zcar.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.ZCarApp;
import projects.nyinyihtunlwin.zcar.data.vo.GenreVO;
import projects.nyinyihtunlwin.zcar.data.vo.SearchResultVO;
import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.events.ConnectionEvent;
import projects.nyinyihtunlwin.zcar.events.MoviesiEvents;
import projects.nyinyihtunlwin.zcar.network.MovieDataAgentImpl;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.persistence.MovieDBHelper;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;
import projects.nyinyihtunlwin.zcar.utils.AppUtils;
import projects.nyinyihtunlwin.zcar.utils.ConfigUtils;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public class MovieModel {

    private static MovieModel objectInstance;


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

    public void startLoadingMovieCredits(String movieId) {
        MovieDataAgentImpl.getObjectInstance().loadMovieCredits(Integer.parseInt(movieId), AppConstants.API_KEY);
    }

    public void startLoadingSimilarMovies(String movieId) {
        MovieDataAgentImpl.getObjectInstance().loadSimilarMovies(Integer.parseInt(movieId), AppConstants.API_KEY);
    }

    public void startLoadingMovieGenres(Context context) {
        MovieDataAgentImpl.getObjectInstance().loadMovieGenres(AppConstants.API_KEY, context);
    }

    public void startLoadingMovies(Context context, String movieType) {
        switch (movieType) {
            case AppConstants.MOVIE_NOW_ON_CINEMA:
                mNowOnCinemaMovies = new ArrayList<>();
                ConfigUtils.getObjInstance().saveMovieNowOnCinemaPageIndex(1);
                break;
            case AppConstants.MOVIE_MOST_POPULAR:
                mMostPopularMovies = new ArrayList<>();
                ConfigUtils.getObjInstance().saveMovieMostPopularPageIndex(1);
                break;
            case AppConstants.MOVIE_UPCOMING:
                mUpcomingMovies = new ArrayList<>();
                ConfigUtils.getObjInstance().saveUpcomingPageIndex(1);
                break;
            case AppConstants.MOVIE_TOP_RATED:
                mTopRatedMovies = new ArrayList<>();
                ConfigUtils.getObjInstance().saveMovieTopRatedPageIndex(1);
                break;
        }
        if (AppUtils.getObjInstance().hasConnection()) {
            loadMovies(context, movieType);
        } else {
            EventBus.getDefault().post(new ConnectionEvent("No internet connection.", AppConstants.TYPE_START_LOADING_DATA));
        }
    }

    public void checkForOfflineCache(Context context, String screenType) {
        Cursor cursor = context.getContentResolver().query(MovieContract.MovieInScreenEntry.CONTENT_URI,
                null,
                MovieContract.MovieInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{screenType}, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                if (cursor.getCount() > 20) {
                    List<String> toDeleteMoviesIds = new ArrayList<>();
                    for (cursor.moveToPosition(20); !cursor.isAfterLast(); cursor.moveToNext()) {
                        String col21 = cursor.getString(cursor.getColumnIndex(MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID));
                        Log.e(ZCarApp.LOG_TAG, col21 + "YOH");
                        toDeleteMoviesIds.add(col21);
                    }
                    String[] movieIdsToDelete = toDeleteMoviesIds.toArray(new String[0]);
                    String args = TextUtils.join(", ", movieIdsToDelete);
                    Log.e(ZCarApp.LOG_TAG, args);
                    MovieDBHelper dbHelper = new MovieDBHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL(String.format("DELETE FROM " + MovieContract.MovieInScreenEntry.TABLE_NAME +
                            " WHERE " + MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID + " IN (%s);", args));
                }
                switch (screenType) {
                    case AppConstants.MOVIE_NOW_ON_CINEMA:
                        ConfigUtils.getObjInstance().saveMovieNowOnCinemaPageIndex(2);
                        break;
                    case AppConstants.MOVIE_UPCOMING:
                        ConfigUtils.getObjInstance().saveUpcomingPageIndex(2);
                        break;
                    case AppConstants.MOVIE_MOST_POPULAR:
                        ConfigUtils.getObjInstance().saveMovieMostPopularPageIndex(2);
                        break;
                    case AppConstants.MOVIE_TOP_RATED:
                        ConfigUtils.getObjInstance().saveMovieTopRatedPageIndex(2);
                        break;
                }
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////
    }

    @Subscribe
    public void onMovieGenresLoaded(MoviesiEvents.MovieGenresDataLoadedEvent event) {
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
    public void onNowOnCinemaMoviesLoaded(MoviesiEvents.NowOnCinemaMoviesDataLoadedEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentMoviesOnDb(AppConstants.MOVIE_NOW_ON_CINEMA, event);
        }
        mNowOnCinemaMovies.addAll(event.getLoadedMovies());
        ConfigUtils.getObjInstance().saveMovieNowOnCinemaPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.MOVIE_NOW_ON_CINEMA);
    }


    @Subscribe
    public void onPopularMoviesLoaded(MoviesiEvents.PoputlarMoviesDataLoadedEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentMoviesOnDb(AppConstants.MOVIE_MOST_POPULAR, event);
        }
        mMostPopularMovies.addAll(event.getLoadedMovies());
        ConfigUtils.getObjInstance().saveMovieMostPopularPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.MOVIE_MOST_POPULAR);
    }

    @Subscribe
    public void onUpcomingMoviesLoaded(MoviesiEvents.UpcomingMoviesDataLoadedEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentMoviesOnDb(AppConstants.MOVIE_UPCOMING, event);
        }
        mUpcomingMovies.addAll(event.getLoadedMovies());
        ConfigUtils.getObjInstance().saveUpcomingPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.MOVIE_UPCOMING);
    }

    @Subscribe
    public void onTopRatedMoviesLoaded(MoviesiEvents.TopRatedMoviesDataLoadedEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentMoviesOnDb(AppConstants.MOVIE_TOP_RATED, event);
        }
        mTopRatedMovies.addAll(event.getLoadedMovies());
        ConfigUtils.getObjInstance().saveMovieTopRatedPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.MOVIE_TOP_RATED);
    }

    private void clearRecentMoviesOnDb(String screenName, MoviesiEvents.MoviesDataLoadedEvent event) {
        int deletedRows = event.getContext().getContentResolver().delete(MovieContract.MovieInScreenEntry.CONTENT_URI,
                MovieContract.MovieInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{screenName});
        Log.e(ZCarApp.LOG_TAG, "Deleted Recent Movies : " + String.valueOf(deletedRows));
    }

    private void saveDataForOfflineMode(MoviesiEvents.MoviesDataLoadedEvent event, String screenName) {
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
        if (AppUtils.getObjInstance().hasConnection()) {
            loadMovies(context, movieType);
        } else {
            EventBus.getDefault().post(new ConnectionEvent("No internet connection.", AppConstants.TYPE_lOAD_MORE_DATA));
        }
    }


    public void forceRefreshMovies(Context context, String movieType) {
        switch (movieType) {
            case AppConstants.MOVIE_NOW_ON_CINEMA:
                mNowOnCinemaMovies = new ArrayList<>();
                ConfigUtils.getObjInstance().saveMovieNowOnCinemaPageIndex(1);
                break;
            case AppConstants.MOVIE_MOST_POPULAR:
                mMostPopularMovies = new ArrayList<>();
                ConfigUtils.getObjInstance().saveMovieMostPopularPageIndex(1);
                break;
            case AppConstants.MOVIE_UPCOMING:
                mUpcomingMovies = new ArrayList<>();
                ConfigUtils.getObjInstance().saveUpcomingPageIndex(1);
                break;
            case AppConstants.MOVIE_TOP_RATED:
                mTopRatedMovies = new ArrayList<>();
                ConfigUtils.getObjInstance().saveMovieTopRatedPageIndex(1);
                break;
        }
        if (AppUtils.getObjInstance().hasConnection()) {
            loadMovies(context, movieType);
        } else {
            EventBus.getDefault().post(new ConnectionEvent("No internet connection.", AppConstants.TYPE_START_LOADING_DATA));
        }

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
                MovieDataAgentImpl.getObjectInstance().loadNowOnCinemaMovies(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadMovieNowOnCinemaPageIndex(), "US", context);
                break;
            case AppConstants.MOVIE_UPCOMING:
                MovieDataAgentImpl.getObjectInstance().loadUpcomingMovies(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadUpcomingPageIndex(), "US", context);
                break;
            case AppConstants.MOVIE_MOST_POPULAR:
                MovieDataAgentImpl.getObjectInstance().loadPopularMovies(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadMovieMostPopularPageIndex(), "US", context);
                break;
            case AppConstants.MOVIE_TOP_RATED:
                MovieDataAgentImpl.getObjectInstance().loadTopRatedMovies(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadMovieTopRatedPageIndex(), "US", context);
                break;
        }
    }
}
