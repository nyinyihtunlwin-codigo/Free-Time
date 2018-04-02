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
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.events.ConnectionEvent;
import projects.nyinyihtunlwin.zcar.events.TvShowsEvents;
import projects.nyinyihtunlwin.zcar.network.TvShowDataAgentImpl;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.persistence.MovieDBHelper;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;
import projects.nyinyihtunlwin.zcar.utils.AppUtils;
import projects.nyinyihtunlwin.zcar.utils.ConfigUtils;

/**
 * Created by Dell on 2/5/2018.
 */

public class TvShowModel {

    private static TvShowModel objectInstance;

    private List<TvShowVO> mAiringTodayTvShows, mOnTheAirTvShows, mMostPopularTvShows, mTopRatedTvShows;


    private TvShowModel() {
        EventBus.getDefault().register(this);
        mAiringTodayTvShows = new ArrayList<>();
        mOnTheAirTvShows = new ArrayList<>();
        mMostPopularTvShows = new ArrayList<>();
        mTopRatedTvShows = new ArrayList<>();
    }

    public static TvShowModel getInstance() {
        if (objectInstance == null) {
            objectInstance = new TvShowModel();
        }
        return objectInstance;
    }


    public void startLoadingTvShows(Context context, String showType) {
        switch (showType) {
            case AppConstants.TV_SHOWS_AIRING_TODAY:
                mAiringTodayTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveTvShowsAiringTodayPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_ON_THE_AIR:
                mOnTheAirTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveTvShowsOnTheAirPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_MOST_POPULAR:
                mMostPopularTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveTvShowsMostPopularPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_TOP_RATED:
                mTopRatedTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveTvShowsTopRatedPageIndex(1);
                break;
        }
        if (AppUtils.getObjInstance().hasConnection()) {
            loadTvShows(context, showType);
        } else {
            EventBus.getDefault().post(new ConnectionEvent("No internet connection.", AppConstants.TYPE_START_LOADING_DATA));
        }
    }

    @Subscribe
    public void onTvAiringTodayTvShowsLoaded(TvShowsEvents.TvAiringTodayEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentTvShowsOnDb(AppConstants.TV_SHOWS_AIRING_TODAY, event);
        }
        mAiringTodayTvShows.addAll(event.getLoadedTvShows());
        ConfigUtils.getObjInstance().saveTvShowsAiringTodayPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.TV_SHOWS_AIRING_TODAY);
    }

    @Subscribe
    public void onTvMostPopularTvShowsLoaded(TvShowsEvents.TvMostPopularEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentTvShowsOnDb(AppConstants.TV_SHOWS_MOST_POPULAR, event);
        }
        mMostPopularTvShows.addAll(event.getLoadedTvShows());
        ConfigUtils.getObjInstance().saveTvShowsMostPopularPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.TV_SHOWS_MOST_POPULAR);
    }

    @Subscribe
    public void onTvOnTheAirShowsLoaded(TvShowsEvents.TvOnTheAirEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentTvShowsOnDb(AppConstants.TV_SHOWS_ON_THE_AIR, event);
        }
        mMostPopularTvShows.addAll(event.getLoadedTvShows());
        ConfigUtils.getObjInstance().saveTvShowsOnTheAirPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.TV_SHOWS_ON_THE_AIR);
    }

    @Subscribe
    public void onTvTopRatedTvShowsLoaded(TvShowsEvents.TvTopRatedEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentTvShowsOnDb(AppConstants.TV_SHOWS_TOP_RATED, event);
        }
        mMostPopularTvShows.addAll(event.getLoadedTvShows());
        ConfigUtils.getObjInstance().saveTvShowsTopRatedPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.TV_SHOWS_TOP_RATED);
    }

    private void clearRecentTvShowsOnDb(String screenName, TvShowsEvents.TvShowsDataLoadedEvent event) {
        int deletedRows = event.getContext().getContentResolver().delete(MovieContract.TvShowInScreenEntry.CONTENT_URI,
                MovieContract.TvShowInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{screenName});
        Log.e(ZCarApp.LOG_TAG, "Deleted Recent TvShows : " + String.valueOf(deletedRows));
    }


    private void saveDataForOfflineMode(TvShowsEvents.TvShowsDataLoadedEvent event, String screenName) {
        ContentValues[] tvShowCVS = new ContentValues[event.getLoadedTvShows().size()];
        List<ContentValues> genreCVList = new ArrayList<>();
        List<ContentValues> tvShowInScreenCVList = new ArrayList<>();

        for (int index = 0; index < tvShowCVS.length; index++) {
            TvShowVO tvShowVO = event.getLoadedTvShows().get(index);
            tvShowCVS[index] = tvShowVO.parseToContentValues();

            for (int genreId : tvShowVO.getGenreIds()) {
                ContentValues genreIdsInMovieCV = new ContentValues();
                genreIdsInMovieCV.put(MovieContract.TvShowGenreEntry.COLUMN_TV_SHOW_ID, tvShowVO.getId());
                genreIdsInMovieCV.put(MovieContract.TvShowGenreEntry.COLUMN_GENRE_ID, genreId);
                genreCVList.add(genreIdsInMovieCV);
            }

            ContentValues tvShowInScreenCV = new ContentValues();
            tvShowInScreenCV.put(MovieContract.TvShowInScreenEntry.COLUMN_TV_SHOWS_ID, tvShowVO.getId());
            tvShowInScreenCV.put(MovieContract.TvShowInScreenEntry.COLUMN_SCREEN, screenName);
            tvShowInScreenCVList.add(tvShowInScreenCV);

        }

        int insertedTvShowGenre = event.getContext().getContentResolver().bulkInsert(MovieContract.TvShowGenreEntry.CONTENT_URI,
                genreCVList.toArray(new ContentValues[0]));
        Log.d(ZCarApp.LOG_TAG, "Inserted Genres In Tv Shows :" + insertedTvShowGenre);

        int insertedTvShowInScreen = event.getContext().getContentResolver().bulkInsert(MovieContract.TvShowInScreenEntry.CONTENT_URI,
                tvShowInScreenCVList.toArray(new ContentValues[0]));
        Log.d(ZCarApp.LOG_TAG, "Inserted Tv Shows In Screen :" + insertedTvShowInScreen);

        int insertedRowCount = event.getContext().getContentResolver().bulkInsert(MovieContract.TvShowsEntry.CONTENT_URI, tvShowCVS);
        Log.d(ZCarApp.LOG_TAG, "Inserted row : " + insertedRowCount);

    }


    public void loadMoreTvShows(Context context, String showType) {
        if (AppUtils.getObjInstance().hasConnection()) {
            loadTvShows(context, showType);
        } else {
            EventBus.getDefault().post(new ConnectionEvent("No internet connection.", AppConstants.TYPE_lOAD_MORE_DATA));
        }
    }

    public void checkForOfflineCache(Context context, String screenType) {
        Cursor cursor = context.getContentResolver().query(MovieContract.TvShowInScreenEntry.CONTENT_URI,
                null,
                MovieContract.TvShowInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{screenType}, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                if (cursor.getCount() > 20) {
                    List<String> toDeleteMoviesIds = new ArrayList<>();
                    for (cursor.moveToPosition(20); !cursor.isAfterLast(); cursor.moveToNext()) {
                        String col21 = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowInScreenEntry.COLUMN_TV_SHOWS_ID));
                        Log.e(ZCarApp.LOG_TAG, col21 + "YOH");
                        toDeleteMoviesIds.add(col21);
                    }
                    String[] movieIdsToDelete = toDeleteMoviesIds.toArray(new String[0]);
                    String args = TextUtils.join(", ", movieIdsToDelete);
                    Log.e(ZCarApp.LOG_TAG, args);
                    MovieDBHelper dbHelper = new MovieDBHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL(String.format("DELETE FROM " + MovieContract.TvShowInScreenEntry.TABLE_NAME +
                            " WHERE " + MovieContract.TvShowInScreenEntry.COLUMN_TV_SHOWS_ID + " IN (%s);", args));
                }
                switch (screenType) {
                    case AppConstants.TV_SHOWS_AIRING_TODAY:
                        ConfigUtils.getObjInstance().saveTvShowsAiringTodayPageIndex(2);
                        break;
                    case AppConstants.TV_SHOWS_ON_THE_AIR:
                        ConfigUtils.getObjInstance().saveTvShowsOnTheAirPageIndex(2);
                        break;
                    case AppConstants.TV_SHOWS_MOST_POPULAR:
                        ConfigUtils.getObjInstance().saveTvShowsMostPopularPageIndex(2);
                        break;
                    case AppConstants.TV_SHOWS_TOP_RATED:
                        ConfigUtils.getObjInstance().saveTvShowsTopRatedPageIndex(2);
                        break;
                }
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////
    }

    public void forceRefreshTvShows(Context context, String showType) {
        switch (showType) {
            case AppConstants.TV_SHOWS_AIRING_TODAY:
                mAiringTodayTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveTvShowsAiringTodayPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_ON_THE_AIR:
                mOnTheAirTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveTvShowsOnTheAirPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_MOST_POPULAR:
                mMostPopularTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveTvShowsMostPopularPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_TOP_RATED:
                mTopRatedTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveTvShowsTopRatedPageIndex(1);
                break;
        }
        if (AppUtils.getObjInstance().hasConnection()) {
            loadTvShows(context, showType);
        } else {
            EventBus.getDefault().post(new ConnectionEvent("No internet connection.", AppConstants.TYPE_START_LOADING_DATA));
        }
    }

    private void loadTvShows(Context context, String showType) {
        switch (showType) {
            case AppConstants.TV_SHOWS_AIRING_TODAY:
                TvShowDataAgentImpl.getObjectInstance().loadTvAiringToday(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadTvShowsAiringTodayPageIndex(), "US", context);
                break;
            case AppConstants.TV_SHOWS_ON_THE_AIR:
                TvShowDataAgentImpl.getObjectInstance().loadTvOnTheAir(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadTvShowsOnTheAirPageIndex(), "US", context);
                break;
            case AppConstants.TV_SHOWS_MOST_POPULAR:
                TvShowDataAgentImpl.getObjectInstance().loadTvMostPopular(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadTvShowsMostPopularPageIndex(), "US", context);
                break;
            case AppConstants.TV_SHOWS_TOP_RATED:
                TvShowDataAgentImpl.getObjectInstance().loadTvTopRated(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadTvShowsTopRatedPageIndex(), "US", context);
                break;
        }
    }

    public List<TvShowVO> getAiringTodayTvShows() {
        return mAiringTodayTvShows;
    }

    public List<TvShowVO> getOnTheAirTvShows() {
        return mOnTheAirTvShows;
    }

    public List<TvShowVO> getMostPopularTvShows() {
        return mMostPopularTvShows;
    }

    public List<TvShowVO> getTopRatedTvShows() {
        return mTopRatedTvShows;
    }
}
