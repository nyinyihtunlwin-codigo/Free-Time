package projects.nyinyihtunlwin.zcar.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.adapters.CastAdapter;
import projects.nyinyihtunlwin.zcar.adapters.GenreAdapter;
import projects.nyinyihtunlwin.zcar.adapters.TrailersAdapter;
import projects.nyinyihtunlwin.zcar.data.models.TvShowModel;
import projects.nyinyihtunlwin.zcar.data.vo.GenreVO;
import projects.nyinyihtunlwin.zcar.data.vo.ReviewVO;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieDetailsDelegate;
import projects.nyinyihtunlwin.zcar.events.MovieDetailsEvent;
import projects.nyinyihtunlwin.zcar.events.MoviesiEvents;
import projects.nyinyihtunlwin.zcar.events.TvShowDetailsEvent;
import projects.nyinyihtunlwin.zcar.events.TvShowsEvents;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;
import projects.nyinyihtunlwin.zcar.utils.AppUtils;

public class TvShowDetailsActivity extends BaseActivity implements MovieDetailsDelegate, View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_TV_SHOW_ID = "tv_show_id";

    public static final int TV_SHOW_DETAILS_LOADER_ID = 1200001;
    public static final int TV_SHOW_GENRES_LOADER_ID = 1200002;


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

    @BindView(R.id.ll_season)
    LinearLayout llSeason;

    @BindView(R.id.tv_season)
    TextView tvSeason;

    @BindView(R.id.lb_season)
    TextView lbSeason;

    @BindView(R.id.ll_episode)
    LinearLayout llEpisode;

    @BindView(R.id.lb_episode)
    TextView lbEpisode;

    @BindView(R.id.tv_episode)
    TextView tvEpisode;

    private String currentMovieId;
    private List<Integer> currentGenreIds;


    private GenreAdapter mGenreAdapter;
    private TrailersAdapter mTrailersAdapter;
    private CastAdapter mCastAdapter;

    private Snackbar mSnackbar;

    public static Intent newIntent(Context context, String tvShowId) {
        Intent intent = new Intent(context, TvShowDetailsActivity.class);
        intent.putExtra(KEY_TV_SHOW_ID, tvShowId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);
        ButterKnife.bind(this, this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadingView.setVisibility(View.VISIBLE);

        if (getIntent().getStringExtra(KEY_TV_SHOW_ID) != null) {
            currentMovieId = getIntent().getStringExtra(KEY_TV_SHOW_ID);
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

        mCastAdapter = new CastAdapter(getApplicationContext());
        rvMovieCasts.setAdapter(mCastAdapter);
        rvMovieCasts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvMovieCasts.setHasFixedSize(true);

        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);

        getSupportLoaderManager().initLoader(TV_SHOW_DETAILS_LOADER_ID, null, this);

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
            TvShowModel.getInstance().startLoadingTvShowTrailers(currentMovieId);
            TvShowModel.getInstance().startLoadingTvShowReviews(currentMovieId);
            TvShowModel.getInstance().startLoadingTvShowCredits(currentMovieId);
            TvShowModel.getInstance().startLoadingTvShowDetails(currentMovieId);
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
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        switch (id) {
            case TV_SHOW_DETAILS_LOADER_ID:
                cursorLoader = new CursorLoader(getApplicationContext(),
                        MovieContract.TvShowsEntry.CONTENT_URI,
                        null,
                        MovieContract.TvShowsEntry.COLUMN_TV_SHOWS_ID + "=?",
                        new String[]{currentMovieId},
                        null);
                break;
            case TV_SHOW_GENRES_LOADER_ID:
                cursorLoader = new CursorLoader(getApplicationContext(),
                        MovieContract.TvShowGenreEntry.CONTENT_URI,
                        null,
                        MovieContract.TvShowGenreEntry.COLUMN_TV_SHOW_ID + "=?",
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
                case TV_SHOW_DETAILS_LOADER_ID:
                    TvShowVO movieVO = TvShowVO.parseFromCursor(getApplicationContext(), data);
                    bindData(movieVO);
                    break;
                case TV_SHOW_GENRES_LOADER_ID:
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

    private void bindData(TvShowVO movieVO) {


        getSupportLoaderManager().initLoader(TV_SHOW_GENRES_LOADER_ID, null, this);
        tvTitleMovieName.setText(movieVO.getName());
        tvMovieName.setText(movieVO.getOriginalName());
        tvReleasedDate.setText(movieVO.getFirstAirDate());
        tvRate.setText(movieVO.getVoteAverage() + "/10");
        tvOverview.setText(movieVO.getOverview());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop();
        Glide.with(getApplicationContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + movieVO.getBackdropPath()).apply(requestOptions).into(ivMovieBack);
        Glide.with(getApplicationContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + movieVO.getPosterPath()).apply(requestOptions).into(ivMovieLogo);

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
    public void onMovieDetailsLoaded(TvShowsEvents.TvShowDetailsDataLoadedEvent event) {
        if (event.getTvShow().getEpisodeRunTime() != null) {
            llTime.setVisibility(View.VISIBLE);
            int hour = event.getTvShow().getEpisodeRunTime().get(0) / 60;
            int min = event.getTvShow().getEpisodeRunTime().get(0) % 60;
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
        if (event.getTvShow().getNumberOfSeasons() > 0) {
            llSeason.setVisibility(View.VISIBLE);
            tvSeason.setText(String.valueOf(event.getTvShow().getNumberOfSeasons()));
        } else {
            llSeason.setVisibility(View.GONE);
        }
        if (event.getTvShow().getNumberOfEpisodes() > 0) {
            llEpisode.setVisibility(View.VISIBLE);
            tvEpisode.setText(String.valueOf(event.getTvShow().getNumberOfEpisodes()));
        } else {
            llEpisode.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onMovieTrailersLoaded(MoviesiEvents.MovieTrailersDataLoadedEvent event) {
        tvTrailers.setVisibility(View.VISIBLE);
        mTrailersAdapter.setNewData(event.getmTrailers());
    }

    @Subscribe
    public void onMovieCastsDataLoaded(MoviesiEvents.MovieCreditsDataLoadedEvent event) {
        tvCasts.setVisibility(View.VISIBLE);
        mCastAdapter.setNewData(event.getMovieCasts());
        loadingView.setVisibility(View.GONE);
        hideSnackBar();
    }

    @Subscribe
    public void onMovieReviewsDataLoaded(MoviesiEvents.MovieReviewsDataLoadedEvent event) {
        loadingView.setVisibility(View.GONE);
        tvReviews.setVisibility(View.VISIBLE);
        for (ReviewVO reviewVO : event.getReviews()) {
            LinearLayout linearlayout = new LinearLayout(this);
            linearlayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParamsParent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams layoutParamsViews = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsParent.setMargins(0, 38, 0, 0);
            linearlayout.setLayoutParams(layoutParamsParent);

            TextView tvContent = new TextView(this);
            TextView tvAuthor = new TextView(this);
            tvContent.setTextSize(14);
            tvAuthor.setTextSize(18);
            tvAuthor.setTypeface(Typeface.createFromAsset(getAssets(), "berylium_rg_it.ttf"));
            tvContent.setTextColor(getResources().getColor(R.color.icons));
            tvAuthor.setTextColor(getResources().getColor(R.color.icons));

            tvContent.setText(reviewVO.getContent());
            tvAuthor.setText("Written by " + reviewVO.getAuthor());

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDetailsApiError(TvShowDetailsEvent.ErrorInvokingAPIEvent event) {
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
