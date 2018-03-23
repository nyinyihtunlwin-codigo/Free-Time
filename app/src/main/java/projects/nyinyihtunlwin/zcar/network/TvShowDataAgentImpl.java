package projects.nyinyihtunlwin.zcar.network;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import projects.nyinyihtunlwin.zcar.events.TvShowsEvents;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvAiringTodayResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvMostPopularResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvOnTheAirResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvTopRatedResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 2/22/2018.
 */

public class TvShowDataAgentImpl implements TvShowDataAgent {

    private static TvShowDataAgentImpl objectInstance;

    private MovieAPI movieAPI;

    private TvShowDataAgentImpl() {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        // time 60 sec is optimal.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient)
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }

    public static TvShowDataAgentImpl getObjectInstance() {
        if (objectInstance == null) {
            objectInstance = new TvShowDataAgentImpl();
        }
        return objectInstance;
    }


    @Override
    public void loadTvAiringToday(String apiKey, int pageNo, String region, final Context context) {
        Call<TvAiringTodayResponse> loadTvAiringTodayCall = movieAPI.loadTvAiringToday(apiKey, pageNo);
        loadTvAiringTodayCall.enqueue(new MovieCallback<TvAiringTodayResponse>() {
            @Override
            public void onResponse(Call<TvAiringTodayResponse> call, Response<TvAiringTodayResponse> response) {
                TvAiringTodayResponse getTvAiringTodayResponse = response.body();
                if (getTvAiringTodayResponse != null
                        && getTvAiringTodayResponse.getTvShows().size() > 0) {
                    TvShowsEvents.TvAiringTodayEvent tvAiringTodayEvent = new TvShowsEvents.TvAiringTodayEvent(
                            getTvAiringTodayResponse.getPage(), getTvAiringTodayResponse.getTvShows(), context
                    );
                    EventBus.getDefault().post(tvAiringTodayEvent);
                }
            }
        });
    }

    @Override
    public void loadTvOnTheAir(String apiKey, int pageNo, String region, final Context context) {
        Call<TvOnTheAirResponse> loadTvOnTheAirCall = movieAPI.loadTvOnTheAir(apiKey, pageNo);
        loadTvOnTheAirCall.enqueue(new MovieCallback<TvOnTheAirResponse>() {
            @Override
            public void onResponse(Call<TvOnTheAirResponse> call, Response<TvOnTheAirResponse> response) {
                TvOnTheAirResponse getTvOnTheAirResponse = response.body();
                if (getTvOnTheAirResponse != null
                        && getTvOnTheAirResponse.getTvShows().size() > 0) {
                    TvShowsEvents.TvOnTheAirEvent tvOnTheAirEvent = new TvShowsEvents.TvOnTheAirEvent(
                            getTvOnTheAirResponse.getPage(), getTvOnTheAirResponse.getTvShows(), context
                    );
                    EventBus.getDefault().post(tvOnTheAirEvent);
                }
            }
        });
    }

    @Override
    public void loadTvMostPopular(String apiKey, int pageNo, String region, final Context context) {
        Call<TvMostPopularResponse> loadTvMostPopularCall = movieAPI.loadTvMostPopular(apiKey, pageNo);
        loadTvMostPopularCall.enqueue(new MovieCallback<TvMostPopularResponse>() {
            @Override
            public void onResponse(Call<TvMostPopularResponse> call, Response<TvMostPopularResponse> response) {
                TvMostPopularResponse getTvMostPopularResponse = response.body();
                if (getTvMostPopularResponse != null
                        && getTvMostPopularResponse.getTvShows().size() > 0) {
                    TvShowsEvents.TvMostPopularEvent tvMostPopularEvent = new TvShowsEvents.TvMostPopularEvent(
                            getTvMostPopularResponse.getPage(), getTvMostPopularResponse.getTvShows(), context
                    );
                    EventBus.getDefault().post(tvMostPopularEvent);
                }
            }
        });
    }

    @Override
    public void loadTvTopRated(String apiKey, int pageNo, String region, final Context context) {
        Call<TvTopRatedResponse> loadTvTopRatedCall = movieAPI.loadTvTopRated(apiKey, pageNo);
        loadTvTopRatedCall.enqueue(new MovieCallback<TvTopRatedResponse>() {
            @Override
            public void onResponse(Call<TvTopRatedResponse> call, Response<TvTopRatedResponse> response) {
                TvTopRatedResponse getTvTopRatedResponse = response.body();
                if (getTvTopRatedResponse != null
                        && getTvTopRatedResponse.getTvShows().size() > 0) {
                    TvShowsEvents.TvTopRatedEvent tvTopRatedEvent = new TvShowsEvents.TvTopRatedEvent(
                            getTvTopRatedResponse.getPage(), getTvTopRatedResponse.getTvShows(), context
                    );
                    EventBus.getDefault().post(tvTopRatedEvent);
                }
            }
        });
    }
}
