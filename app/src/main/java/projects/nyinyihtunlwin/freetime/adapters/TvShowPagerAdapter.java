package projects.nyinyihtunlwin.freetime.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import projects.nyinyihtunlwin.freetime.fragments.tvshows.NestedTvShowFragment;

/**
 * Created by Dell on 3/26/2018.
 */

public class TvShowPagerAdapter extends FragmentPagerAdapter {

    public TvShowPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NestedTvShowFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}
