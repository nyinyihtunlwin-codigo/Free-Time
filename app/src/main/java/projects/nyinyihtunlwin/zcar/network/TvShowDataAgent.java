package projects.nyinyihtunlwin.zcar.network;

import android.content.Context;

/**
 * Created by Dell on 2/22/2018.
 */

public interface TvShowDataAgent {

    void loadTvAiringToday(String apiKey, int pageNo, String region, Context context);

    void loadTvOnTheAir(String apiKey, int pageNo, String region, Context context);

    void loadTvMostPopular(String apiKey, int pageNo, String region, Context context);

    void loadTvTopRated(String apiKey, int pageNo, String region, Context context);
}
