package projects.nyinyihtunlwin.freetime.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.data.vo.TrailerVO;
import projects.nyinyihtunlwin.freetime.delegates.MovieDetailsDelegate;
import projects.nyinyihtunlwin.freetime.viewholders.TrailersViewHolder;

/**
 * Created by Dell on 2/6/2018.
 */

public class TrailersAdapter extends BaseRecyclerAdapter<TrailersViewHolder, TrailerVO> {

    private MovieDetailsDelegate mMovieDetailsDelegate;

    public TrailersAdapter(Context context, MovieDetailsDelegate movieDetailsDelegate) {
        super(context);
        this.mMovieDetailsDelegate = movieDetailsDelegate;
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_movie_trailer, parent, false);
        return new TrailersViewHolder(view, mMovieDetailsDelegate);
    }
}
