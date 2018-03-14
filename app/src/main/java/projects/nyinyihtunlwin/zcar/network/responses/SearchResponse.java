package projects.nyinyihtunlwin.zcar.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.SearchResultVO;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchResponse extends BaseResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<SearchResultVO> results;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<SearchResultVO> getResults() {
        return results;
    }
}
