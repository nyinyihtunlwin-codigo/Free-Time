package projects.nyinyihtunlwin.freetime.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.adapters.TvShowPagerAdapter;


public class TVShowsFragment extends Fragment {

    private TvShowPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public TVShowsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_shows, container, false);

        mSectionsPagerAdapter = new TvShowPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.vp_tv_show);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        return view;
    }
}
