package projects.nyinyihtunlwin.freetime.dagger;

import javax.inject.Singleton;

import dagger.Component;
import projects.nyinyihtunlwin.freetime.FreeTimeApp;
import projects.nyinyihtunlwin.freetime.fragments.movies.NestedMovieFragment;
import projects.nyinyihtunlwin.freetime.fragments.tvshows.NestedTvShowFragment;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(FreeTimeApp app);

    void inject(NestedMovieFragment nestedMovieFragment);

    void inject(NestedTvShowFragment nestedTvShowFragment);
}
