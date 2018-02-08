package projects.nyinyihtunlwin.zcar.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.events.RestApiEvents;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieTrailersResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.MovieGenresResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.NowShowingMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.PopularMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.TopRatedMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.UpcomingMoviesResponse;
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
    public void loadPopularMovies(String apiKey, int pageNo, String region, final Context context) {
        Call<PopularMoviesResponse> loadPopularMoviesCall = movieAPI.loadPopularMovies(apiKey, pageNo, "US");
        loadPopularMoviesCall.enqueue(new MovieCallback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                PopularMoviesResponse getPopularMovieResponse = response.body();
                if (getPopularMovieResponse != null
                        && getPopularMovieResponse.getMovies().size() > 0) {
                    RestApiEvents.PoputlarMoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.PoputlarMoviesDataLoadedEvent(getPopularMovieResponse.getPage(), getPopularMovieResponse.getMovies(), context);
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                }
            }
        });
    }

    @Override
    public void loadNowOnCinemaMovies(String apiKey, int pageNo, String region, final Context context) {
        Call<NowShowingMoviesResponse> loadNowShowingMoviesCall = movieAPI.loadNowShowingMovies(apiKey, pageNo, "US");
        loadNowShowingMoviesCall.enqueue(new MovieCallback<NowShowingMoviesResponse>() {
            @Override
            public void onResponse(Call<NowShowingMoviesResponse> call, Response<NowShowingMoviesResponse> response) {
                NowShowingMoviesResponse getNowShowingMoviesResponse = response.body();
                if (getNowShowingMoviesResponse != null
                        && getNowShowingMoviesResponse.getMovies().size() > 0) {
                    RestApiEvents.NowOnCinemaMoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.NowOnCinemaMoviesDataLoadedEvent(getNowShowingMoviesResponse.getPage(), getNowShowingMoviesResponse.getMovies(), context);
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                }
            }
        });
    }

    @Override
    public void loadUpcomingMovies(String apiKey, int pageNo, String region, final Context context) {
        Call<UpcomingMoviesResponse> loadUpcomingMoviesCall = movieAPI.loadUpcomingMovies(apiKey, pageNo, "US");
        loadUpcomingMoviesCall.enqueue(new MovieCallback<UpcomingMoviesResponse>() {
            @Override
            public void onResponse(Call<UpcomingMoviesResponse> call, Response<UpcomingMoviesResponse> response) {
                UpcomingMoviesResponse getUpcomingMoviesResponse = response.body();
                if (getUpcomingMoviesResponse != null
                        && getUpcomingMoviesResponse.getMovies().size() > 0) {
                    RestApiEvents.UpcomingMoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.UpcomingMoviesDataLoadedEvent(getUpcomingMoviesResponse.getPage(), getUpcomingMoviesResponse.getMovies(), context);
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                }
            }
        });
    }

    @Override
    public void loadTopRatedMovies(String apiKey, int pageNo, String region, final Context context) {
        Call<TopRatedMoviesResponse> loadTopRatedMoviesCall = movieAPI.loadTopRatedMovies(apiKey, pageNo, "US");
        loadTopRatedMoviesCall.enqueue(new MovieCallback<TopRatedMoviesResponse>() {
            @Override
            public void onResponse(Call<TopRatedMoviesResponse> call, Response<TopRatedMoviesResponse> response) {
                TopRatedMoviesResponse getTopRatedMoviesResponse = response.body();
                if (getTopRatedMoviesResponse != null
                        && getTopRatedMoviesResponse.getMovies().size() > 0) {
                    RestApiEvents.TopRatedMoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.TopRatedMoviesDataLoadedEvent(getTopRatedMoviesResponse.getPage(), getTopRatedMoviesResponse.getMovies(), context);
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                }
            }
        });
    }

    @Override
    public void loadMovieGenres(String apiKey, final Context context) {
        Call<MovieGenresResponse> loadMovieGenres = movieAPI.loadMovieGenres(apiKey);
        loadMovieGenres.enqueue(new MovieCallback<MovieGenresResponse>() {
            @Override
            public void onResponse(Call<MovieGenresResponse> call, Response<MovieGenresResponse> response) {
                MovieGenresResponse getMovieGenresResponse = response.body();
                if (getMovieGenresResponse != null
                        && getMovieGenresResponse.getGenres().size() > 0) {
                    RestApiEvents.MovieGenresDataLoadedEvent movieGenresDataLoadedEvent = new RestApiEvents.MovieGenresDataLoadedEvent(getMovieGenresResponse.getGenres(), context);
                    EventBus.getDefault().post(movieGenresDataLoadedEvent);
                }
            }
        });
    }

    @Override
    public void loadMovieDetails(int movieId, String apiKey) {
        Call<MovieVO> loadMovieDetails = movieAPI.loadMovieDetails(movieId, apiKey);
        loadMovieDetails.enqueue(new Callback<MovieVO>() {
            @Override
            public void onResponse(Call<MovieVO> call, Response<MovieVO> response) {
                MovieVO movieDetailsResponse = response.body();
                if (movieDetailsResponse != null) {
                    EventBus.getDefault().post(new RestApiEvents.MovieDetailsDataLoadedEvent(movieDetailsResponse));
                    Log.e("details", "Runtime" + movieDetailsResponse.getRuntime());
                }
            }

            @Override
            public void onFailure(Call<MovieVO> call, Throwable t) {
                RestApiEvents.ErrorInvokingAPIEvent errorEvent
                        = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void loadMovieTrailers(int movieId, String apiKey) {
        Call<GetMovieTrailersResponse> loadMovieTrailersResponse = movieAPI.loadMovieTrailers(movieId, apiKey);
        loadMovieTrailersResponse.enqueue(new MovieCallback<GetMovieTrailersResponse>() {
            @Override
            public void onResponse(Call<GetMovieTrailersResponse> call, Response<GetMovieTrailersResponse> response) {
                GetMovieTrailersResponse getMovieTrailersResponse = response.body();
                if (getMovieTrailersResponse != null
                        && getMovieTrailersResponse.getVideos().size() > 0) {
                    EventBus.getDefault().post(new RestApiEvents.MovieTrailersDataLoadedEvent(getMovieTrailersResponse.getVideos()));
                    Log.e("Trailers:",getMovieTrailersResponse.getVideos().size()+"");
                }
            }
        });
    }
}
