package projects.nyinyihtunlwin.zcar;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import projects.nyinyihtunlwin.zcar.data.models.MovieModel;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;
import projects.nyinyihtunlwin.zcar.utils.AppUtils;
import projects.nyinyihtunlwin.zcar.utils.ConfigUtils;

/**
 * Created by Nyi Nyi Htun Lwin on 12/9/2017.
 */

public class ZCarApp extends Application {
    public static final String LOG_TAG = "Z-CAR";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        ConfigUtils.initConfigUtils(getApplicationContext());
        AppUtils.initAppUtils(getApplicationContext());
        MovieModel.getInstance().startLoadingMovieGenres(getApplicationContext());
    }
}
