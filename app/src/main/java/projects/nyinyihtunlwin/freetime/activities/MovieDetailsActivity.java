package projects.nyinyihtunlwin.freetime.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.adapters.CastAdapter;
import projects.nyinyihtunlwin.freetime.adapters.GenreAdapter;
import projects.nyinyihtunlwin.freetime.adapters.SimilarMovieAdapter;
import projects.nyinyihtunlwin.freetime.adapters.TrailersAdapter;
import projects.nyinyihtunlwin.freetime.data.models.MovieModel;
import projects.nyinyihtunlwin.freetime.data.vo.GenreVO;
import projects.nyinyihtunlwin.freetime.data.vo.ReviewVO;
import projects.nyinyihtunlwin.freetime.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.freetime.delegates.MovieDetailsDelegate;
import projects.nyinyihtunlwin.freetime.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.freetime.events.MovieDetailsEvent;
import projects.nyinyihtunlwin.freetime.events.MoviesiEvents;
import projects.nyinyihtunlwin.freetime.persistence.MovieContract;
import projects.nyinyihtunlwin.freetime.utils.AppConstants;
import projects.nyinyihtunlwin.freetime.utils.AppUtils;

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsDelegate, View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

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

    @BindView(R.id.ll_time)
    LinearLayout llTime;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_trailers)
    TextView tvTrailers;

    @BindView(R.id.avi)
    AVLoadingIndicatorView loadingView;

    @BindView(R.id.tv_reviews)
    TextView tvReviews;

    @BindView(R.id.ll_reviews)
    LinearLayout llReviews;

    @BindView(R.id.rv_movie_casts)
    RecyclerView rvMovieCasts;

    @BindView(R.id.tv_casts)
    TextView tvCasts;

    @BindView(R.id.iv_share)
    ImageView ivShare;

    @BindView(R.id.tv_similar_movies)
    TextView tvSimilarMovies;

    @BindView(R.id.rv_movie_similar)
    RecyclerView rvSimilarMovies;

    private String currentMovieId;
    private List<Integer> currentGenreIds;


    private GenreAdapter mGenreAdapter;
    private TrailersAdapter mTrailersAdapter;
    private CastAdapter mCastAdapter;
    private SimilarMovieAdapter mSimilarMoviesAdapter;

    private String movieTagline, imdbId, homepage;

    private Snackbar mSnackbar;

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

        loadingView.setVisibility(View.VISIBLE);

        if (getIntent().getStringExtra(KEY_MOVIE_ID) != null) {
            currentMovieId = getIntent().getStringExtra(KEY_MOVIE_ID);
            loadDetails();
        }
        currentGenreIds = new ArrayList<>();

        mGenreAdapter = new GenreAdapter(getApplicationContext());
        rvGenre.setAdapter(mGenreAdapter);
        rvGenre.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvGenre.setHasFixedSize(true);

        mTrailersAdapter = new TrailersAdapter(getApplicationContext(), this);
        rvTrailers.setAdapter(mTrailersAdapter);
        rvTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrailers.setHasFixedSize(true);

        mCastAdapter = new CastAdapter(getApplicationContext(), this);
        rvMovieCasts.setAdapter(mCastAdapter);
        rvMovieCasts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvMovieCasts.setHasFixedSize(true);

        mSimilarMoviesAdapter = new SimilarMovieAdapter(getApplicationContext(), new MovieItemDelegate() {
            @Override
            public void onClickMovie(String movieId) {
                startActivity(MovieDetailsActivity.newIntent(getApplicationContext(), movieId));
            }
        });
        rvSimilarMovies.setAdapter(mSimilarMoviesAdapter);
        rvSimilarMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvSimilarMovies.setHasFixedSize(true);

        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);

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

    private void loadDetails() {
        if (AppUtils.getObjInstance().hasConnection()) {
            MovieModel.getInstance().startLoadingMovieDetails(currentMovieId);
            MovieModel.getInstance().startLoadingMovieTrailers(currentMovieId);
            MovieModel.getInstance().startLoadingMovieReviews(currentMovieId);
            MovieModel.getInstance().startLoadingMovieCredits(currentMovieId);
            MovieModel.getInstance().startLoadingSimilarMovies(currentMovieId);
        } else {
            showSnackBar("No internet connection.");
            loadingView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_share:
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent movieShareIntent = new Intent(Intent.ACTION_SEND);
                movieShareIntent.setType("text/plain");
                String extraText = "";
                extraText += tvTitleMovieName.getText().toString() + "\n";
                if (movieTagline != null) extraText += movieTagline + "\n";
                if (imdbId != null) extraText += AppConstants.IMDB_BASE_URL + imdbId + "\n";
                if (homepage != null) extraText += homepage;
                movieShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(movieShareIntent);
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
        mGenreAdapter.setNewData(genreList);
    }

    private void bindData(MovieVO movieVO) {

        movieTagline = movieVO.getTagline();
        homepage = movieVO.getHomepage();
        imdbId = movieVO.getImdbId();

        getSupportLoaderManager().initLoader(MOVIE_GENRES_LOADER_ID, null, this);
        tvTitleMovieName.setText(movieVO.getTitle());
        tvMovieName.setText(movieVO.getOriginalTitle());
        tvReleasedDate.setText(movieVO.getReleasedDate());
        tvRate.setText(String.format("%.1f", movieVO.getVoteAverage()) + "/10");
        tvOverview.setText(movieVO.getOverview());

        Glide.with(getApplicationContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + movieVO.getBackDropPath()).apply(AppConstants.requestOptions).into(ivMovieBack);
        Glide.with(getApplicationContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + movieVO.getPosterPath()).apply(AppConstants.requestOptions).into(ivMovieLogo);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMovieDetailsLoaded(MoviesiEvents.MovieDetailsDataLoadedEvent event) {
        bindData(event.getmMovie());
        loadingView.setVisibility(View.GONE);
        if (event.getmMovie().getRuntime() != null) {
            llTime.setVisibility(View.VISIBLE);
            int hour = event.getmMovie().getRuntime() / 60;
            int min = event.getmMovie().getRevenue() % 60;
            tvTime.setText(hour + " hr " + min + " mins");
            if (min == 0) {
                tvTime.setText(hour + " hr ");
            }
            if (hour == 0) {
                tvTime.setText(min + " mins");
            }
            if (hour == 0 && min == 0) {
                llTime.setVisibility(View.GONE);
            }
        } else {
            llTime.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onMovieTrailersLoaded(MoviesiEvents.MovieTrailersDataLoadedEvent event) {
        loadingView.setVisibility(View.GONE);
        tvTrailers.setVisibility(View.VISIBLE);
        mTrailersAdapter.setNewData(event.getmTrailers());
    }

    @Subscribe
    public void onMovieCastsDataLoaded(MoviesiEvents.MovieCreditsDataLoadedEvent event) {
        loadingView.setVisibility(View.GONE);
        tvCasts.setVisibility(View.VISIBLE);
        mCastAdapter.setNewData(event.getMovieCasts());
    }

    @Subscribe
    public void onSimilarMoviesDataLoaded(MoviesiEvents.MovieSimilarDataLoadedEvent event) {
        loadingView.setVisibility(View.GONE);
        tvSimilarMovies.setVisibility(View.VISIBLE);
        mSimilarMoviesAdapter.setNewData(event.getMovies());
    }

    @Subscribe
    public void onMovieReviewsDataLoaded(MoviesiEvents.MovieReviewsDataLoadedEvent event) {
        hideSnackBar();
        loadingView.setVisibility(View.GONE);
        if (!event.getReviews().isEmpty()) {
            tvReviews.setVisibility(View.VISIBLE);
            LinearLayout linearlayout = new LinearLayout(this);
            linearlayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParamsParent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams layoutParamsViews = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsParent.setMargins(0, 38, 0, 0);
            linearlayout.setLayoutParams(layoutParamsParent);

            TextView tvContent = new TextView(this);
            TextView tvAuthor = new TextView(this);
            tvContent.setTextSize(14);
            tvAuthor.setTextSize(14);
            tvAuthor.setTypeface(null,Typeface.ITALIC);

            tvContent.setLineSpacing(1.3f,1.3f);
            tvContent.setTextColor(getResources().getColor(R.color.icons));
            tvAuthor.setTextColor(getResources().getColor(R.color.light_brown));

            tvContent.setText(event.getReviews().get(0).getContent());
            tvAuthor.setText("Written by " + event.getReviews().get(0).getAuthor());
            tvAuthor.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            tvContent.setLayoutParams(layoutParamsViews);
            tvAuthor.setLayoutParams(layoutParamsViews);
            linearlayout.addView(tvContent);
            linearlayout.addView(tvAuthor);
            llReviews.addView(linearlayout);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClickTriler(String trailerKey) {
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_WATCH_BASE_URL + trailerKey));
        startActivity(youtubeIntent);
    }

    @Override
    public void onClickCast(Integer castId) {
        startActivity(PersonDetailsActivity.newIntent(getApplicationContext(), castId));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiError(MovieDetailsEvent.ErrorInvokingAPIEvent event) {
        mSnackbar = Snackbar.make(rvMovieCasts, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
            }
        });
        mSnackbar.show();
    }

    public void showSnackBar(String message) {
        mSnackbar = Snackbar.make(rvMovieCasts, message, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
                loadingView.setVisibility(View.VISIBLE);
                loadDetails();
            }
        });
        mSnackbar.show();
    }

    public void hideSnackBar() {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }
}
