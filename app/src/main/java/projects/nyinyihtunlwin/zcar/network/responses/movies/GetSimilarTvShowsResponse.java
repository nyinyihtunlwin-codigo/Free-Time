package projects.nyinyihtunlwin.zcar.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.network.responses.BaseResponse;

public class GetSimilarTvShowsResponse extends BaseResponse {
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<TvShowVO> tvShows;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public GetSimilarTvShowsResponse(Integer page, List<TvShowVO> tvShows, Integer totalPages, Integer totalResults) {
        this.page = page;
        this.tvShows = tvShows;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<TvShowVO> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<TvShowVO> tvShows) {
        this.tvShows = tvShows;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
