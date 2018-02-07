package projects.nyinyihtunlwin.zcar.fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;

/**
 * Created by Dell on 2/7/2018.
 */

public class DrawerFragment extends Fragment {

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle toggle;
    private RecyclerView recyclerView;


    public DrawerFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.myRecycler);
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
}
