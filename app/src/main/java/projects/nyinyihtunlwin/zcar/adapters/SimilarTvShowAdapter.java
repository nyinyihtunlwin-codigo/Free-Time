package projects.nyinyihtunlwin.zcar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.zcar.viewholders.SimilarTvShowViewHolder;


/**
 * Created by Nyi Nyi Htun Lwin on 11/7/2017.
 */

public class SimilarTvShowAdapter extends BaseRecyclerAdapter<SimilarTvShowViewHolder, TvShowVO> {

    private MovieItemDelegate mMovieItemDelegate;

    public SimilarTvShowAdapter(Context context, MovieItemDelegate movieItemDelegate) {
        super(context);
        this.mMovieItemDelegate = movieItemDelegate;
    }

    @Override
    public SimilarTvShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_similar_movie, parent, false);
        return new SimilarTvShowViewHolder(view, mMovieItemDelegate);
    }
}
