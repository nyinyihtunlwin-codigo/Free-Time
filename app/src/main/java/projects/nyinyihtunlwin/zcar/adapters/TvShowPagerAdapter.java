package projects.nyinyihtunlwin.zcar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import projects.nyinyihtunlwin.zcar.fragments.tvshows.NestedTvShowFragment;

/**
 * Created by Dell on 3/26/2018.
 */

public class TvShowPagerAdapter extends FragmentPagerAdapter {

    public TvShowPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new NestedTvShowFragment().newInstance(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}
