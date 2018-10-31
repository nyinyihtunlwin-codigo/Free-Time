package projects.nyinyihtunlwin.freetime.network;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public interface PersonDataAgent {
    void loadPersonDetails(int personId, String apiKey);
    void loadMovieCredits(int personId,String apiKey);
    void loadTVShowCredits(int personId,String apiKey);
}
