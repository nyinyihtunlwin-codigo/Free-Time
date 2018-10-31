package projects.nyinyihtunlwin.freetime.network.responses;

import com.google.gson.annotations.SerializedName;

public class PersonDetailsResponse {

    @SerializedName("birthday")
    private String dateOfBirth;

    @SerializedName("deathday")
    private String dateOfDeath;

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private Integer gender;

    @SerializedName("biography")
    private String biography;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("place_of_birth")
    private String placeOfBirth;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("adult")
    private Boolean adult;

    @SerializedName("imdb_id")
    private String imdbId;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getGender() {
        return gender;
    }

    public String getBiography() {
        return biography;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getImdbId() {
        return imdbId;
    }
}
