package projects.nyinyihtunlwin.zcar.fragments.tvshows;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.fragments.BaseFragment;

/**
 * Created by Dell on 2/22/2018.
 */

public class TvOnTheAirFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_on_the_air, container, false);
        return view;
    }
}
