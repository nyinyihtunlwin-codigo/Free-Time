package projects.nyinyihtunlwin.zcar.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.CastVO;
import projects.nyinyihtunlwin.zcar.data.vo.CrewVO;
import projects.nyinyihtunlwin.zcar.network.responses.BaseResponse;

/**
 * Created by Dell on 2/8/2018.
 */

public class GetMovieCreditsResponse extends BaseResponse {

    @SerializedName("id")
    private Integer id;

    @SerializedName("cast")
    private List<CastVO> casts;

    @SerializedName("crew")
    private List<CrewVO> crews;

    public Integer getId() {
        return id;
    }

    public List<CastVO> getCasts() {
        return casts;
    }

    public List<CrewVO> getCrews() {
        return crews;
    }
}
