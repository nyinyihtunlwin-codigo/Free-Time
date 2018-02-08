package projects.nyinyihtunlwin.zcar.fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.adapters.DrawerMenuItemAdapter;
import projects.nyinyihtunlwin.zcar.data.vo.DrawerMenuItemVO;
import projects.nyinyihtunlwin.zcar.delegates.DrawerMenuItemDelegate;
import projects.nyinyihtunlwin.zcar.events.TapDrawerMenuItemEvent;

/**
 * Created by Dell on 2/7/2018.
 */

public class DrawerFragment extends Fragment implements DrawerMenuItemDelegate{

    private DrawerLayout dLayout;


    private ActionBarDrawerToggle toggle;

    @BindView(R.id.rv_drawer)
    RecyclerView rvDrawerMenu;

    DrawerMenuItemAdapter mDrawerMenuItemAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, v);

        List<DrawerMenuItemVO> menuItemList = new ArrayList<>();
        menuItemList.add(new DrawerMenuItemVO(R.drawable.ic_movies, "Movies"));
        menuItemList.add(new DrawerMenuItemVO(R.drawable.ic_live_tv_24dp, "TV Series"));

        mDrawerMenuItemAdapter = new DrawerMenuItemAdapter(getContext(),this);
        rvDrawerMenu.setAdapter(mDrawerMenuItemAdapter);
        rvDrawerMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDrawerMenu.setHasFixedSize(true);
        mDrawerMenuItemAdapter.setNewData(menuItemList);

        return v;
    }

    public void setUP(final DrawerLayout dLayout, Toolbar tbar) {
        this.dLayout = dLayout;
        final CoordinatorLayout mainView = (CoordinatorLayout) getActivity().findViewById(R.id.main_layout);
        toggle = new ActionBarDrawerToggle(getActivity(), dLayout, tbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                dLayout.bringChildToFront(drawerView);
                dLayout.requestLayout();
            }
        };
        dLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void clickDrawerMenuItem(DrawerMenuItemVO drawerMenuItemVO) {
        Log.e("clickedddd","Yes");
    }
}
