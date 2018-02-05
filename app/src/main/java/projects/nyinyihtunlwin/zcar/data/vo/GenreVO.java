package projects.nyinyihtunlwin.zcar.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 2/5/2018.
 */

public class GenreVO {


    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String genreName;

    public GenreVO(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
