package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

public class SimilarMovieViewHolder extends BaseViewHolder<MovieVO> {
    @BindView(R.id.iv_movie)
    ImageView ivMovie;

    private MovieItemDelegate mMovieItemDelegate;

    private MovieVO mData;

    public SimilarMovieViewHolder(View itemView, MovieItemDelegate movieItemDelegate) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        this.mMovieItemDelegate = movieItemDelegate;
    }

    @Override
    public void setData(MovieVO mData) {
        this.mData = mData;
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + mData.getPosterPath()).apply(requestOptions).into(ivMovie);
    }

    @Override
    public void onClick(View view) {
        mMovieItemDelegate.onClickMovie(mData.getId());
    }
}