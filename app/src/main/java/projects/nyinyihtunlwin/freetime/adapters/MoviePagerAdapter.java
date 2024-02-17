package projects.nyinyihtunlwin.freetime.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import projects.nyinyihtunlwin.freetime.fragments.movies.NestedMovieFragment;

/**
 * Created by Nyi Nyi Htun Lwin on 11/7/2017.
 */

public class MoviePagerAdapter extends FragmentPagerAdapter {

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NestedMovieFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}
