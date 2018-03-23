package projects.nyinyihtunlwin.zcar.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.models.MovieModel;
import projects.nyinyihtunlwin.zcar.data.models.TvShowModel;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.mvp.views.TvShowsAiringTodayView;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 3/6/2018.
 */

public class TvShowsAiringTodayPresenter extends BasePresenter<TvShowsAiringTodayView> {

    private Context mContext;

    public TvShowsAiringTodayPresenter(Context context) {
        this.mContext = context;
        // check offline data storage
        //   TvShowModel.getInstance().checkForOfflineCache(mContext, AppConstants.TV_SHOWS_AIRING_TODAY);
    }

    @Override
    public void onStart() {
        List<TvShowVO> tvShowList = TvShowModel.getInstance().getmAiringTodayTvShows();
        if (!tvShowList.isEmpty()) {
            mView.displayTvShowList(tvShowList);
        }
    }

    @Override
    public void onStop() {

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
            TvShowModel.getInstance().startLoadingTvShows(mContext, AppConstants.TV_SHOWS_AIRING_TODAY);
        }
    }

    public void onTapTvShow(String tvShowId) {
        mView.navigateToTvShowDetails(tvShowId);
    }

    public void onTvShowListEndReached(Context context) {
        //  TvShowModel.getInstance().loadMoreMovies(context, AppConstants.MOVIE_NOW_ON_CINEMA);
    }

    public void onForceRefresh(Context context) {
        TvShowModel.getInstance().forceRefreshTvShows(context, AppConstants.TV_SHOWS_AIRING_TODAY);
    }
}
