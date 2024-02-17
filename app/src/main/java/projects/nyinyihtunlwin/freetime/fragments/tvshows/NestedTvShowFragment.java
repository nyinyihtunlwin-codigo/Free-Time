package projects.nyinyihtunlwin.freetime.fragments.tvshows;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.freetime.FreeTimeApp;
import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.activities.TvShowDetailsActivity;
import projects.nyinyihtunlwin.freetime.adapters.TvShowAdapter;
import projects.nyinyihtunlwin.freetime.components.EmptyViewPod;
import projects.nyinyihtunlwin.freetime.components.SmartRecyclerView;
import projects.nyinyihtunlwin.freetime.components.SmartScrollListener;
import projects.nyinyihtunlwin.freetime.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.freetime.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.freetime.fragments.BaseFragment;
import projects.nyinyihtunlwin.freetime.mvp.presenters.TvShowPresenter;
import projects.nyinyihtunlwin.freetime.mvp.views.TvShowView;
import projects.nyinyihtunlwin.freetime.persistence.MovieContract;
import projects.nyinyihtunlwin.freetime.utils.AppConstants;

/**
 * Created by Dell on 2/22/2018.
 */

public class NestedTvShowFragment extends BaseFragment implements MovieItemDelegate, TvShowView {

    private static final String SCREEN_ID = "SCREEN_ID";

    @BindView(R.id.rv_tv_show)
    SmartRecyclerView rvTvShow;

    private TvShowAdapter adapter;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    private SmartScrollListener mSmartScrollListener;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private TvShowPresenter mPresenter;
    private Snackbar mSnackbar;
    private int mRetryConnectionType;
    private int mScreenId;

    @Inject
    Context mContext;

    private View mView;

    public static NestedTvShowFragment newInstance(int screenId) {

        Bundle args = new Bundle();
        args.putInt(SCREEN_ID, screenId);
        NestedTvShowFragment fragment = new NestedTvShowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_nested_tv_show, container, false);
        }
        ButterKnife.bind(this, mView);

        if (getArguments() != null) {
            mScreenId = getArguments().getInt(SCREEN_ID, -1);
            Log.e("Screen ID", mScreenId + "");
        }
        if (getActivity() != null) {
            FreeTimeApp freeTimeApp = (FreeTimeApp) getActivity().getApplicationContext();
            freeTimeApp.getAppComponent().inject(this);
        }

        mPresenter = new TvShowPresenter(mContext, mScreenId);
        mPresenter.onCreate(this);

        rvTvShow.setHasFixedSize(true);


        adapter = new TvShowAdapter(mContext, this);

        rvTvShow.setEmptyView(vpEmptyMovie);
        rvTvShow.setAdapter(adapter);
        rvTvShow.setLayoutManager(new GridLayoutManager(mContext, 2));

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                showLoadMore();
                mPresenter.onTvShowListEndReached(mContext);
            }
        });

        rvTvShow.addOnScrollListener(mSmartScrollListener);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(mContext);
            }
        });

        showLoding();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int tvShowLoaderId = 0;
                switch (mScreenId) {
                    case 0:
                        tvShowLoaderId = AppConstants.TV_SHOWS_AIRING_TODAY_LOADER_ID;
                        break;
                    case 1:
                        tvShowLoaderId = AppConstants.TV_SHOWS_ON_THE_AIR_LOADER_ID;
                        break;
                    case 2:
                        tvShowLoaderId = AppConstants.TV_SHOWS_MOST_POPULAR_LOADER_ID;
                        break;
                    case 3:
                        tvShowLoaderId = AppConstants.TV_SHOWS_TOP_RATED_LOADER_ID;
                        break;
                }
                if (getActivity() != null) {
                    getActivity().getSupportLoaderManager().initLoader(tvShowLoaderId, null, NestedTvShowFragment.this);
                }
            }
        }, 1000);

        return mView;
    }

    private void showLoadMore() {
        Snackbar snackbar = Snackbar.make(rvTvShow, "loading tv shows...", Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(getResources().getColor(R.color.accent));
        snackbar.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean orientationLand = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? true : false);
        if (orientationLand) {
            rvTvShow.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String currentScreen = null;
        switch (mScreenId) {
            case 0:
                currentScreen = AppConstants.TV_SHOWS_AIRING_TODAY;
                break;
            case 1:
                currentScreen = AppConstants.TV_SHOWS_ON_THE_AIR;
                break;
            case 2:
                currentScreen = AppConstants.TV_SHOWS_MOST_POPULAR;
                break;
            case 3:
                currentScreen = AppConstants.TV_SHOWS_TOP_RATED;
                break;
        }
        return new CursorLoader(getActivity().getApplicationContext(),
                MovieContract.TvShowInScreenEntry.CONTENT_URI,
                null,
                MovieContract.TvShowInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{currentScreen}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (getActivity() != null) {
            mPresenter.onDataLoaded(getActivity().getApplicationContext(), data);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClickMovie(String movieId) {
        mPresenter.onTapTvShow(movieId);
    }

    @Override
    public void displayTvShowList(List<TvShowVO> tvShowList) {
        hideSnackBar();
        adapter.setNewData(tvShowList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoding() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void navigateToTvShowDetails(String tvShowId) {
        Intent intent = TvShowDetailsActivity.newIntent(mContext, tvShowId);
        startActivity(intent);
    }


    @Override
    public void onConnectionError(String message, int retryConnectionType) {
        showSnackBar(message);
        mRetryConnectionType = retryConnectionType;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onApiError(String message) {
        mSnackbar = Snackbar.make(rvTvShow, message, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
            }
        });
        mSnackbar.show();
    }


    public void showSnackBar(String message) {
        mSnackbar = Snackbar.make(rvTvShow, message, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
                switch (mRetryConnectionType) {
                    case AppConstants.TYPE_START_LOADING_DATA:
                        swipeRefreshLayout.setRefreshing(true);
                        mPresenter.onForceRefresh(getActivity().getApplicationContext());
                        break;
                    case AppConstants.TYPE_lOAD_MORE_DATA:
                        mPresenter.onTvShowListEndReached(getActivity().getApplicationContext());
                        break;
                }
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
