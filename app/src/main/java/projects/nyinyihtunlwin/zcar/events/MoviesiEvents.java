package projects.nyinyihtunlwin.zcar.events;

import android.content.Context;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.CastVO;
import projects.nyinyihtunlwin.zcar.data.vo.GenreVO;
import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.data.vo.ReviewVO;
import projects.nyinyihtunlwin.zcar.data.vo.TrailerVO;

/**
 * Created by Nyi Nyi Htun Lwin on 12/7/2017.
 */

public class MoviesiEvents {

    public static class ErrorInvokingAPIEvent {
        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class MoviesDataLoadedEvent {
        private int loadedPageIndex;
        private List<MovieVO> loadedMovies;
        private Context context;

        public MoviesDataLoadedEvent(int loadedPageIndex, List<MovieVO> loadedMovies, Context context) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedMovies = loadedMovies;
            this.context = context;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<MovieVO> getLoadedMovies() {
            return loadedMovies;
        }

        public Context getContext() {
            return context;
        }
    }

    public static class UpcomingMoviesDataLoadedEvent extends MoviesDataLoadedEvent {
        public UpcomingMoviesDataLoadedEvent(int loadedPageIndex, List<MovieVO> loadedMovies, Context context) {
            super(loadedPageIndex, loadedMovies, context);
        }
    }

    public static class PoputlarMoviesDataLoadedEvent extends MoviesDataLoadedEvent {
        public PoputlarMoviesDataLoadedEvent(int loadedPageIndex, List<MovieVO> loadedMovies, Context context) {
            super(loadedPageIndex, loadedMovies, context);
        }
    }

    public static class NowOnCinemaMoviesDataLoadedEvent extends MoviesDataLoadedEvent {
        public NowOnCinemaMoviesDataLoadedEvent(int loadedPageIndex, List<MovieVO> loadedMovies, Context context) {
            super(loadedPageIndex, loadedMovies, context);
        }
    }

    public static class TopRatedMoviesDataLoadedEvent extends MoviesDataLoadedEvent {
        public TopRatedMoviesDataLoadedEvent(int loadedPageIndex, List<MovieVO> loadedMovies, Context context) {
            super(loadedPageIndex, loadedMovies, context);
        }
    }

    public static class MovieGenresDataLoadedEvent {
        private List<GenreVO> genres;
        private Context context;

        public MovieGenresDataLoadedEvent(List<GenreVO> genres, Context context) {
            this.genres = genres;
            this.context = context;
        }

        public List<GenreVO> getGenres() {
            return genres;
        }

        public Context getContext() {
            return context;
        }
    }

    public static class MovieDetailsDataLoadedEvent {
        private MovieVO mMovie;

        public MovieDetailsDataLoadedEvent(MovieVO mMovie) {
            this.mMovie = mMovie;
        }

        public MovieVO getmMovie() {
            return mMovie;
        }
    }

    public static class MovieTrailersDataLoadedEvent {
        private List<TrailerVO> mTrailers;

        public MovieTrailersDataLoadedEvent(List<TrailerVO> trailers) {
            this.mTrailers = trailers;
        }

        public List<TrailerVO> getmTrailers() {
            return mTrailers;
        }
    }

    public static class MovieReviewsDataLoadedEvent {
        private List<ReviewVO> mReviews;

        public MovieReviewsDataLoadedEvent(List<ReviewVO> mReviews) {
            this.mReviews = mReviews;
        }

        public List<ReviewVO> getReviews() {
            return mReviews;
        }
    }

    public static class MovieCreditsDataLoadedEvent {
        private List<CastVO> movieCasts;

        public MovieCreditsDataLoadedEvent(List<CastVO> movieCasts) {
            this.movieCasts = movieCasts;
        }

        public List<CastVO> getMovieCasts() {
            return movieCasts;
        }
    }

    public static class MovieSimilarDataLoadedEvent {
        private List<MovieVO> movies;

        public MovieSimilarDataLoadedEvent(List<MovieVO> movies) {
            this.movies = movies;
        }

        public List<MovieVO> getMovies() {
            return movies;
        }
    }
}
