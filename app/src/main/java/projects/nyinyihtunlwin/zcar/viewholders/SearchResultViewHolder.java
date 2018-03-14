package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.SearchResultVO;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchResultViewHolder extends BaseViewHolder<SearchResultVO> {

    @BindView(R.id.iv_movie)
    ImageView ivMovie;

    private SearchResultVO mData;

    public SearchResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(SearchResultVO mData) {
        this.mData = mData;
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + mData.getPosterPath()).apply(requestOptions).into(ivMovie);
    }

    @Override
    public void onClick(View view) {

    }
}
