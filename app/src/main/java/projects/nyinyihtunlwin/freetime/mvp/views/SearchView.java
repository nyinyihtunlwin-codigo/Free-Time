package projects.nyinyihtunlwin.freetime.mvp.views;

import java.util.List;

import projects.nyinyihtunlwin.freetime.data.vo.SearchResultVO;

/**
 * Created by Dell on 3/14/2018.
 */

public interface SearchView {

    void displaySearchResults(List<SearchResultVO> resultList);

    void showLoding();

    void navigateToDetails(String movieId,String mediaType);

    void showErrorMsg(String message);
}
