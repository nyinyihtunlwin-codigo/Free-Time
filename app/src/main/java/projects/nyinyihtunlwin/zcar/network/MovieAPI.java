package projects.nyinyihtunlwin.zcar.network;

import projects.nyinyihtunlwin.zcar.network.responses.NowShowingMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.PopularMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.TopRatedMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.UpcomingMoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public interface MovieAPI {

    @GET("movie/now_playing")
    Call<NowShowingMoviesResponse> loadNowShowingMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/popular")
    Call<PopularMoviesResponse> loadPopularMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/upcoming")
    Call<UpcomingMoviesResponse> loadUpcomingMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/top_rated")
    Call<TopRatedMoviesResponse> loadTopRatedMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("region") String region);
}
