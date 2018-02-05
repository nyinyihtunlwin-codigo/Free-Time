package projects.nyinyihtunlwin.zcar.fragments.movies;

import android.content.Context;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.adapters.MovieAdapter;
import projects.nyinyihtunlwin.zcar.components.EmptyViewPod;
import projects.nyinyihtunlwin.zcar.components.SmartRecyclerView;
import projects.nyinyihtunlwin.zcar.components.SmartScrollListener;
import projects.nyinyihtunlwin.zcar.data.models.MovieModel;
import projects.nyinyihtunlwin.zcar.data.vo.MovieVO;
import projects.nyinyihtunlwin.zcar.events.RestApiEvents;
import projects.nyinyihtunlwin.zcar.fragments.BaseFragment;
import projects.nyinyihtunlwin.zcar.persistence.MovieContract;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;


public class NowOnCinemaFragment extends BaseFragment {

    private static final int MOVIE_NOW_ON_CINEMA_LOADER_ID = 1001;

    @BindView(R.id.rv_now_on_cinema)
    SmartRecyclerView rvNowOnCinema;

    private MovieAdapter adapter;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    private SmartScrollListener mSmartScrollListener;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_on_cinema, container, false);
        ButterKnife.bind(this, view);

        rvNowOnCinema.setHasFixedSize(true);

        adapter = new MovieAdapter(getContext());

        rvNowOnCinema.setEmptyView(vpEmptyMovie);
        rvNowOnCinema.setAdapter(adapter);
        rvNowOnCinema.setLayoutManager(new GridLayoutManager(container.getContext(), 2));

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                MovieModel.getInstance().loadMoreMovies(getActivity().getApplicationContext(), AppConstants.MOVIE_NOW_ON_CINEMA);
            }
        });

        rvNowOnCinema.addOnScrollListener(mSmartScrollListener);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MovieModel.getInstance().forceRefreshMovies(getActivity().getApplicationContext(), AppConstants.MOVIE_NOW_ON_CINEMA);
            }
        });

        getActivity().getSupportLoaderManager().initLoader(MOVIE_NOW_ON_CINEMA_LOADER_ID, null, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity().getApplicationContext(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<MovieVO> movieList = new ArrayList<>();
            do {
                MovieVO movieVO = MovieVO.parseFromCursor(data);
                movieList.add(movieVO);
            } while (data.moveToNext());
        //    adapter.appendNewData(movieList);
         //   swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        List<MovieVO> movieList = MovieModel.getInstance().getNowOnCinemaMovies();
        if (!movieList.isEmpty()) {
            adapter.setNewData(movieList);
        } else {
            MovieModel.getInstance().startLoadingMovies(getActivity().getApplicationContext(), AppConstants.MOVIE_NOW_ON_CINEMA);
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMovieDataLoaded(RestApiEvents.NowOnCinemaMoviesDataLoadedEvent event) {
        adapter.appendNewData(event.getLoadedMovies());
        swipeRefreshLayout.setRefreshing(false);
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
}
