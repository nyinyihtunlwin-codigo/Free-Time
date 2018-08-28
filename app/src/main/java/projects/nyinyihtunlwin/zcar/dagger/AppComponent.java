package projects.nyinyihtunlwin.zcar.dagger;

import javax.inject.Singleton;

import dagger.Component;
import projects.nyinyihtunlwin.zcar.ZCarApp;
import projects.nyinyihtunlwin.zcar.fragments.movies.NestedMovieFragment;
import projects.nyinyihtunlwin.zcar.fragments.tvshows.NestedTvShowFragment;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(ZCarApp app);

    void inject(NestedMovieFragment nestedMovieFragment);

    void inject(NestedTvShowFragment nestedTvShowFragment);
}
