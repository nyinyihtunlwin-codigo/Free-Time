package projects.nyinyihtunlwin.zcar.mvp.views;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;

public interface TvShowsTopRatedView {
    void displayTvShowList(List<TvShowVO> tvShowList);

    void showLoding();

    void navigateToTvShowDetails(String tvShowId);

    void onConnectionError(String message, int retryConnectionType);

    void onApiError(String message);
}
