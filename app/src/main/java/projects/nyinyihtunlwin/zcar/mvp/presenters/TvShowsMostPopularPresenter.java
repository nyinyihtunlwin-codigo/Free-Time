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
import projects.nyinyihtunlwin.zcar.mvp.views.TvShowsMostPopularView;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

public class TvShowsMostPopularPresenter extends BasePresenter<TvShowsMostPopularView> {

    private Context mContext;

    public TvShowsMostPopularPresenter(Context context) {
        this.mContext = context;
        //  check offline data storage
        TvShowModel.getInstance().checkForOfflineCache(mContext, AppConstants.TV_SHOWS_MOST_POPULAR);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        List<TvShowVO> tvShowList = TvShowModel.getInstance().getMostPopularTvShows();
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
            TvShowModel.getInstance().startLoadingTvShows(mContext, AppConstants.TV_SHOWS_MOST_POPULAR);
        }
    }

    public void onTapTvShow(String tvShowId) {
        mView.navigateToTvShowDetails(tvShowId);
    }

    public void onTvShowListEndReached(Context context) {
        TvShowModel.getInstance().loadMoreTvShows(context, AppConstants.TV_SHOWS_MOST_POPULAR);
    }

    public void onForceRefresh(Context context) {
        TvShowModel.getInstance().forceRefreshTvShows(context, AppConstants.TV_SHOWS_MOST_POPULAR);
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
