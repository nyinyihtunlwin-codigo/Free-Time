package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.CastVO;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 2/8/2018.
 */

public class CastViewHolder extends BaseViewHolder<CastVO> {

    @BindView(R.id.iv_cast)
    ImageView ivCast;

    @BindView(R.id.tv_cast_name)
    TextView tvCastName;

    public CastViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(CastVO mData) {
        tvCastName.setText(mData.getName());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + mData.getProfilePath()).apply(requestOptions).into(ivCast);

    }

    @Override
    public void onClick(View view) {

    }
}
