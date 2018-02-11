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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
import projects.nyinyihtunlwin.zcar.mvp.views.MovieNowOnCinemaView;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;


public class NowOnCinemaFragment extends BaseFragment implements MovieItemDelegate, MovieNowOnCinemaView {


    @BindView(R.id.rv_now_on_cinema)
    SmartRecyclerView rvNowOnCinema;

    private MovieAdapter adapter;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    private SmartScrollListener mSmartScrollListener;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MovieNowOnCinemaPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_on_cinema, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new MovieNowOnCinemaPresenter(getActivity());
        mPresenter.onCreate(this);

        rvNowOnCinema.setHasFixedSize(true);


        adapter = new MovieAdapter(getContext(), this);

        rvNowOnCinema.setEmptyView(vpEmptyMovie);
        rvNowOnCinema.setAdapter(adapter);
        rvNowOnCinema.setLayoutManager(new GridLayoutManager(container.getContext(), 2));

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                mPresenter.onMovieListEndReached(getActivity().getApplicationContext());
            }
        });

        rvNowOnCinema.addOnScrollListener(mSmartScrollListener);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getActivity().getApplicationContext());
            }
        });

        getActivity().getSupportLoaderManager().initLoader(AppConstants.MOVIE_NOW_ON_CINEMA_LOADER_ID, null, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity().getApplicationContext(),
                MovieContract.MovieInScreenEntry.CONTENT_URI,
                null,
                MovieContract.MovieInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{AppConstants.MOVIE_NOW_ON_CINEMA},
                MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID + " ASC");
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
        Snackbar.make(rvNowOnCinema, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
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
        adapter.setNewData(moviesList);
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
