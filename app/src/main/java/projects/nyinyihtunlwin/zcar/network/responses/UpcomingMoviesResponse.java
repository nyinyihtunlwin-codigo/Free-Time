package projects.nyinyihtunlwin.zcar.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;

/**
 * Created by hitanshu on 28/7/17.
 */

public class UpcomingMoviesResponse extends BaseResponse{

    @SerializedName("results")
    private List<MovieVO> movies;
    @SerializedName("page")
    private Integer page;
    @SerializedName("total_results")
    private Integer totalResults;
    @SerializedName("total_pages")
    private Integer totalPages;

    public UpcomingMoviesResponse(List<MovieVO> movies, Integer page, Integer totalResults, Integer totalPages) {
        this.movies = movies;
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public List<MovieVO> getMovies() {
        return movies;
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
}
