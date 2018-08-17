package projects.nyinyihtunlwin.zcar.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.models.TvShowModel;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.events.ConnectionEvent;
import projects.nyinyihtunlwin.zcar.events.MoviesiEvents;
import projects.nyinyihtunlwin.zcar.mvp.views.TvShowView;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

public class TvShowPresenter extends BasePresenter<TvShowView> {

    private Context mContext;
    private String mScreenType = null;

    public TvShowPresenter(Context context, int screenId) {
        this.mContext = context;
        //  check offline data storage
        switch (screenId) {
            case 0:
                mScreenType = AppConstants.TV_SHOWS_AIRING_TODAY;
                break;
            case 1:
                mScreenType = AppConstants.TV_SHOWS_ON_THE_AIR;
                break;
            case 2:
                mScreenType = AppConstants.TV_SHOWS_MOST_POPULAR;
                break;
            case 3:
                mScreenType = AppConstants.TV_SHOWS_TOP_RATED;
                break;
        }
        TvShowModel.getInstance().checkForOfflineCache(mContext, mScreenType);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        List<TvShowVO> tvShowList = TvShowModel.getInstance().getAiringTodayTvShows();
        if (!tvShowList.isEmpty()) {
            mView.displayTvShowList(tvShowList);
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    public void onDataLoaded(Context context, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<TvShowVO> tvShowList = new ArrayList<>();
            do {
                TvShowVO tvShowVO = TvShowVO.parseFromCursor(context, data);
                tvShowList.add(tvShowVO);
            } while (data.moveToNext());
            mView.displayTvShowList(tvShowList);
        } else {
            mView.showLoding();
            TvShowModel.getInstance().startLoadingTvShows(mContext,mScreenType);
        }
    }

    public void onTapTvShow(String tvShowId) {
        mView.navigateToTvShowDetails(tvShowId);
    }

    public void onTvShowListEndReached(Context context) {
        TvShowModel.getInstance().loadMoreTvShows(context,mScreenType);
    }

    public void onForceRefresh(Context context) {
        TvShowModel.getInstance().forceRefreshTvShows(context,mScreenType);
    }

    @Subscribe
    public void onConnectionError(ConnectionEvent event) {
        mView.onConnectionError(event.getMessage(), event.getType());
    }

    @Subscribe
    public void onApiError(MoviesiEvents.ErrorInvokingAPIEvent event) {
        mView.onApiError(event.getErrorMsg());
    }
}
