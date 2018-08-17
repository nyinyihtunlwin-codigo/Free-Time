package projects.nyinyihtunlwin.zcar.dagger;

import javax.inject.Singleton;

import dagger.Component;
import projects.nyinyihtunlwin.zcar.ZCarApp;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(ZCarApp app);
}
