package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

public class SimilarTvShowViewHolder extends BaseViewHolder<TvShowVO> {
    @BindView(R.id.iv_movie)
    ImageView ivMovie;

    private MovieItemDelegate mMovieItemDelegate;

    private TvShowVO mData;

    public SimilarTvShowViewHolder(View itemView, MovieItemDelegate movieItemDelegate) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        this.mMovieItemDelegate = movieItemDelegate;
    }

    @Override
    public void setData(TvShowVO mData) {
        this.mData = mData;
        Glide.with(itemView.getRootView().getContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + mData.getPosterPath()).apply(AppConstants.requestOptions).into(ivMovie);
    }

    @Override
    public void onClick(View view) {
        mMovieItemDelegate.onClickMovie(mData.getId());
    }
}
