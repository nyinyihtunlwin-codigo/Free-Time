package projects.nyinyihtunlwin.zcar.data.models;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.network.TvShowDataAgentImpl;
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
                ConfigUtils.getObjInstance().saveMovieNowOnCinemaPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_ON_THE_AIR:
                mOnTheAirTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveMovieMostPopularPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_MOST_POPULAR:
                mMostPopularTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveUpcomingPageIndex(1);
                break;
            case AppConstants.TV_SHOWS_TOP_RATED:
                mTopRatedTvShows = new ArrayList<>();
                ConfigUtils.getObjInstance().saveMovieTopRatedPageIndex(1);
                break;
        }
        loadTvShows(context, showType);
    }

    private void loadTvShows(Context context, String showType) {
        switch (showType) {
            case AppConstants.TV_SHOWS_AIRING_TODAY:
                TvShowDataAgentImpl.getObjectInstance().loadTvAiringToday(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadMovieNowOnCinemaPageIndex(), "US", context);
                break;
            case AppConstants.TV_SHOWS_ON_THE_AIR:
                TvShowDataAgentImpl.getObjectInstance().loadTvOnTheAir(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadUpcomingPageIndex(), "US", context);
                break;
            case AppConstants.TV_SHOWS_MOST_POPULAR:
                TvShowDataAgentImpl.getObjectInstance().loadTvMostPopular(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadMovieMostPopularPageIndex(), "US", context);
                break;
            case AppConstants.TV_SHOWS_TOP_RATED:
                TvShowDataAgentImpl.getObjectInstance().loadTvTopRated(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadMovieTopRatedPageIndex(), "US", context);
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
