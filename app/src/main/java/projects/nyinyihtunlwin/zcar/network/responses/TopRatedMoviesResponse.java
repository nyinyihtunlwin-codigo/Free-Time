package projects.nyinyihtunlwin.zcar.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;

/**
 * Created by hitanshu on 31/7/17.
 */

public class TopRatedMoviesResponse extends BaseResponse{

    @SerializedName("page")
    private Integer page;
    @SerializedName("total_results")
    private Integer totalResults;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("results")
    private List<MovieVO> movies;

    public TopRatedMoviesResponse(Integer page, Integer totalResults, Integer totalPages, List<MovieVO> movies) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.movies = movies;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<MovieVO> getMovies() {
        return movies;
    }
}
