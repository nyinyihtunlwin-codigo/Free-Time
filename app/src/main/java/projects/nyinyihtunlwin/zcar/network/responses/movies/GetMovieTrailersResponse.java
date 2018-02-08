package projects.nyinyihtunlwin.zcar.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.TrailerVO;
import projects.nyinyihtunlwin.zcar.network.responses.BaseResponse;

/**
 * Created by Dell on 2/8/2018.
 */

public class GetMovieTrailersResponse extends BaseResponse {

    @SerializedName("id")
    private Integer id;

    @SerializedName("results")
    private List<TrailerVO> videos;

    public Integer getId() {
        return id;
    }

    public List<TrailerVO> getVideos() {
        return videos;
    }
}
