package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;

/**
 * Created by Nyi Nyi Htun Lwin on 11/7/2017.
 */

public class MovieViewHolder extends BaseViewHolder<MovieVO> {

/*    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;*/

/*    @BindView(R.id.tv_rate)
    TextView tvRate;*/

    @BindView(R.id.iv_movie)
    ImageView ivMovie;
/*
    @BindView(R.id.rb_movie)
    AppCompatRatingBar rbMovie;

    @BindView(R.id.tv_released_date)
    TextView tvReleasedDate;*/

    public MovieViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(MovieVO mData) {
        //   tvMovieName.setText(mData.getTitle());
        //  tvRate.setText(mData.getVoteAverage() + "");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load("https://image.tmdb.org/t/p/original" + mData.getPosterPath()).apply(requestOptions).into(ivMovie);
//        Log.e("path", mData.getPosterPath());
        float popularity = mData.getPopularity() / 200;
        //   tvReleasedDate.setText(mData.getReleasedDate());
        //   rbMovie.setRating(popularity);
    }

    @Override
    public void onClick(View view) {

    }
}

