package projects.nyinyihtunlwin.zcar.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Dell on 12/6/2017.
 */



public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }

        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if(mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
