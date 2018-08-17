package projects.nyinyihtunlwin.zcar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import projects.nyinyihtunlwin.zcar.fragments.movies.NestedMovieFragment;

/**
 * Created by Nyi Nyi Htun Lwin on 11/7/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new NestedMovieFragment().newInstance(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}
