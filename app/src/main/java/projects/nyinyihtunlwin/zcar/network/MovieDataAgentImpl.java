package projects.nyinyihtunlwin.zcar.network;

import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import projects.nyinyihtunlwin.zcar.events.RestApiEvents;
import projects.nyinyihtunlwin.zcar.network.responses.GetPopularMovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public class MovieDataAgentImpl implements MovieDataAgent {
    private static MovieDataAgentImpl objectInstance;

    private MovieAPI movieAPI;

    private MovieDataAgentImpl() {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        // time 60 sec is optimal.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://padcmyanmar.com/padc-3/popular-movies/apis/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient)
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }

    public static MovieDataAgentImpl getObjectInstance() {
        if (objectInstance == null) {
            objectInstance = new MovieDataAgentImpl();
        }
        return objectInstance;
    }


    @Override
    public void loadPopularMovies(int page, String accessToken) {
        Call<GetPopularMovieResponse> loadPopularMoviesCall = movieAPI.loadPopularMovies(page, accessToken);
        loadPopularMoviesCall.enqueue(new Callback<GetPopularMovieResponse>() {
            @Override
            public void onResponse(Call<GetPopularMovieResponse> call, Response<GetPopularMovieResponse> response) {
                GetPopularMovieResponse getPopularMovieResponse = response.body();
                Log.e("status ", getPopularMovieResponse.getCode() + "");
                if (getPopularMovieResponse != null
                        && getPopularMovieResponse.getPopularMovies().size() > 0) {
                    RestApiEvents.MoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.MoviesDataLoadedEvent(getPopularMovieResponse.getPage(), getPopularMovieResponse.getPopularMovies());
                    EventBus.getDefault().post(moviesDataLoadedEvent);

                } else {
                    RestApiEvents.ErrorInvokingAPIEvent errorInvokingAPIEvent
                            = new RestApiEvents.ErrorInvokingAPIEvent("No data could be load for now. Please try again later.");
                    EventBus.getDefault().post(errorInvokingAPIEvent);
                }
            }

            @Override
            public void onFailure(Call<GetPopularMovieResponse> call, Throwable t) {
                RestApiEvents.ErrorInvokingAPIEvent errorInvokingAPIEvent
                        = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
                EventBus.getDefault().post(errorInvokingAPIEvent);
            }
        });

    }
}
