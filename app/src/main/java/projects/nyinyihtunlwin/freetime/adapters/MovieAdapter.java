package projects.nyinyihtunlwin.freetime.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.freetime.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.freetime.viewholders.MovieViewHolder;


/**
 * Created by Nyi Nyi Htun Lwin on 11/7/2017.
 */

public class MovieAdapter extends BaseRecyclerAdapter<MovieViewHolder, MovieVO> {

    private MovieItemDelegate mMovieItemDelegate;

    public MovieAdapter(Context context, MovieItemDelegate movieItemDelegate) {
        super(context);
        this.mMovieItemDelegate = movieItemDelegate;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_grid_movie, parent, false);
        return new MovieViewHolder(view,mMovieItemDelegate);
    }
}
