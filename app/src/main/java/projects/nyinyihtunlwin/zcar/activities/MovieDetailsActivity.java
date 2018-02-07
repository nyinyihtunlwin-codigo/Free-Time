package projects.nyinyihtunlwin.zcar.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.adapters.GenreAdapter;
import projects.nyinyihtunlwin.zcar.adapters.TrailersAdapter;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

public class MovieDetailsActivity extends BaseActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_MOVIE_ID = "movie_id";
    public static final int MOVIE_DETAILS_LOADER_ID = 1000001;


    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_movie_logo)
    ImageView ivMovieLogo;

    @BindView(R.id.iv_movie_back)
    ImageView ivMovieBack;

    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;

    @BindView(R.id.tv_released_date)
    TextView tvReleasedDate;

    @BindView(R.id.rv_genre)
    RecyclerView rvGenre;

    @BindView(R.id.tv_rate)
    TextView tvRate;

    @BindView(R.id.rv_movie_trailers)
    RecyclerView rvTrailers;

    private String currentMovieId;


    private GenreAdapter mGenreAdapter;
    private TrailersAdapter mTrailersAdapter;

    public static Intent newIntent(Context context, String movieId) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(KEY_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this, this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getStringExtra(KEY_MOVIE_ID) != null) {
            currentMovieId = getIntent().getStringExtra(KEY_MOVIE_ID);
        }

        mGenreAdapter = new GenreAdapter(getApplicationContext());
        rvGenre.setAdapter(mGenreAdapter);
        rvGenre.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvGenre.setHasFixedSize(true);

        mTrailersAdapter = new TrailersAdapter(getApplicationContext());
        rvTrailers.setAdapter(mTrailersAdapter);
        rvTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrailers.setHasFixedSize(true);

        ivBack.setOnClickListener(this);

        getSupportLoaderManager().initLoader(MOVIE_DETAILS_LOADER_ID, null, this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=?",
                new String[]{currentMovieId},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            MovieVO movieVO = MovieVO.parseFromCursor(getApplicationContext(), data);
            bindData(movieVO);
        }
    }

    private void bindData(MovieVO movieVO) {
        tvMovieName.setText(movieVO.getOriginalTitle());
        tvReleasedDate.setText(movieVO.getReleasedDate());
        tvRate.setText(movieVO.getVoteAverage() + "/10");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop();
        Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/original" + movieVO.getBackDropPath()).apply(requestOptions).into(ivMovieBack);
        Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/original" + movieVO.getPosterPath()).apply(requestOptions).into(ivMovieLogo);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
