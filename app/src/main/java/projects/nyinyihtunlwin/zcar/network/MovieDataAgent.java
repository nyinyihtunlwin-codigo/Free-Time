package projects.nyinyihtunlwin.zcar.network;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public interface MovieDataAgent {

    void loadPopularMovies(String apiKey, int pageNo, String region);

    void loadNowOnCinemaMovies(String apiKey, int pageNo, String region);

    void loadUpcomingMovies(String apiKey, int pageNo, String region);

    void loadTopRatedMovies(String apiKey, int pageNo, String region);
}
