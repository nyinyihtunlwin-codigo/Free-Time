package projects.nyinyihtunlwin.zcar.network;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import projects.nyinyihtunlwin.zcar.events.RestApiEvents;
import projects.nyinyihtunlwin.zcar.network.responses.NowShowingMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.PopularMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.TopRatedMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.UpcomingMoviesResponse;
import retrofit2.Call;
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
                .baseUrl("https://api.themoviedb.org/3/")
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
    public void loadPopularMovies(String apiKey, int pageNo, String region) {
        Call<PopularMoviesResponse> loadPopularMoviesCall = movieAPI.loadPopularMovies(apiKey, pageNo, "US");
        loadPopularMoviesCall.enqueue(new MovieCallback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                PopularMoviesResponse getPopularMovieResponse = response.body();
                if (getPopularMovieResponse != null
                        && getPopularMovieResponse.getMovies().size() > 0) {
                    RestApiEvents.MoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.MoviesDataLoadedEvent(getPopularMovieResponse.getPage(), getPopularMovieResponse.getMovies());
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                }
            }
        });
    }

    @Override
    public void loadNowOnCinemaMovies(String apiKey, int pageNo, String region) {
        Call<NowShowingMoviesResponse> loadNowShowingMoviesCall = movieAPI.loadNowShowingMovies(apiKey, pageNo, "US");
        loadNowShowingMoviesCall.enqueue(new MovieCallback<NowShowingMoviesResponse>() {
            @Override
            public void onResponse(Call<NowShowingMoviesResponse> call, Response<NowShowingMoviesResponse> response) {
                NowShowingMoviesResponse getNowShowingMoviesResponse = response.body();
                if (getNowShowingMoviesResponse != null
                        && getNowShowingMoviesResponse.getMovies().size() > 0) {
                    RestApiEvents.MoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.MoviesDataLoadedEvent(getNowShowingMoviesResponse.getPage(), getNowShowingMoviesResponse.getMovies());
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                }
            }
        });
    }

    @Override
    public void loadUpcomingMovies(String apiKey, int pageNo, String region) {
        Call<UpcomingMoviesResponse> loadUpcomingMoviesCall = movieAPI.loadUpcomingMovies(apiKey, pageNo, "US");
        loadUpcomingMoviesCall.enqueue(new MovieCallback<UpcomingMoviesResponse>() {
            @Override
            public void onResponse(Call<UpcomingMoviesResponse> call, Response<UpcomingMoviesResponse> response) {
                UpcomingMoviesResponse getUpcomingMoviesResponse = response.body();
                if (getUpcomingMoviesResponse != null
                        && getUpcomingMoviesResponse.getMovies().size() > 0) {
                    RestApiEvents.MoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.MoviesDataLoadedEvent(getUpcomingMoviesResponse.getPage(), getUpcomingMoviesResponse.getMovies());
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                }
            }
        });
    }

    @Override
    public void loadTopRatedMovies(String apiKey, int pageNo, String region) {
        Call<TopRatedMoviesResponse> loadTopRatedMoviesCall = movieAPI.loadTopRatedMovies(apiKey, pageNo, "US");
        loadTopRatedMoviesCall.enqueue(new MovieCallback<TopRatedMoviesResponse>() {
            @Override
            public void onResponse(Call<TopRatedMoviesResponse> call, Response<TopRatedMoviesResponse> response) {
                TopRatedMoviesResponse getTopRatedMoviesResponse = response.body();
                if (getTopRatedMoviesResponse != null
                        && getTopRatedMoviesResponse.getMovies().size() > 0) {
                    RestApiEvents.MoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.MoviesDataLoadedEvent(getTopRatedMoviesResponse.getPage(), getTopRatedMoviesResponse.getMovies());
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                }
            }
        });
    }
}
