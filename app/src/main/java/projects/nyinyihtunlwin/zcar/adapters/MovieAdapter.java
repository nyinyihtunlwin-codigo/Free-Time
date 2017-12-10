package projects.nyinyihtunlwin.zcar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.viewholders.MovieViewHolder;

/**
 * Created by Dell on 11/7/2017.
 */

public class MovieAdapter extends BaseRecyclerAdapter<MovieViewHolder, MovieVO> {

    public MovieAdapter(Context context) {
        super(context);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_movie, parent, false);
        return new MovieViewHolder(view);
    }
}
