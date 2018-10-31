package projects.nyinyihtunlwin.zcar.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import projects.nyinyihtunlwin.zcar.FreeTimeApp;

@Module
public class AppModule {

    private FreeTimeApp mApp;

    public AppModule(FreeTimeApp app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

}
