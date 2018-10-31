package projects.nyinyihtunlwin.freetime.data.models;

import projects.nyinyihtunlwin.freetime.network.MovieDataAgentImpl;
import projects.nyinyihtunlwin.freetime.utils.AppConstants;
import projects.nyinyihtunlwin.freetime.utils.ConfigUtils;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchResultModel {

    private static SearchResultModel objectInstance;

    private String mQuery;

    private SearchResultModel() {
    }

    public static SearchResultModel getInstance() {
        if (objectInstance == null) {
            objectInstance = new SearchResultModel();
        }
        return objectInstance;
    }

    public void startSearching(String query) {
        ConfigUtils.getObjInstance().saveMovieTopRatedPageIndex(1);
        MovieDataAgentImpl.getObjectInstance().startSearching(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadSearchResultPageIndex(), query);
        mQuery = query;
    }

    public void loadMoreResults(String mQuery) {
        ConfigUtils.getObjInstance().saveSearchResultPageIndex(ConfigUtils.getObjInstance().loadSearchResultPageIndex() + 1);
        if (mQuery != null) {
            MovieDataAgentImpl.getObjectInstance().startSearching(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadSearchResultPageIndex(), mQuery);
        }
    }
}
