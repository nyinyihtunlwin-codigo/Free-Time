package projects.nyinyihtunlwin.zcar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.zcar.viewholders.TvShowViewHolder;

/**
 * Created by Dell on 3/23/2018.
 */

public class TvShowAdapter extends BaseRecyclerAdapter<TvShowViewHolder, TvShowVO> {

    private MovieItemDelegate mMovieItemDelegate;

    public TvShowAdapter(Context context, MovieItemDelegate movieItemDelegate) {
        super(context);
        this.mMovieItemDelegate = movieItemDelegate;
    }

    @Override
    public TvShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_grid_movie, parent, false);
        return new TvShowViewHolder(view, mMovieItemDelegate);
    }
}
