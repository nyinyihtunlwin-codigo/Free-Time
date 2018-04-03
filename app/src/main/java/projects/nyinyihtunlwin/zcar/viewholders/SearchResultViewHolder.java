package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.SearchResultVO;
import projects.nyinyihtunlwin.zcar.delegates.SearchResultDelegate;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchResultViewHolder extends BaseViewHolder<SearchResultVO> {

    @BindView(R.id.iv_movie)
    ImageView ivMovie;

    @BindView(R.id.tv_type)
    TextView tvType;

    private SearchResultVO mData;
    private SearchResultDelegate mSearchResultDelegate;

    public SearchResultViewHolder(View itemView, SearchResultDelegate searchResultDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mSearchResultDelegate = searchResultDelegate;
    }

    @Override
    public void setData(SearchResultVO mData) {
        this.mData = mData;
        tvType.setText(getType(mData.getMediaType()));
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + mData.getPosterPath()).apply(requestOptions).into(ivMovie);
    }

    @Override
    public void onClick(View view) {
        mSearchResultDelegate.onClickResultItems(String.valueOf(mData.getId()), mData.getMediaType());
    }

    public String getType(String type) {
        switch (type) {
            case AppConstants.TYPE_SEARCH_MOVIE:
                return "Movie";
            case AppConstants.TYPE_SEARCH_TV_SHOW:
                return "TV Show";
            case AppConstants.TYPE_SEARCH_PERSON:
                return "Person";
            default:
                return "Movie";
        }
    }
}
