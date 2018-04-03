package projects.nyinyihtunlwin.zcar.mvp.presenters;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import projects.nyinyihtunlwin.zcar.data.models.SearchResultModel;
import projects.nyinyihtunlwin.zcar.events.SearchEvents;
import projects.nyinyihtunlwin.zcar.mvp.views.SearchView;
import projects.nyinyihtunlwin.zcar.utils.ConfigUtils;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchPresenter extends BasePresenter<SearchView> {

    private Context mContext;
    private String mQuery;

    public SearchPresenter(Context context) {
        this.mContext = context;
    }

    public void onTapSearch(String query) {
        mView.showLoding();
        mQuery = query;
        SearchResultModel.getInstance().startSearching(query);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onLoadedSearchResults(SearchEvents.SearchResultsDataLoadedEvent event) {
        ConfigUtils.getObjInstance().saveSearchResultPageIndex(event.getLoadedPageIndex());
        mView.displaySearchResults(event.getLoadedSearchResults());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }


    public void onTapResult(String movieId, String mediaType) {
        mView.navigateToDetails(movieId,mediaType);
    }

    public void onResultListEndReached() {
        SearchResultModel.getInstance().loadMoreResults(mQuery);
    }
}
