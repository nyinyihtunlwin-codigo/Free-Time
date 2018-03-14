package projects.nyinyihtunlwin.zcar.mvp.views;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.SearchResultVO;

/**
 * Created by Dell on 3/14/2018.
 */

public interface SearchView {

    void displaySearchResults(List<SearchResultVO> resultList);

    void showLoding();

    void navigateToDetails(String movieId);
}
