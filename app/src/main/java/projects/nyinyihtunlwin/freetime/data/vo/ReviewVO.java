package projects.nyinyihtunlwin.freetime.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 2/8/2018.
 */

public class ReviewVO {

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
