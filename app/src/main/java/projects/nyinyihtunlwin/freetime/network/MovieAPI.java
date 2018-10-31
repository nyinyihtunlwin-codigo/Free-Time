package projects.nyinyihtunlwin.freetime.network;

import projects.nyinyihtunlwin.freetime.network.responses.PersonDetailsResponse;
import projects.nyinyihtunlwin.freetime.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.freetime.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.freetime.network.responses.SearchResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.GetMovieCreditsResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.GetMovieReviewsResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.GetMovieTrailersResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.GetSimilarMoviesResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.GetSimilarTvShowsResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.MovieCreditOfPersonResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.MovieGenresResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.NowShowingMoviesResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.PopularMoviesResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.TVCreditOfPersonResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.TopRatedMoviesResponse;
import projects.nyinyihtunlwin.freetime.network.responses.movies.UpcomingMoviesResponse;
import projects.nyinyihtunlwin.freetime.network.responses.tvshows.TvAiringTodayResponse;
import projects.nyinyihtunlwin.freetime.network.responses.tvshows.TvMostPopularResponse;
import projects.nyinyihtunlwin.freetime.network.responses.tvshows.TvOnTheAirResponse;
import projects.nyinyihtunlwin.freetime.network.responses.tvshows.TvTopRatedResponse;
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

    @GET("movie/{id}/similar")
    Call<GetSimilarMoviesResponse> loadSimilarMovies(@Path("id") Integer movieId, @Query("api_key") String apiKey, @Query("page") Integer page);


    // TV Shows
    @GET("tv/airing_today")
    Call<TvAiringTodayResponse> loadTvAiringToday(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/on_the_air")
    Call<TvOnTheAirResponse> loadTvOnTheAir(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/popular")
    Call<TvMostPopularResponse> loadTvMostPopular(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/top_rated")
    Call<TvTopRatedResponse> loadTvTopRated(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/{id}")
    Call<TvShowVO> loadTVShowDetails(@Path("id") Integer tvShowId, @Query("api_key") String apiKey);

    @GET("tv/{id}/videos")
    Call<GetMovieTrailersResponse> loadTvShowTrailers(@Path("id") Integer movieId, @Query("api_key") String apiKey);

    @GET("tv/{id}/credits")
    Call<GetMovieCreditsResponse> loadTvShowCredits(@Path("id") Integer movieId, @Query("api_key") String apiKey);

    @GET("/tv/{tv_id}/reviews")
    Call<GetMovieReviewsResponse> loadTvShowReviews(@Path("tv_id") Integer movieId, @Query("api_key") String apiKey);

    @GET("tv/{id}/similar")
    Call<GetSimilarTvShowsResponse> loadSimilarTVShows(@Path("id") Integer tvShowId, @Query("api_key") String apiKey, @Query("page") Integer page);

    // Search Movies and TV Shows
    @GET("search/multi")
    Call<SearchResponse> search(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("query") String query);

    // Person
    @GET("person/{id}")
    Call<PersonDetailsResponse> loadPersonDetails(@Path("id") Integer personId, @Query("api_key") String apiKey);

    @GET("person/{id}/movie_credits")
    Call<MovieCreditOfPersonResponse> loadMovieCreditsOfPerson(@Path("id") Integer personId, @Query("api_key") String apiKey);

    @GET("person/{id}/tv_credits")
    Call<TVCreditOfPersonResponse> loadTVCreditsOfPerson(@Path("id") Integer personId, @Query("api_key") String apiKey);
}
