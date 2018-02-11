package projects.nyinyihtunlwin.zcar.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.DrawerMenuItemVO;
import projects.nyinyihtunlwin.zcar.delegates.DrawerMenuItemDelegate;
import projects.nyinyihtunlwin.zcar.events.TapDrawerMenuItemEvent;
import projects.nyinyihtunlwin.zcar.fragments.MoviesFragment;
import projects.nyinyihtunlwin.zcar.services.CacheManager;

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

    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvAppTitle.setTypeface(Typeface.createFromAsset(getAssets(), "code_heavy.ttf"));

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
        
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MoviesFragment()).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onTapDrawerMenuItemEvent(TapDrawerMenuItemEvent event) {
        Toast.makeText(getApplicationContext(), event.getDrawerMenuItemVO().getName(), Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(Gravity.START);
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
            super.onBackPressed();
        }
    }

    @Override
    public void clickDrawerMenuItem(DrawerMenuItemVO drawerMenuItemVO) {
        Log.e("H", "YEs");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_movies:
                break;
            case R.id.btn_tv_shows:
                break;
        }
        drawer.closeDrawer(Gravity.START);
    }
}
