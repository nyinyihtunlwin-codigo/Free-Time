package projects.nyinyihtunlwin.zcar.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.network.responses.BaseResponse;

public class TVCreditOfPersonResponse extends BaseResponse {
    @SerializedName("cast")
    private List<TvShowVO> casts;
    //crew missing
    @SerializedName("id")
    private Integer id;

    public TVCreditOfPersonResponse(List<TvShowVO> casts, Integer id) {
        this.casts = casts;
        this.id = id;
    }

    public List<TvShowVO> getCasts() {
        return casts;
    }

    public void setCasts(List<TvShowVO> casts) {
        this.casts = casts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
