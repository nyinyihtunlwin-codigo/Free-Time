package projects.nyinyihtunlwin.zcar.data.models;

/**
 * Created by Dell on 2/5/2018.
 */

public class TvShowModel {

    private static TvShowModel objectInstance;

    private TvShowModel() {
    }

    public static TvShowModel getInstance() {
        if (objectInstance == null) {
            objectInstance = new TvShowModel();
        }
        return objectInstance;
    }
}
