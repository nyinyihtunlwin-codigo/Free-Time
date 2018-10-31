package projects.nyinyihtunlwin.freetime.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.data.vo.SearchResultVO;
import projects.nyinyihtunlwin.freetime.delegates.SearchResultDelegate;
import projects.nyinyihtunlwin.freetime.viewholders.SearchResultViewHolder;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchResultAdapter extends BaseRecyclerAdapter<SearchResultViewHolder, SearchResultVO> {

    private SearchResultDelegate mSearchResultDelegate;

    public SearchResultAdapter(Context context, SearchResultDelegate searchResultDelegate) {
        super(context);
        this.mSearchResultDelegate = searchResultDelegate;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_list_movie, parent, false);
        return new SearchResultViewHolder(view,mSearchResultDelegate);
    }
}
