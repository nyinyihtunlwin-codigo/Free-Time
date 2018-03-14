package projects.nyinyihtunlwin.zcar.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchResultVO {

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("id")
    private Integer id;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    public String getMediaType() {
        return mediaType;
    }

    public Integer getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
