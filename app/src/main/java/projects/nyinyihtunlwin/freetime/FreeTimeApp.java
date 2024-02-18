package projects.nyinyihtunlwin.freetime;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import projects.nyinyihtunlwin.freetime.dagger.AppComponent;
import projects.nyinyihtunlwin.freetime.dagger.AppModule;
import projects.nyinyihtunlwin.freetime.dagger.DaggerAppComponent;
import projects.nyinyihtunlwin.freetime.data.models.MovieModel;
import projects.nyinyihtunlwin.freetime.utils.AppUtils;
import projects.nyinyihtunlwin.freetime.utils.ConfigUtils;

/**
 * Created by Nyi Nyi Htun Lwin on 12/9/2017.
 */

public class FreeTimeApp extends Application {
    public static final String LOG_TAG = "FreeTime";

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mAppComponent = initDagger(); //daggar init
        mAppComponent.inject(this);//register consumer

        ConfigUtils.initConfigUtils(getApplicationContext());
        AppUtils.initAppUtils(getApplicationContext());
        MovieModel.getInstance().startLoadingMovieGenres(getApplicationContext());
    }

    private AppComponent initDagger() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
