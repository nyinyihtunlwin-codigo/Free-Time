package projects.nyinyihtunlwin.zcar.network;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.events.MovieDetailsEvent;
import projects.nyinyihtunlwin.zcar.events.MoviesiEvents;
import projects.nyinyihtunlwin.zcar.events.TvShowDetailsEvent;
import projects.nyinyihtunlwin.zcar.events.TvShowsEvents;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieCreditsResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieReviewsResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetMovieTrailersResponse;
import projects.nyinyihtunlwin.zcar.network.responses.movies.GetSimilarTvShowsResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvAiringTodayResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvMostPopularResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvOnTheAirResponse;
import projects.nyinyihtunlwin.zcar.network.responses.tvshows.TvTopRatedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 2/22/2018.
 */

public class TvShowDataAgentImpl extends BaseDataAgentImpl implements TvShowDataAgent {

    private static TvShowDataAgentImpl objectInstance;

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

    @Override
    public void loadTvShowDetails(int movieId, String apiKey) {
        Call<TvShowVO> loadTVShowDetails = movieAPI.loadTVShowDetails(movieId, apiKey);
        loadTVShowDetails.enqueue(new Callback<TvShowVO>() {
            @Override
            public void onResponse(Call<TvShowVO> call, Response<TvShowVO> response) {
                TvShowVO movieDetailsResponse = response.body();
                if (movieDetailsResponse != null) {
                    EventBus.getDefault().post(new TvShowsEvents.TvShowDetailsDataLoadedEvent(movieDetailsResponse));
                    //  Log.e("details", "Runtime" + movieDetailsResponse.getRuntime());
                }
            }

            @Override
            public void onFailure(Call<TvShowVO> call, Throwable t) {
                TvShowDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new TvShowDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void loadTvShowTrailers(int movieId, String apiKey) {
        Call<GetMovieTrailersResponse> loadMovieTrailersResponse = movieAPI.loadTvShowTrailers(movieId, apiKey);
        loadMovieTrailersResponse.enqueue(new Callback<GetMovieTrailersResponse>() {
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
                TvShowDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new TvShowDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void loadTvShowReviews(int movieId, String apiKey) {
        Call<GetMovieReviewsResponse> loadMovieReviewsResponse = movieAPI.loadTvShowReviews(movieId, apiKey);
        loadMovieReviewsResponse.enqueue(new Callback<GetMovieReviewsResponse>() {
            @Override
            public void onResponse(Call<GetMovieReviewsResponse> call, Response<GetMovieReviewsResponse> response) {
                GetMovieReviewsResponse getMovieReviewsResponse = response.body();
                if (getMovieReviewsResponse != null) {
                    if (getMovieReviewsResponse.getReviews().size() > 0) {
                        EventBus.getDefault().post(new MoviesiEvents.MovieReviewsDataLoadedEvent(getMovieReviewsResponse.getReviews()));
                    } else {
                        EventBus.getDefault().post(new MoviesiEvents.ErrorInvokingAPIEvent("No reviews for now!"));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMovieReviewsResponse> call, Throwable t) {
                TvShowDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new TvShowDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void loadTvShowCredits(int movieId, String apiKey) {
        Call<GetMovieCreditsResponse> loadMovieCreditsResponse = movieAPI.loadTvShowCredits(movieId, apiKey);
        loadMovieCreditsResponse.enqueue(new Callback<GetMovieCreditsResponse>() {
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
                TvShowDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new TvShowDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }

    @Override
    public void loadSimilarTvShows(int movieId, String apiKey) {
        Call<GetSimilarTvShowsResponse> loadSimilarTVShowsResponse = movieAPI.loadSimilarTVShows(movieId, apiKey, 1);
        loadSimilarTVShowsResponse.enqueue(new MovieCallback<GetSimilarTvShowsResponse>() {
            @Override
            public void onResponse(Call<GetSimilarTvShowsResponse> call, Response<GetSimilarTvShowsResponse> response) {
                GetSimilarTvShowsResponse getSimilarTvShowsResponse = response.body();
                if (getSimilarTvShowsResponse != null
                        && getSimilarTvShowsResponse.getTvShows().size() > 0) {
                    EventBus.getDefault().post(new TvShowsEvents.TvShowSimilarDataLoadedEvent(getSimilarTvShowsResponse.getTvShows()));
                }
            }

            @Override
            public void onFailure(Call<GetSimilarTvShowsResponse> call, Throwable t) {
                MovieDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new MovieDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }
}
