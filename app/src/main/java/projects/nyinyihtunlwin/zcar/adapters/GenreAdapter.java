package projects.nyinyihtunlwin.zcar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.GenreVO;
import projects.nyinyihtunlwin.zcar.viewholders.GenreViewHolder;

/**
 * Created by Dell on 2/6/2018.
 */

public class GenreAdapter extends BaseRecyclerAdapter<GenreViewHolder, GenreVO> {

    public GenreAdapter(Context context) {
        super(context);
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_genre, parent, false);
        return new GenreViewHolder(view);
    }
}
