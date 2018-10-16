package projects.nyinyihtunlwin.zcar.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.network.responses.BaseResponse;

public class GetSimilarMoviesResponse extends BaseResponse {

    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<MovieVO> movies;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public GetSimilarMoviesResponse(Integer page, List<MovieVO> movies, Integer totalPages, Integer totalResults) {
        this.page = page;
        this.movies = movies;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MovieVO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieVO> results) {
        this.movies = results;
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
