package projects.nyinyihtunlwin.zcar.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 2/8/2018.
 */

public class CastVO {
    @SerializedName("cast_id")
    private Integer castId;

    @SerializedName("character")
    private String character;

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("gender")
    private Integer gender;

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("order")
    private Integer order;

    @SerializedName("profile_path")
    private String profilePath;

    public Integer getCastId() {
        return castId;
    }

    public String getCharacter() {
        return character;
    }

    public String getCreditId() {
        return creditId;
    }

    public Integer getGender() {
        return gender;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getOrder() {
        return order;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
