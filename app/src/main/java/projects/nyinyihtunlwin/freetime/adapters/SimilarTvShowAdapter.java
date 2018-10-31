package projects.nyinyihtunlwin.freetime.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.freetime.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.freetime.viewholders.SimilarTvShowViewHolder;


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
