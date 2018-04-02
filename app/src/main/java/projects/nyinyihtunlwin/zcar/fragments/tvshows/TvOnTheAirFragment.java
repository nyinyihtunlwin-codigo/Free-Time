package projects.nyinyihtunlwin.zcar.fragments.tvshows;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.adapters.TvShowAdapter;
import projects.nyinyihtunlwin.zcar.components.EmptyViewPod;
import projects.nyinyihtunlwin.zcar.components.SmartRecyclerView;
import projects.nyinyihtunlwin.zcar.components.SmartScrollListener;
import projects.nyinyihtunlwin.zcar.data.vo.tvshows.TvShowVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.zcar.fragments.BaseFragment;
import projects.nyinyihtunlwin.zcar.mvp.presenters.TvShowsAiringTodayPresenter;
import projects.nyinyihtunlwin.zcar.mvp.presenters.TvShowsOnTheAirPresenter;
import projects.nyinyihtunlwin.zcar.mvp.views.TvShowsOnTheAirView;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 2/22/2018.
 */

public class TvOnTheAirFragment extends BaseFragment implements MovieItemDelegate,TvShowsOnTheAirView {


    @BindView(R.id.rv_on_the_air_tv)
    SmartRecyclerView rvOnTheAirTv;

    private TvShowAdapter adapter;

    @BindView(R.id.vp_empty_tv_shows)
    EmptyViewPod vpEmptyMovie;

    private SmartScrollListener mSmartScrollListener;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private TvShowsOnTheAirPresenter mPresenter;
    private Snackbar mSnackbar;
    private int mRetryConnectionType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_on_the_air, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new TvShowsOnTheAirPresenter(getActivity());
        mPresenter.onCreate(this);

        rvOnTheAirTv.setHasFixedSize(true);


        adapter = new TvShowAdapter(getContext(), this);

        rvOnTheAirTv.setEmptyView(vpEmptyMovie);
        rvOnTheAirTv.setAdapter(adapter);
        rvOnTheAirTv.setLayoutManager(new GridLayoutManager(container.getContext(), 2));

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                showLoadMore();
                mPresenter.onTvShowListEndReached(getActivity().getApplicationContext());
            }
        });

        rvOnTheAirTv.addOnScrollListener(mSmartScrollListener);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getActivity().getApplicationContext());
            }
        });

        showLoding();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportLoaderManager().initLoader(AppConstants.TV_SHOWS_ON_THE_AIR_LOADER_ID, null, TvOnTheAirFragment.this);
            }
        }, 1000);

        return view;
    }

    private void showLoadMore() {
        Snackbar snackbar = Snackbar.make(rvOnTheAirTv, "loading tv shows...", Snackbar.LENGTH_LONG);
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
            rvOnTheAirTv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity().getApplicationContext(),
                MovieContract.TvShowInScreenEntry.CONTENT_URI,
                null,
                MovieContract.TvShowInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{AppConstants.TV_SHOWS_ON_THE_AIR}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mPresenter.onDataLoaded(getActivity().getApplicationContext(), data);
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

    }


    @Override
    public void onConnectionError(String message, int retryConnectionType) {
        showSnackBar(message);
        mRetryConnectionType = retryConnectionType;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onApiError(String message) {
        mSnackbar = Snackbar.make(rvOnTheAirTv, message, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
            }
        });
        mSnackbar.show();
    }


    public void showSnackBar(String message) {
        mSnackbar = Snackbar.make(rvOnTheAirTv, message, Snackbar.LENGTH_INDEFINITE);
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
