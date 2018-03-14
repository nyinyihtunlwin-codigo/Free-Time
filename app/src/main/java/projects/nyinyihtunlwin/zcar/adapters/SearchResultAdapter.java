package projects.nyinyihtunlwin.zcar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.SearchResultVO;
import projects.nyinyihtunlwin.zcar.viewholders.SearchResultViewHolder;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchResultAdapter extends BaseRecyclerAdapter<SearchResultViewHolder, SearchResultVO> {

    public SearchResultAdapter(Context context) {
        super(context);
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_grid_movie, parent, false);
        return new SearchResultViewHolder(view);
    }
}
