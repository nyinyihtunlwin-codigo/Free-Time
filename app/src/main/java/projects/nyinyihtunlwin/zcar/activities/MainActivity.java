package projects.nyinyihtunlwin.zcar.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.events.TapDrawerMenuItemEvent;
import projects.nyinyihtunlwin.zcar.fragments.DrawerFragment;
import projects.nyinyihtunlwin.zcar.fragments.MoviesFragment;
import projects.nyinyihtunlwin.zcar.fragments.TVShowsFragment;

/**
 * Created by Nyi Nyi Htun Lwin on 11/7/2017.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer);
        drawerFragment.setUP((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        tvAppTitle.setTypeface(Typeface.createFromAsset(getAssets(), "code_heavy.ttf"));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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

}
