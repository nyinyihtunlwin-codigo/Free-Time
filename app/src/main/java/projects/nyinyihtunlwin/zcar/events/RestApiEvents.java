package projects.nyinyihtunlwin.zcar.events;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;

/**
 * Created by Nyi Nyi Htun Lwin on 12/7/2017.
 */

public class RestApiEvents {

    public static class ErrorInvokingAPIEvent {
        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class MoviesDataLoadedEvent {
        private int loadedPageIndex;
        private List<MovieVO> loadedMovies;
        private String moviesForScreen;

        public MoviesDataLoadedEvent(int loadedPageIndex, List<MovieVO> loadedMovies, String moviesForScreen) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedMovies = loadedMovies;
            this.moviesForScreen = moviesForScreen;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<MovieVO> getLoadedMovies() {
            return loadedMovies;
        }

        public String getMoviesForScreen() {
            return moviesForScreen;
        }
    }
}
