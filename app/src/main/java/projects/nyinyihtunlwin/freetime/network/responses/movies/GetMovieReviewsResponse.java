package projects.nyinyihtunlwin.freetime.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.freetime.data.vo.ReviewVO;
import projects.nyinyihtunlwin.freetime.network.responses.BaseResponse;

/**
 * Created by Dell on 2/8/2018.
 */

public class GetMovieReviewsResponse extends BaseResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<ReviewVO> reviews;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public List<ReviewVO> getReviews() {
        return reviews;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
