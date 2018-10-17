package projects.nyinyihtunlwin.zcar.network;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.events.MovieDetailsEvent;
import projects.nyinyihtunlwin.zcar.events.MoviesiEvents;
import projects.nyinyihtunlwin.zcar.events.SearchEvents;
import projects.nyinyihtunlwin.zcar.network.responses.SearchResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieCreditsResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieReviewsResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieTrailersResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetSimilarMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.MovieGenresResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.NowShowingMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.PopularMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.TopRatedMoviesResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.UpcomingMoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public class MovieDataAgentImpl extends BaseDataAgentImpl implements MovieDataAgent {
    private static MovieDataAgentImpl objectInstance;

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
                    MoviesiEvents.PoputlarMoviesDataLoadedEvent moviesDataLoadedEvent = new MoviesiEvents.PoputlarMoviesDataLoadedEvent(getPopularMovieResponse.getPage(), getPopularMovieResponse.getMovies(), context);
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
                    MoviesiEvents.NowOnCinemaMoviesDataLoadedEvent moviesDataLoadedEvent = new MoviesiEvents.NowOnCinemaMoviesDataLoadedEvent(getNowShowingMoviesResponse.getPage(), getNowShowingMoviesResponse.getMovies(), context);
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
                    MoviesiEvents.UpcomingMoviesDataLoadedEvent moviesDataLoadedEvent = new MoviesiEvents.UpcomingMoviesDataLoadedEvent(getUpcomingMoviesResponse.getPage(), getUpcomingMoviesResponse.getMovies(), context);
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
                    MoviesiEvents.TopRatedMoviesDataLoadedEvent moviesDataLoadedEvent = new MoviesiEvents.TopRatedMoviesDataLoadedEvent(getTopRatedMoviesResponse.getPage(), getTopRatedMoviesResponse.getMovies(), context);
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
                    MoviesiEvents.MovieGenresDataLoadedEvent movieGenresDataLoadedEvent = new MoviesiEvents.MovieGenresDataLoadedEvent(getMovieGenresResponse.getGenres(), context);
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
                    EventBus.getDefault().post(new MoviesiEvents.MovieDetailsDataLoadedEvent(movieDetailsResponse));
                    Log.e("details", "Runtime" + movieDetailsResponse.getRuntime());
                }
            }

            @Override
            public void onFailure(Call<MovieVO> call, Throwable t) {
                MovieDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new MovieDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
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
                    EventBus.getDefault().post(new MoviesiEvents.MovieTrailersDataLoadedEvent(getMovieTrailersResponse.getVideos()));
                    Log.e("Trailers:", getMovieTrailersResponse.getVideos().size() + "");
                }
            }

            @Override
            public void onFailure(Call<GetMovieTrailersResponse> call, Throwable t) {
                MovieDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new MovieDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void loadMovieReviews(int movieId, String apiKey) {
        Call<GetMovieReviewsResponse> loadMovieReviewsResponse = movieAPI.loadMovieReviews(movieId, apiKey);
        loadMovieReviewsResponse.enqueue(new MovieCallback<GetMovieReviewsResponse>() {
            @Override
            public void onResponse(Call<GetMovieReviewsResponse> call, Response<GetMovieReviewsResponse> response) {
                GetMovieReviewsResponse getMovieReviewsResponse = response.body();
                if (getMovieReviewsResponse != null) {
                    if (getMovieReviewsResponse.getReviews().size() > 0) {
                        EventBus.getDefault().post(new MoviesiEvents.MovieReviewsDataLoadedEvent(getMovieReviewsResponse.getReviews()));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMovieReviewsResponse> call, Throwable t) {
                MovieDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new MovieDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void loadMovieCredits(int movieId, String apiKey) {
        Call<GetMovieCreditsResponse> loadMovieCreditsResponse = movieAPI.loadMovieCredits(movieId, apiKey);
        loadMovieCreditsResponse.enqueue(new MovieCallback<GetMovieCreditsResponse>() {
            @Override
            public void onResponse(Call<GetMovieCreditsResponse> call, Response<GetMovieCreditsResponse> response) {
                GetMovieCreditsResponse getMovieCreditsResponse = response.body();
                if (getMovieCreditsResponse != null
                        && getMovieCreditsResponse.getCasts().size() > 0) {
                    EventBus.getDefault().post(new MoviesiEvents.MovieCreditsDataLoadedEvent(getMovieCreditsResponse.getCasts()));
                }
            }

            @Override
            public void onFailure(Call<GetMovieCreditsResponse> call, Throwable t) {
                MovieDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new MovieDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void loadSimilarMovies(int movieId, String apiKey) {
        Call<GetSimilarMoviesResponse> loadSimilarMoviesResponse = movieAPI.loadSimilarMovies(movieId, apiKey, 1);
        loadSimilarMoviesResponse.enqueue(new MovieCallback<GetSimilarMoviesResponse>() {
            @Override
            public void onResponse(Call<GetSimilarMoviesResponse> call, Response<GetSimilarMoviesResponse> response) {
                GetSimilarMoviesResponse getSimilarMoviesResponse = response.body();
                if (getSimilarMoviesResponse != null
                        && getSimilarMoviesResponse.getMovies().size() > 0) {
                    EventBus.getDefault().post(new MoviesiEvents.MovieSimilarDataLoadedEvent(getSimilarMoviesResponse.getMovies()));
                }
            }

            @Override
            public void onFailure(Call<GetSimilarMoviesResponse> call, Throwable t) {
                MovieDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new MovieDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void startSearching(String apiKey, int pageNo, String query) {
        Call<SearchResponse> loadSearhCall = movieAPI.search(apiKey, pageNo, query);
        loadSearhCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse getSearchResponse = response.body();
                if (getSearchResponse != null
                        && getSearchResponse.getResults().size() > 0) {
                    SearchEvents.SearchResultsDataLoadedEvent event = new SearchEvents.SearchResultsDataLoadedEvent(getSearchResponse.getPage(), getSearchResponse.getResults());
                    EventBus.getDefault().post(event);
                } else {
                    SearchEvents.ErrorInvokingAPIEvent errorEvent
                            = new SearchEvents.ErrorInvokingAPIEvent("No data found.");
                    EventBus.getDefault().post(errorEvent);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                SearchEvents.ErrorInvokingAPIEvent errorEvent
                        = new SearchEvents.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }
}
