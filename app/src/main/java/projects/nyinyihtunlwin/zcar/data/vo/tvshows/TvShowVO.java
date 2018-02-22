package projects.nyinyihtunlwin.zcar.data.vo.tvshows;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dell on 2/22/2018.
 */

public class TvShowVO {

    @SerializedName("id")
    private Integer id;

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

    public Integer getId() {
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
}
