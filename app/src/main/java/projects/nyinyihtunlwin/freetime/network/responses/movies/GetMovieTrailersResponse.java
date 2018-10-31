package projects.nyinyihtunlwin.freetime.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.freetime.data.vo.TrailerVO;
import projects.nyinyihtunlwin.freetime.network.responses.BaseResponse;

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
