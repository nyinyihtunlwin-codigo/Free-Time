package projects.nyinyihtunlwin.zcar.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_facebook)
    ImageView btnFacebook;

    @BindView(R.id.iv_linkedin)
    ImageView btnLinkedIn;

    @BindView(R.id.iv_github)
    ImageView btnGitHub;

    @BindView(R.id.tv_app_name)
    TextView tvAppName;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this, this);

        tvAppName.setTypeface(Typeface.createFromAsset(getAssets(), "code_heavy.ttf"));

        btnFacebook.setOnClickListener(this);
        btnLinkedIn.setOnClickListener(this);
        btnGitHub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_facebook:
                open("https://www.facebook.com/nyinyi.htunlwin");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
