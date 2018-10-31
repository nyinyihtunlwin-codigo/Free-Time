package projects.nyinyihtunlwin.freetime.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.adapters.SimilarMovieAdapter;
import projects.nyinyihtunlwin.freetime.adapters.SimilarTvShowAdapter;
import projects.nyinyihtunlwin.freetime.data.models.PersonModel;
import projects.nyinyihtunlwin.freetime.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.freetime.events.MovieDetailsEvent;
import projects.nyinyihtunlwin.freetime.events.PersonDetailsEvent;
import projects.nyinyihtunlwin.freetime.network.responses.PersonDetailsResponse;
import projects.nyinyihtunlwin.freetime.utils.AppConstants;
import projects.nyinyihtunlwin.freetime.utils.AppUtils;

public class PersonDetailsActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_PERSON_ID = "person_id";

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_share)
    ImageView ivShare;

    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_title_name)
    TextView tvTitleName;

    @BindView(R.id.tv_biography)
    TextView tvBiography;

    @BindView(R.id.tv_age)
    TextView tvAge;

    @BindView(R.id.tv_birthplace)
    TextView tvBirthplace;

    @BindView(R.id.avi)
    AVLoadingIndicatorView loadingView;

    @BindView(R.id.tv_movie_cast)
    TextView tvMovieCast;

    @BindView(R.id.rv_movie_cast)
    RecyclerView rvMovieCast;

    @BindView(R.id.tv_tv_cast)
    TextView tvTvShowCast;

    @BindView(R.id.rv_tv_cast)
    RecyclerView rvTvCast;

    private Integer currentPersonId;

    private Snackbar mSnackbar;
    private SimilarMovieAdapter mSimilarMoviesAdapter;
    private SimilarTvShowAdapter mSimilarTvShowAdapter;

    public static Intent newIntent(Context context, int personId) {
        Intent intent = new Intent(context, PersonDetailsActivity.class);
        intent.putExtra(KEY_PERSON_ID, personId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        ButterKnife.bind(this, this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadingView.setVisibility(View.VISIBLE);

        currentPersonId = getIntent().getIntExtra(KEY_PERSON_ID, -1);
        loadDetails();

        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);

        mSimilarMoviesAdapter = new SimilarMovieAdapter(getApplicationContext(), new MovieItemDelegate() {
            @Override
            public void onClickMovie(String movieId) {
                startActivity(MovieDetailsActivity.newIntent(getApplicationContext(), movieId));
            }
        });
        rvMovieCast.setAdapter(mSimilarMoviesAdapter);
        rvMovieCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvMovieCast.setHasFixedSize(true);

        mSimilarTvShowAdapter = new SimilarTvShowAdapter(getApplicationContext(), new MovieItemDelegate() {
            @Override
            public void onClickMovie(String movieId) {
                startActivity(MovieDetailsActivity.newIntent(getApplicationContext(), movieId));
            }
        });
        rvTvCast.setAdapter(mSimilarTvShowAdapter);
        rvTvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTvCast.setHasFixedSize(true);

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
                    tvTitleName.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    tvTitleName.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });
    }

    private void loadDetails() {
        if (AppUtils.getObjInstance().hasConnection()) {
            PersonModel.getInstance().startLoadingPersonDetails(currentPersonId);
            PersonModel.getInstance().startLoadingMovieCreditsOfPerson(currentPersonId);
            PersonModel.getInstance().startLoadingTVShowCreditsOfPerson(currentPersonId);
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
        }
    }

    private void bindData(PersonDetailsResponse response) {
        tvName.setText(response.getName());
        tvTitleName.setText(response.getName());
        tvBirthplace.setText(response.getPlaceOfBirth());
        tvBiography.setText(response.getBiography());
        setAge(response.getDateOfBirth());

        Glide.with(getApplicationContext()).load(AppConstants.IMAGE_LOADING_BASE_URL + response.getProfilePath()).apply(AppConstants.requestOptions).into(ivProfile);
    }

    private void setAge(String dateOfBirthString) {
        if (dateOfBirthString != null && !dateOfBirthString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(dateOfBirthString);
                tvAge.setText((Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(sdf2.format(releaseDate))) + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMovieDetailsLoaded(PersonDetailsEvent.PersonDetailsDataLoadedEvent event) {
        loadingView.setVisibility(View.GONE);
        bindData(event.getPersonDetails());
    }

    @Subscribe
    public void onMovieCreditsOfPersonLoaded(PersonDetailsEvent.PersonMovieCreditDataLoadedEvent event) {
        if (event.getMovieList() != null && event.getMovieList().size() > 0) {
            loadingView.setVisibility(View.GONE);
            tvMovieCast.setVisibility(View.VISIBLE);
            rvMovieCast.setVisibility(View.VISIBLE);
            mSimilarMoviesAdapter.setNewData(event.getMovieList());
        }
    }

    @Subscribe
    public void onTvShowCreditsOfPersonLoaded(PersonDetailsEvent.PersonTVShowCreditDataLoadedEvent event) {
        if (event.getTvShowList() != null && event.getTvShowList().size() > 0) {
            loadingView.setVisibility(View.GONE);
            tvTvShowCast.setVisibility(View.VISIBLE);
            rvTvCast.setVisibility(View.VISIBLE);
            mSimilarTvShowAdapter.setNewData(event.getTvShowList());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiError(MovieDetailsEvent.ErrorInvokingAPIEvent event) {
        mSnackbar = Snackbar.make(rvMovieCast, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
            }
        });
        mSnackbar.show();
    }

    public void showSnackBar(String message) {
        mSnackbar = Snackbar.make(rvTvCast, message, Snackbar.LENGTH_INDEFINITE);
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
