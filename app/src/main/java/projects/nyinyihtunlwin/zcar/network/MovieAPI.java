package projects.nyinyihtunlwin.zcar.network;

import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.network.responses.SearchResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieCreditsResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieReviewsResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieTrailersResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.MovieGenresResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.NowShowingMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.PopularMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.TopRatedMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.UpcomingMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvAiringTodayResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvMostPopularResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvOnTheAirResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvTopRatedResponse;
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

    @GET("movie/{movie_id}/reviews")
    Call<GetMovieReviewsResponse> loadMovieReviews(@Path("movie_id") Integer movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<GetMovieCreditsResponse> loadMovieCredits(@Path("id") Integer movieId, @Query("api_key") String apiKey);

    // TV Shows
    @GET("tv/airing_today")
    Call<TvAiringTodayResponse> loadTvAiringToday(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/on_the_air")
    Call<TvOnTheAirResponse> loadTvOnTheAir(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/popular")
    Call<TvMostPopularResponse> loadTvMostPopular(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/top_rated")
    Call<TvTopRatedResponse> loadTvTopRated(@Query("api_key") String apiKey, @Query("page") Integer page);

    // Search Movies and TV Shows
    @GET("search/multi")
    Call<SearchResponse> search(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("query") String query);

}
