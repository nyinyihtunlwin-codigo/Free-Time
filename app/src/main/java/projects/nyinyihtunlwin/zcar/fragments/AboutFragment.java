package projects.nyinyihtunlwin.zcar.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;

public class AboutFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.iv_facebook)
    ImageView btnFacebook;

    @BindView(R.id.iv_linkedin)
    ImageView btnLinkedIn;

    @BindView(R.id.iv_github)
    ImageView btnGitHub;

    @BindView(R.id.tv_app_name)
    TextView tvAppName;

    public AboutFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);

   /*     if (getActivity() != null) {
            tvAppName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "roboto_bold.ttf"));
        }*/

        btnFacebook.setOnClickListener(this);
        btnLinkedIn.setOnClickListener(this);
        btnGitHub.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_facebook:
                open("fb://profile/100002954864451");
                break;
            case R.id.iv_linkedin:
                open("https://www.linkedin.com/in/nyinyi-htunlwin-1b2999112/");
                break;
            case R.id.iv_github:
                open("https://github.com/nyinyihtunlwin");
                break;
        }
    }

    public void open(String url) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(viewIntent);
    }
}
