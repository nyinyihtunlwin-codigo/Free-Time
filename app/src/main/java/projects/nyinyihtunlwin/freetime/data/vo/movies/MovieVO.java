package projects.nyinyihtunlwin.freetime.data.vo.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.freetime.persistence.MovieContract;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public class MovieVO {

    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("id")
    private String id;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @SerializedName("backdrop_path")
    private String backDropPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releasedDate;


    @SerializedName("budget")
    private Integer budget;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("revenue")
    private Integer revenue;
    @SerializedName("runtime")
    private Integer runtime;
    @SerializedName("status")
    private String status;
    @SerializedName("tagline")
    private String tagline;


    public int getVoteCount() {
        return voteCount;
    }

    public String getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public Integer getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, id);
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, voteCount);
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        contentValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, video);
        contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle);
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, title);
        contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, popularity);
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, posterPath);
        contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, originalLanguage);
        contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, backDropPath);
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview);
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releasedDate);
        contentValues.put(MovieContract.MovieEntry.COLUMN_ADULT, adult);
        return contentValues;
    }

    public static MovieVO parseFromCursor(Context context, Cursor cursor) {
        MovieVO movie = new MovieVO();
        movie.id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
        movie.voteCount = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT));
        movie.video = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VIDEO)) > 0;
        movie.voteAverage = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));
        movie.title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
        movie.popularity = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY));
        movie.posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
        movie.originalLanguage = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE));
        movie.originalTitle = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE));
        movie.backDropPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH));
        movie.adult = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ADULT)) > 0;
        movie.overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
        movie.releasedDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
        movie.genreIds = loadGenresInMovie(context, movie.id);
        return movie;
    }

    private static List<Integer> loadGenresInMovie(Context context, String movieId) {
        Cursor genresInMovieCursor = context.getContentResolver().query(MovieContract.MovieGenreEntry.CONTENT_URI,
                null,
                MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + " = ?", new String[]{movieId},
                null);

        if (genresInMovieCursor != null && genresInMovieCursor.moveToFirst()) {
            List<Integer> genresInMovies = new ArrayList<>();
            do {
                genresInMovies.add(
                        genresInMovieCursor.getInt(
                                genresInMovieCursor.getColumnIndex(MovieContract.MovieGenreEntry.COLUMN_GENRE_ID)
                        )
                );
            } while (genresInMovieCursor.moveToNext());
            genresInMovieCursor.close();
            return genresInMovies;
        }
        return null;
    }
}
