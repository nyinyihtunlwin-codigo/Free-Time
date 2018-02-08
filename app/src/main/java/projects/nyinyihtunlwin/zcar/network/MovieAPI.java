package projects.nyinyihtunlwin.zcar.network;

import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieTrailersResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.MovieGenresResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.NowShowingMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.PopularMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.TopRatedMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.UpcomingMoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public interface MovieAPI {

    // for movies

    @GET("movie/now_playing")
    Call<NowShowingMoviesResponse> loadNowShowingMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/popular")
    Call<PopularMoviesResponse> loadPopularMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/upcoming")
    Call<UpcomingMoviesResponse> loadUpcomingMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("region") String region);

    @GET("movie/top_rated")
    Call<TopRatedMoviesResponse> loadTopRatedMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("region") String region);

    @GET("genre/movie/list")
    Call<MovieGenresResponse> loadMovieGenres(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieVO> loadMovieDetails(@Path("id") Integer movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<GetMovieTrailersResponse> loadMovieTrailers(@Path("id") Integer movieId, @Query("api_key") String apiKey);

}
