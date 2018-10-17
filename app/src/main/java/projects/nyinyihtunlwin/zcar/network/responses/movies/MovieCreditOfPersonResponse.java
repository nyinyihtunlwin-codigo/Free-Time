package projects.nyinyihtunlwin.zcar.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.network.responses.BaseResponse;

public class MovieCreditOfPersonResponse extends BaseResponse {

    @SerializedName("cast")
    private List<MovieVO> casts;
    //crew missing
    @SerializedName("id")
    private Integer id;

    public MovieCreditOfPersonResponse(List<MovieVO> casts, Integer id) {
        this.casts = casts;
        this.id = id;
    }

    public List<MovieVO> getCasts() {
        return casts;
    }

    public void setCasts(List<MovieVO> casts) {
        this.casts = casts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
