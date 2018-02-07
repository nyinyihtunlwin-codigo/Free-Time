package projects.nyinyihtunlwin.zcar.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.adapters.GenreAdapter;
import projects.nyinyihtunlwin.zcar.adapters.TrailersAdapter;
import projects.nyinyihtunlwin.zcar.data.vo.GenreVO;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

public class MovieDetailsActivity extends BaseActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_MOVIE_ID = "movie_id";

    public static final int MOVIE_DETAILS_LOADER_ID = 1000001;
    public static final int MOVIE_GENRES_LOADER_ID = 1000002;


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

    @BindView(R.id.tv_overview)
    TextView tvOverview;

    @BindView(R.id.rv_movie_trailers)
    RecyclerView rvTrailers;

    @BindView(R.id.tv_title_movie_name)
    TextView tvTitleMovieName;

    private String currentMovieId;
    private List<Integer> currentGenreIds;


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
        currentGenreIds = new ArrayList<>();

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

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    tvTitleMovieName.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    tvTitleMovieName.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });
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
        CursorLoader cursorLoader = null;
        switch (id) {
            case MOVIE_DETAILS_LOADER_ID:
                cursorLoader = new CursorLoader(getApplicationContext(),
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=?",
                        new String[]{currentMovieId},
                        null);
                break;
            case MOVIE_GENRES_LOADER_ID:
                cursorLoader = new CursorLoader(getApplicationContext(),
                        MovieContract.MovieGenreEntry.CONTENT_URI,
                        null,
                        MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + "=?",
                        new String[]{String.valueOf(currentMovieId)},
                        null);
                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            switch (loader.getId()) {
                case MOVIE_DETAILS_LOADER_ID:
                    MovieVO movieVO = MovieVO.parseFromCursor(getApplicationContext(), data);
                    bindData(movieVO);
                    break;
                case MOVIE_GENRES_LOADER_ID:
                    List<GenreVO> genreList = new ArrayList<>();
                    do {
                        GenreVO genreVO = GenreVO.parseFromCursor(data);
                        genreList.add(genreVO);
                    } while (data.moveToNext());
                    bindGenres(genreList);
                    break;
            }
        }
    }

    private void bindGenres(List<GenreVO> genreList) {
        mGenreAdapter.appendNewData(genreList);
    }

    private void bindData(MovieVO movieVO) {
        getSupportLoaderManager().initLoader(MOVIE_GENRES_LOADER_ID, null, this);
        tvTitleMovieName.setText(movieVO.getTitle());
        tvMovieName.setText(movieVO.getOriginalTitle());
        tvReleasedDate.setText(movieVO.getReleasedDate());
        tvRate.setText(movieVO.getVoteAverage() + "/10");
        tvOverview.setText(movieVO.getOverview());
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
