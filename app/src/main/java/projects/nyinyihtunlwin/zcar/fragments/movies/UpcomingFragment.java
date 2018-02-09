package projects.nyinyihtunlwin.zcar.fragments.movies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.activities.MovieDetailsActivity;
import projects.nyinyihtunlwin.zcar.adapters.MovieAdapter;
import projects.nyinyihtunlwin.zcar.components.EmptyViewPod;
import projects.nyinyihtunlwin.zcar.components.SmartRecyclerView;
import projects.nyinyihtunlwin.zcar.components.SmartScrollListener;
import projects.nyinyihtunlwin.zcar.data.models.MovieModel;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.zcar.events.RestApiEvents;
import projects.nyinyihtunlwin.zcar.fragments.BaseFragment;
import projects.nyinyihtunlwin.zcar.mvp.presenters.MovieNowOnCinemaPresenter;
import projects.nyinyihtunlwin.zcar.mvp.presenters.MovieUpcomingPresenter;
import projects.nyinyihtunlwin.zcar.mvp.views.MovieUpcomingView;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;


public class UpcomingFragment extends BaseFragment implements MovieItemDelegate, MovieUpcomingView {


    @BindView(R.id.rv_upcoming)
    SmartRecyclerView rvUpcoming;

    private MovieAdapter adapter;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private SmartScrollListener mSmartScrollListener;

    private MovieUpcomingPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new MovieUpcomingPresenter(getActivity());
        mPresenter.onCreate(this);

        rvUpcoming.setHasFixedSize(true);
        adapter = new MovieAdapter(getContext(), this);
        rvUpcoming.setEmptyView(vpEmptyMovie);
        rvUpcoming.setAdapter(adapter);
        rvUpcoming.setLayoutManager(new GridLayoutManager(container.getContext(), 2));

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                mPresenter.onMovieListEndReached(getActivity().getApplicationContext());
            }
        });

        rvUpcoming.addOnScrollListener(mSmartScrollListener);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getActivity().getApplicationContext());
            }
        });

        getActivity().getSupportLoaderManager().initLoader(AppConstants.MOVIE_UPCOMING_LOADER_ID, null, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity().getApplicationContext(),
                MovieContract.MovieInScreenEntry.CONTENT_URI,
                null,
                MovieContract.MovieInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{AppConstants.MOVIE_UPCOMING},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mPresenter.onDataLoaded(getActivity().getApplicationContext(), data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mPresenter.onStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvUpcoming, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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
        adapter.appendNewData(moviesList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoding() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void navigateToMovieDetails(String movieId) {
        Intent intent = MovieDetailsActivity.newIntent(getActivity().getApplicationContext(), movieId);
        startActivity(intent);
    }
}
