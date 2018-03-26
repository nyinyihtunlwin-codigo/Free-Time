package projects.nyinyihtunlwin.zcar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import projects.nyinyihtunlwin.zcar.fragments.tvshows.TvAiringTodayFragment;
import projects.nyinyihtunlwin.zcar.fragments.tvshows.TvMostPopularFragment;
import projects.nyinyihtunlwin.zcar.fragments.tvshows.TvOnTheAirFragment;
import projects.nyinyihtunlwin.zcar.fragments.tvshows.TvTopRatedFragment;

/**
 * Created by Dell on 3/26/2018.
 */

public class TvShowPagerAdapter extends FragmentPagerAdapter {

    public TvShowPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    Fragment fragment;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = new TvAiringTodayFragment();
                break;
            case 1:
                fragment = new TvOnTheAirFragment();
                break;
            case 2:
                fragment = new TvMostPopularFragment();
                break;
            case 3:
                fragment = new TvTopRatedFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}
