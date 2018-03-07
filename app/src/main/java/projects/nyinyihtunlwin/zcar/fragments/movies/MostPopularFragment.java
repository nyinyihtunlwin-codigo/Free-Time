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
import android.util.Log;
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
import projects.nyinyihtunlwin.zcar.ZCarApp;
import projects.nyinyihtunlwin.zcar.activities.MovieDetailsActivity;
import projects.nyinyihtunlwin.zcar.adapters.MovieAdapter;
import projects.nyinyihtunlwin.zcar.components.EmptyViewPod;
import projects.nyinyihtunlwin.zcar.components.SmartRecyclerView;
import projects.nyinyihtunlwin.zcar.components.SmartScrollListener;
import projects.nyinyihtunlwin.zcar.data.vo.movies.MovieVO;
import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;
import projects.nyinyihtunlwin.zcar.events.MoviesiEvents;
import projects.nyinyihtunlwin.zcar.fragments.BaseFragment;
import projects.nyinyihtunlwin.zcar.mvp.presenters.MovieMostPopularPresenter;
import projects.nyinyihtunlwin.zcar.mvp.views.MovieMostPopularView;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;
import projects.nyinyihtunlwin.zcar.utils.ConfigUtils;


public class MostPopularFragment extends BaseFragment implements MovieItemDelegate, MovieMostPopularView {


    @BindView(R.id.rv_most_popular)
    SmartRecyclerView rvMostPopular;

    MovieAdapter adapter;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private SmartScrollListener mSmartScrollListener;

    private MovieMostPopularPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new MovieMostPopularPresenter(getActivity());
        mPresenter.onCreate(this);

        rvMostPopular.setHasFixedSize(true);
        adapter = new MovieAdapter(getContext(), this);
        rvMostPopular.setEmptyView(vpEmptyMovie);
        rvMostPopular.setAdapter(adapter);
        rvMostPopular.setLayoutManager(new GridLayoutManager(container.getContext(), 2));

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                mPresenter.onMovieListEndReached(getActivity().getApplicationContext());
            }
        });

        rvMostPopular.addOnScrollListener(mSmartScrollListener);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getActivity().getApplicationContext());
            }
        });

        // leave only 20 movies for offline mode///////////////////////////////////////////////////
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(MovieContract.MovieInScreenEntry.CONTENT_URI,
                null,
                MovieContract.MovieInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{AppConstants.MOVIE_MOST_POPULAR},
                MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID + " ASC");
        if (cursor != null && cursor.getCount() > 20) {
            if (cursor.moveToPosition(20)) {
                String col21 = cursor.getString(cursor.getColumnIndex(MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID));
                Log.e(ZCarApp.LOG_TAG, col21 + " Found");
                int deletedRows = getActivity().getApplicationContext().getContentResolver().delete(MovieContract.MovieInScreenEntry.CONTENT_URI,
                        MovieContract.MovieInScreenEntry.COLUMN_SCREEN + "=? AND " + MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID + ">=?",
                        new String[]{AppConstants.MOVIE_MOST_POPULAR, col21});
                Log.e(ZCarApp.LOG_TAG, "Deleted Extra Movies : " + String.valueOf(deletedRows));
                ConfigUtils.getObjInstance().saveMovieNowOnCinemaPageIndex(1);
            }
        }
        Log.e(ZCarApp.LOG_TAG, String.valueOf(cursor.getCount()) + " count");

        //////////////////////////////////////////////////////////////////////////////////////////


        showLoding();
        getActivity().getSupportLoaderManager().initLoader(AppConstants.MOVIE_MOST_POPULAR_LOADER_ID, null, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity().getApplicationContext(),
                MovieContract.MovieInScreenEntry.CONTENT_URI,
                null,
                MovieContract.MovieInScreenEntry.COLUMN_SCREEN + "=?",
                new String[]{AppConstants.MOVIE_MOST_POPULAR},
                MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID + " ASC");

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
        EventBus.getDefault().register(this);
        mPresenter.onStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(MoviesiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvMostPopular, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
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
