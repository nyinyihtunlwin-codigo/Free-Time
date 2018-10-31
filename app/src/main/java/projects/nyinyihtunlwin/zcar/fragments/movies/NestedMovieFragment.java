package projects.nyinyihtunlwin.zcar.fragments.movies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.FreeTimeApp;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.activities.MovieDetailsActivity;
import projects.nyinyihtunlwin.zcar.adapters.MovieAdapter;
import projects.nyinyihtunlwin.zcar.components.EmptyViewPod;
import projects.nyinyihtunlwin.zcar.components.SmartRecyclerView;
import projects.nyinyihtunlwin.zcar.components.SmartScrollListener;
import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.zcar.fragments.BaseFragment;
import projects.nyinyihtunlwin.zcar.mvp.presenters.MoviePresenter;
import projects.nyinyihtunlwin.zcar.mvp.views.MovieView;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;


public class NestedMovieFragment extends BaseFragment implements MovieItemDelegate, MovieView {


    private static final String SCREEN_ID = "SCREEN_ID";

    @BindView(R.id.rv_movie)
    SmartRecyclerView rvMovie;

    private MovieAdapter adapter;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    private SmartScrollListener mSmartScrollListener;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MoviePresenter mPresenter;
    private Snackbar mSnackbar;
    private int mRetryConnectionType;
    private int mScreenId;

    @Inject
    Context mContext;

    private View mView;

    public static NestedMovieFragment newInstance(int screenId) {
        Bundle args = new Bundle();
        args.putInt(SCREEN_ID, screenId);
        NestedMovieFragment fragment = new NestedMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_nested_movie, container, false);
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

        mPresenter = new MoviePresenter(mContext, mScreenId);
        mPresenter.onCreate(this);

        rvMovie.setHasFixedSize(true);


        adapter = new MovieAdapter(mContext, this);

        rvMovie.setEmptyView(vpEmptyMovie);
        rvMovie.setAdapter(adapter);
        rvMovie.setLayoutManager(new GridLayoutManager(mContext, 2));

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                showLoadMore();
                mPresenter.onMovieListEndReached(mContext);
            }
        });

        rvMovie.addOnScrollListener(mSmartScrollListener);


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
                int movieLoaderId = 0;
                switch (mScreenId) {
                    case 0:
                        movieLoaderId = AppConstants.MOVIE_NOW_ON_CINEMA_LOADER_ID;
                        break;
                    case 1:
                        movieLoaderId = AppConstants.MOVIE_UPCOMING_LOADER_ID;
                        break;
                    case 2:
                        movieLoaderId = AppConstants.MOVIE_MOST_POPULAR_LOADER_ID;
                        break;
                    case 3:
                        movieLoaderId = AppConstants.MOVIE_TOP_RATED_LOADER_ID;
                        break;
                }
                if (getActivity() != null) {
                    getActivity().getSupportLoaderManager().initLoader(movieLoaderId, null, NestedMovieFragment.this);
                }
            }
        }, 1000);

        return mView;
    }

    private void showLoadMore() {
        Snackbar snackbar = Snackbar.make(rvMovie, "loading movies...", Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(getResources().getColor(R.color.accent));
        snackbar.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean orientationLand = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? true : false);
        if (orientationLand) {
            rvMovie.setLayoutManager(new GridLayoutManager(mContext, 3));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String currentScreen = null;
        switch (mScreenId) {
            case 0:
                currentScreen = AppConstants.MOVIE_NOW_ON_CINEMA;
                break;
            case 1:
                currentScreen = AppConstants.MOVIE_UPCOMING;
                break;
            case 2:
                currentScreen = AppConstants.MOVIE_MOST_POPULAR;
                break;
            case 3:
                currentScreen = AppConstants.MOVIE_TOP_RATED;
                break;
        }
        return new CursorLoader(mContext,
                MovieContract.MovieInScreenEntry.CONTENT_URI,
                null,
                MovieContract.MovieInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{currentScreen}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mPresenter.onDataLoaded(mContext, data);
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
        mPresenter.onTapMovie(movieId);
    }

    @Override
    public void displayMoviesList(List<MovieVO> moviesList) {
        hideSnackBar();
        adapter.setNewData(moviesList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoding() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void navigateToMovieDetails(String movieId) {
        Intent intent = MovieDetailsActivity.newIntent(mContext, movieId);
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
        mSnackbar = Snackbar.make(rvMovie, message, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
            }
        });
        mSnackbar.show();
    }


    public void showSnackBar(String message) {
        mSnackbar = Snackbar.make(rvMovie, message, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
                switch (mRetryConnectionType) {
                    case AppConstants.TYPE_START_LOADING_DATA:
                        swipeRefreshLayout.setRefreshing(true);
                        mPresenter.onForceRefresh(mContext);
                        break;
                    case AppConstants.TYPE_lOAD_MORE_DATA:
                        mPresenter.onMovieListEndReached(mContext);
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
