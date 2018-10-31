package projects.nyinyihtunlwin.freetime.network.responses.tvshows;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.freetime.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.freetime.network.responses.BaseResponse;

/**
 * Created by Dell on 2/22/2018.
 */

public class TvOnTheAirResponse extends BaseResponse{

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<TvShowVO> tvShows;

    public Integer getPage() {
        return page;
    }

    public List<TvShowVO> getTvShows() {
        return tvShows;
    }
}
