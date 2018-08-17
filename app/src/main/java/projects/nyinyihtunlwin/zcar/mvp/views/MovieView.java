package projects.nyinyihtunlwin.zcar.mvp.views;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;

public interface MovieView {
    void displayMoviesList(List<MovieVO> moviesList);

    void showLoding();

    void navigateToMovieDetails(String movieId);

    void onConnectionError(String message, int retryConnectionType);

    void onApiError(String message);
}
