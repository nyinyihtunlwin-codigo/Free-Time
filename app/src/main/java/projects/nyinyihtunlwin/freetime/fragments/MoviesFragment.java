package projects.nyinyihtunlwin.freetime.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import projects.nyinyihtunlwin.freetime.R;
import projects.nyinyihtunlwin.freetime.adapters.MoviePagerAdapter;


public class MoviesFragment extends Fragment {

    private MoviePagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public MoviesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);

        mSectionsPagerAdapter = new MoviePagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.vp_movies);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        return view;
    }

}
