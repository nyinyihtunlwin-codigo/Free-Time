package projects.nyinyihtunlwin.zcar.mvp.views;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;

/**
 * Created by Dell on 3/6/2018.
 */

public interface TvShowsAiringTodayView {

    void displayTvShowList(List<TvShowVO> tvShowList);

    void showLoding();

    void navigateToTvShowDetails(String tvShowId);
}
