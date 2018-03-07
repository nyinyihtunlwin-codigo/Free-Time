package projects.nyinyihtunlwin.zcar.mvp.presenters;

import android.content.Context;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.models.TvShowModel;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.mvp.views.TvShowsAiringTodayView;

/**
 * Created by Dell on 3/6/2018.
 */

public class TvShowsAiringTodayPresenter extends BasePresenter<TvShowsAiringTodayView> {

    private Context mContext;

    public TvShowsAiringTodayPresenter(Context context) {
        this.mContext = context;
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

    public void onTapTvShow(String tvShowId) {
        mView.navigateToTvShowDetails(tvShowId);
    }
}
