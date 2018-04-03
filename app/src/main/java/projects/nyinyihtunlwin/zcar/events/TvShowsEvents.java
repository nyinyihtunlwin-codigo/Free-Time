package projects.nyinyihtunlwin.zcar.events;

import android.content.Context;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;

/**
 * Created by Dell on 2/22/2018.
 */

public class TvShowsEvents {

    public static class ErrorInvokingAPIEvent {
        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class TvShowsDataLoadedEvent {
        private int loadedPageIndex;
        private List<TvShowVO> loadedTvShows;
        private Context context;

        public TvShowsDataLoadedEvent(int loadedPageIndex, List<TvShowVO> loadedTvShows, Context context) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedTvShows = loadedTvShows;
            this.context = context;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<TvShowVO> getLoadedTvShows() {
            return loadedTvShows;
        }

        public Context getContext() {
            return context;
        }
    }

    public static class TvAiringTodayEvent extends TvShowsDataLoadedEvent {
        public TvAiringTodayEvent(int loadedPageIndex, List<TvShowVO> loadedTvShows, Context context) {
            super(loadedPageIndex, loadedTvShows, context);
        }
    }

    public static class TvOnTheAirEvent extends TvShowsDataLoadedEvent {
        public TvOnTheAirEvent(int loadedPageIndex, List<TvShowVO> loadedTvShows, Context context) {
            super(loadedPageIndex, loadedTvShows, context);
        }
    }

    public static class TvMostPopularEvent extends TvShowsDataLoadedEvent {
        public TvMostPopularEvent(int loadedPageIndex, List<TvShowVO> loadedTvShows, Context context) {
            super(loadedPageIndex, loadedTvShows, context);
        }
    }

    public static class TvTopRatedEvent extends TvShowsDataLoadedEvent {
        public TvTopRatedEvent(int loadedPageIndex, List<TvShowVO> loadedTvShows, Context context) {
            super(loadedPageIndex, loadedTvShows, context);
        }
    }

    public static class TvShowDetailsDataLoadedEvent {
        private TvShowVO mTvShow;

        public TvShowDetailsDataLoadedEvent(TvShowVO mTvShow) {
            this.mTvShow = mTvShow;
        }

        public TvShowVO getTvShow() {
            return mTvShow;
        }
    }

}
