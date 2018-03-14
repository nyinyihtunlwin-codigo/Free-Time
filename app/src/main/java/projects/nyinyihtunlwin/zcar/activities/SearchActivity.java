package projects.nyinyihtunlwin.zcar.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.adapters.SearchResultAdapter;
import projects.nyinyihtunlwin.zcar.components.SmartRecyclerView;
import projects.nyinyihtunlwin.zcar.components.SmartScrollListener;
import projects.nyinyihtunlwin.zcar.data.vo.SearchResultVO;
import projects.nyinyihtunlwin.zcar.events.SearchEvents;
import projects.nyinyihtunlwin.zcar.mvp.presenters.SearchPresenter;
import projects.nyinyihtunlwin.zcar.mvp.views.SearchView;
import projects.nyinyihtunlwin.zcar.network.MovieDataAgentImpl;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

/**
 * Created by Dell on 3/11/2018.
 */

public class SearchActivity extends BaseActivity implements SearchView {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.tv_message)
    TextView tvMessage;

    @BindView(R.id.rv_result)
    SmartRecyclerView rvResult;

    private SearchResultAdapter mAdapter;
    private SearchPresenter mPresenter;

    private SmartScrollListener mSmartScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this, this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPresenter = new SearchPresenter(getApplicationContext());
        mPresenter.onCreate(this);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                mPresenter.onResultListEndReached();
            }
        });

        mAdapter = new SearchResultAdapter(getApplicationContext());
        rvResult.setAdapter(mAdapter);
        rvResult.setHasFixedSize(true);
        rvResult.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvResult.addOnScrollListener(mSmartScrollListener);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int keyCode, KeyEvent keyEvent) {
                if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                    if (!etSearch.getText().toString().equals("")) {
                        String query = etSearch.getText().toString();
                        mPresenter.onTapSearch(query);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void displaySearchResults(List<SearchResultVO> resultList) {
        mAdapter.appendNewData(resultList);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoding() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToDetails(String movieId) {

    }
}
