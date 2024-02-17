package projects.nyinyihtunlwin.freetime.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.data.vo.DrawerMenuItemVO;
import projects.nyinyihtunlwin.freetime.delegates.DrawerMenuItemDelegate;
import projects.nyinyihtunlwin.freetime.events.TapDrawerMenuItemEvent;
import projects.nyinyihtunlwin.freetime.fragments.AboutFragment;
import projects.nyinyihtunlwin.freetime.fragments.MoviesFragment;
import projects.nyinyihtunlwin.freetime.fragments.TVShowsFragment;
import projects.nyinyihtunlwin.freetime.services.CacheManager;

/**
 * Created by Nyi Nyi Htun Lwin on 11/7/2017.
 */

public class MainActivity extends BaseActivity implements DrawerMenuItemDelegate, View.OnClickListener {

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.main_layout)
    CoordinatorLayout mainView;

    @BindView(R.id.btn_movies)
    LinearLayout btnMovies;

    @BindView(R.id.btn_tv_shows)
    LinearLayout btnTvShows;

    @BindView(R.id.btn_about)
    LinearLayout btnAbout;

    @BindView(R.id.tv_current_section)
    TextView tvCurrentSection;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fabSearch;

    DrawerLayout drawer;

    private int mCurrentSection = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

      /*  tvAppTitle.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_bold.ttf"));
        tvCurrentSection.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_bold.ttf"));*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                mainView.setX(slideOffset * drawerView.getWidth());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        Intent cacheManagerIntent = new Intent(this, CacheManager.class);
        cacheManagerIntent.putExtra("hello", "Hello");
        startService(cacheManagerIntent);

        btnMovies.setOnClickListener(this);
        btnTvShows.setOnClickListener(this);
        fabSearch.setOnClickListener(this);
        btnAbout.setOnClickListener(this);

        setFragment(new MoviesFragment());

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onTapDrawerMenuItemEvent(TapDrawerMenuItemEvent event) {
        Toast.makeText(getApplicationContext(), event.getDrawerMenuItemVO().getName(), Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(mCurrentSection!=0){
                fabSearch.setVisibility(View.VISIBLE);
                tvCurrentSection.setText("Movies");
                if (mCurrentSection != 0) {
                    setFragment(new MoviesFragment());
                    mCurrentSection = 0;
                }
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public void clickDrawerMenuItem(DrawerMenuItemVO drawerMenuItemVO) {
        Log.e("H", "YEs");
    }

    @Override
    public void onClick(final View view) {
        drawer.closeDrawer(GravityCompat.START);
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                switch (view.getId()) {
                    case R.id.btn_movies:
                        fabSearch.setVisibility(View.VISIBLE);
                        tvCurrentSection.setText("Movies");
                        if (mCurrentSection != 0) {
                            setFragment(new MoviesFragment());
                            mCurrentSection = 0;
                        }
                        break;
                    case R.id.btn_tv_shows:
                        fabSearch.setVisibility(View.VISIBLE);
                        tvCurrentSection.setText("TV Shows");
                        if (mCurrentSection != 1) {
                            setFragment(new TVShowsFragment());
                            mCurrentSection = 1;
                        }
                        break;
                    case R.id.btn_about:
                        tvCurrentSection.setText("About");
                        fabSearch.setVisibility(View.GONE);
                        if (mCurrentSection != 2) {
                            setFragment(new AboutFragment());
                            mCurrentSection = 2;
                        }
                        break;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        if (view.getId() == R.id.fab) {
            Intent intent = SearchActivity.newIntent(getApplicationContext());
            startActivity(intent);
        }

    }

    private void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
