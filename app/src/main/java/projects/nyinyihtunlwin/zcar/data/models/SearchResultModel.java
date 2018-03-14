package projects.nyinyihtunlwin.zcar.data.models;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.SearchResultVO;
import projects.nyinyihtunlwin.zcar.network.MovieDataAgentImpl;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;
import projects.nyinyihtunlwin.zcar.utils.ConfigUtils;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchResultModel {

    private static SearchResultModel objectInstance;

    private String mQuery;

    private SearchResultModel() {
        EventBus.getDefault().register(this);
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
        if (mQuery != null) {
            MovieDataAgentImpl.getObjectInstance().startSearching(AppConstants.API_KEY, ConfigUtils.getObjInstance().loadSearchResultPageIndex(), mQuery);
        }
    }
}
