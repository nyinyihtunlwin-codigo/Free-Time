package projects.nyinyihtunlwin.freetime.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 2/8/2018.
 */

public class CrewVO {

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("department")
    private String department;

    @SerializedName("gender")
    private Integer gender;

    @SerializedName("id")
    private Integer id;

    @SerializedName("job")
    private String job;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profilePath;

    public String getCreditId() {
        return creditId;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getGender() {
        return gender;
    }

    public Integer getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
