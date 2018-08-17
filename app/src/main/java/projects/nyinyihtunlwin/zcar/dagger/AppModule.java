package projects.nyinyihtunlwin.zcar.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import projects.nyinyihtunlwin.zcar.ZCarApp;

@Module
public class AppModule {

    private ZCarApp mApp;

    public AppModule(ZCarApp app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

}
