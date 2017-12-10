package projects.nyinyihtunlwin.zcar.viewholders;

import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;

/**
 * Created by Dell on 11/7/2017.
 */

public class MovieViewHolder extends BaseViewHolder<MovieVO> {

    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;

    @BindView(R.id.tv_rate)
    TextView tvRate;

    @BindView(R.id.iv_movie)
    ImageView ivMovie;

    @BindView(R.id.rb_movie)
    AppCompatRatingBar rbMovie;

    public MovieViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(MovieVO mData) {
        tvMovieName.setText(mData.getTitle());
        tvRate.setText(mData.getVoteAverage() + "");
   /*     RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_movie_viewholder_black_24dp)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load("https://image.tmdb.org/t/p/original" + mData.getPosterPath()).apply(requestOptions).into(ivMovie);
       */ Log.e("path", mData.getPosterPath());
        float popularity = mData.getPopularity() / 200;
        rbMovie.setRating(popularity);
    }

    @Override
    public void onClick(View view) {

    }
}

