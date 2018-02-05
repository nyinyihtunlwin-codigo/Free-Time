package projects.nyinyihtunlwin.zcar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import projects.nyinyihtunlwin.zcar.fragments.movies.MostPopularFragment;
import projects.nyinyihtunlwin.zcar.fragments.movies.NowOnCinemaFragment;
import projects.nyinyihtunlwin.zcar.fragments.movies.TopRatedFragment;
import projects.nyinyihtunlwin.zcar.fragments.movies.UpcomingFragment;

/**
 * Created by Nyi Nyi Htun Lwin on 11/7/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    Fragment fragment;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = new NowOnCinemaFragment();
                break;
            case 1:
                fragment = new UpcomingFragment();
                break;
            case 2:
                fragment = new MostPopularFragment();
                break;
            case 3:
                fragment = new TopRatedFragment();
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
