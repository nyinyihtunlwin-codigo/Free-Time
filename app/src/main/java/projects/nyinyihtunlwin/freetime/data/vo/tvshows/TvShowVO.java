package projects.nyinyihtunlwin.freetime.data.vo.tvshows;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.freetime.persistence.MovieContract;

/**
 * Created by Dell on 2/22/2018.
 */

public class TvShowVO {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("vote_count")
    private Integer voteCount;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("origin_country")
    private List<String> originCountries;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("number_of_episodes")
    private Integer numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private Integer numberOfSeasons;

    @SerializedName("status")
    private String status;

    @SerializedName("networks")
    private List<Network> networks;

    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public Double getPopularity() {
        return popularity;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public List<String> getOriginCountries() {
        return originCountries;
    }


    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOriginCountries(List<String> originCountries) {
        this.originCountries = originCountries;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_TV_SHOWS_ID, id);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_VOTE_COUNT, voteCount);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_ORIGINAL_NAME, originalName);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_NAME, name);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_POPULARITY, popularity);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_POSTER_PATH, posterPath);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_ORIGINAL_LANGUAGE, originalLanguage);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_BACKDROP_PATH, backdropPath);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_OVERVIEW, overview);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_FIRST_AIR_DATE, firstAirDate);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_LAST_AIR_DATE, lastAirDate);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_STATUS, status);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_NO_OF_EPISODES, numberOfEpisodes);
        contentValues.put(MovieContract.TvShowsEntry.COLUMN_NO_OF_SEASONS, numberOfSeasons);
        return contentValues;
    }

    public static TvShowVO parseFromCursor(Context context, Cursor cursor) {
        TvShowVO tvShow = new TvShowVO();
        tvShow.id = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_TV_SHOWS_ID));
        tvShow.voteCount = cursor.getInt(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_VOTE_COUNT));
        tvShow.voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_VOTE_AVERAGE));
        tvShow.name = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_NAME));
        tvShow.popularity = cursor.getDouble(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_POPULARITY));
        tvShow.posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_POSTER_PATH));
        tvShow.originalLanguage = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_ORIGINAL_LANGUAGE));
        tvShow.originalName = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_ORIGINAL_NAME));
        tvShow.backdropPath = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_BACKDROP_PATH));
        tvShow.overview = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_OVERVIEW));
        tvShow.firstAirDate = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_FIRST_AIR_DATE));
        tvShow.lastAirDate = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_LAST_AIR_DATE));
        tvShow.status = cursor.getString(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_STATUS));
        tvShow.numberOfEpisodes = cursor.getInt(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_NO_OF_EPISODES));
        tvShow.numberOfSeasons = cursor.getInt(cursor.getColumnIndex(MovieContract.TvShowsEntry.COLUMN_NO_OF_SEASONS));
        tvShow.genreIds = loadGenresInMovie(context, tvShow.id);
        return tvShow;
    }

    private static List<Integer> loadGenresInMovie(Context context, String movieId) {
        Cursor genresInTvShowCursor = context.getContentResolver().query(MovieContract.TvShowGenreEntry.CONTENT_URI,
                null,
                MovieContract.TvShowGenreEntry.COLUMN_TV_SHOW_ID + " = ?", new String[]{movieId},
                null);

        if (genresInTvShowCursor != null && genresInTvShowCursor.moveToFirst()) {
            List<Integer> genresInMovies = new ArrayList<>();
            do {
                genresInMovies.add(
                        genresInTvShowCursor.getInt(
                                genresInTvShowCursor.getColumnIndex(MovieContract.TvShowGenreEntry.COLUMN_GENRE_ID)
                        )
                );
            } while (genresInTvShowCursor.moveToNext());
            genresInTvShowCursor.close();
            return genresInMovies;
        }
        return null;
    }

}
