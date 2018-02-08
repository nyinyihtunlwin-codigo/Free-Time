package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.TrailerVO;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 2/6/2018.
 */

public class TrailersViewHolder extends BaseViewHolder<TrailerVO> {

    @BindView(R.id.iv_trailer)
    ImageView ivTrailer;

    @BindView(R.id.tv_trailer_name)
    TextView tvTrailerName;

    public TrailersViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(TrailerVO mData) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load(AppConstants.IMAGE_TRAILERS_BASE_URL + mData.getKey() + AppConstants.IMAGE_TRAILERS_QUALITY).apply(requestOptions).into(ivTrailer);
        if (mData != null) {
            tvTrailerName.setVisibility(View.VISIBLE);
            tvTrailerName.setText(mData.getName());
        } else {
            tvTrailerName.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
