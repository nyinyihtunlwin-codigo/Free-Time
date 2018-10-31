package projects.nyinyihtunlwin.freetime.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.data.vo.CastVO;
import projects.nyinyihtunlwin.freetime.delegates.MovieDetailsDelegate;
import projects.nyinyihtunlwin.freetime.utils.AppConstants;

/**
 * Created by Dell on 2/8/2018.
 */

public class CastViewHolder extends BaseViewHolder<CastVO> {

    @BindView(R.id.iv_cast)
    ImageView ivCast;

    @BindView(R.id.tv_cast_name)
    TextView tvCastName;

    private MovieDetailsDelegate mMovieDetailsDelegate;
    private CastVO mData;

    public CastViewHolder(View itemView, MovieDetailsDelegate movieDetailsDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mMovieDetailsDelegate = movieDetailsDelegate;
    }

    @Override
    public void setData(CastVO data) {
        this.mData = data;
        tvCastName.setText(mData.getName());
        Glide.with(itemView.getRootView().getContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + mData.getProfilePath()).apply(AppConstants.requestOptions).into(ivCast);

    }

    @Override
    public void onClick(View view) {
        if (mData.getId() != null) {
            mMovieDetailsDelegate.onClickCast(mData.getId());
        }
    }
}
