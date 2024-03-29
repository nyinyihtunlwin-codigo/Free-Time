package projects.nyinyihtunlwin.freetime.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.freetime.R;

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
                if (getActivity() != null) {
                    if (isAppInstalled(getActivity(), "com.facebook.katana")) {
                        open("fb://profile/100002954864451");
                    } else {
                        open("https://facebook.com/nyinyi.htunlwin");
                    }
                }
                break;
            case R.id.iv_linkedin:
                open("https://www.linkedin.com/in/nyinyi-htunlwin-1b2999112/");
                break;
            case R.id.iv_github:
                open("https://github.com/nyinyihtunlwin");
                break;
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void open(String url) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (getActivity() != null) {
            getActivity().startActivity(viewIntent);
        }
    }
}
