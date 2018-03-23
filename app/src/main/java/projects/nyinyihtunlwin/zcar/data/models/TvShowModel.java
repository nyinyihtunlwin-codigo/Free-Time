package projects.nyinyihtunlwin.zcar.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.ZCarApp;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.events.TvShowsEvents;
import projects.nyinyihtunlwin.zcar.network.TvShowDataAgentImpl;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;
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
        loadTvShows(context, showType);
    }

    @Subscribe
    public void onTvAiringTodayTvShowsLoaded(TvShowsEvents.TvAiringTodayEvent event) {
        if (event.getLoadedPageIndex() == 1) {
            clearRecentTvShowsOnDb(AppConstants.TV_SHOWS_AIRING_TODAY, event);
        }
        mAiringTodayTvShows.addAll(event.getLoadedTvShows());
        ConfigUtils.getObjInstance().saveTvShowsAiringTodayPageIndex(event.getLoadedPageIndex() + 1);
        saveDataForOfflineMode(event, AppConstants.MOVIE_NOW_ON_CINEMA);
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
        loadTvShows(context, showType);
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

    public List<TvShowVO> getmAiringTodayTvShows() {
        return mAiringTodayTvShows;
    }

    public List<TvShowVO> getmOnTheAirTvShows() {
        return mOnTheAirTvShows;
    }

    public List<TvShowVO> getmMostPopularTvShows() {
        return mMostPopularTvShows;
    }

    public List<TvShowVO> getmTopRatedTvShows() {
        return mTopRatedTvShows;
    }
}
