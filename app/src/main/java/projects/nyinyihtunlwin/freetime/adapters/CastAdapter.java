package projects.nyinyihtunlwin.freetime.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.data.vo.CastVO;
import projects.nyinyihtunlwin.freetime.delegates.MovieDetailsDelegate;
import projects.nyinyihtunlwin.freetime.viewholders.CastViewHolder;

/**
 * Created by Dell on 2/8/2018.
 */

public class CastAdapter extends BaseRecyclerAdapter<CastViewHolder, CastVO> {

    private MovieDetailsDelegate mMovieDetailsDelegate;

    public CastAdapter(Context context, MovieDetailsDelegate movieDetailsDelegate) {
        super(context);
        this.mMovieDetailsDelegate=movieDetailsDelegate;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_cast, parent, false);
        return new CastViewHolder(view,mMovieDetailsDelegate);
    }
}
