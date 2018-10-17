package projects.nyinyihtunlwin.zcar.data.models;

import projects.nyinyihtunlwin.zcar.network.PersonDataAgentImpl;
import projects.nyinyihtunlwin.zcar.utils.AppConstants;

public class PersonModel {
    private static PersonModel objectInstance;

    private PersonModel() {
    }

    public static PersonModel getInstance() {
        if (objectInstance == null) {
            objectInstance = new PersonModel();
        }
        return objectInstance;
    }

    public void startLoadingPersonDetails(int personId) {
        PersonDataAgentImpl.getObjectInstance().loadPersonDetails(personId, AppConstants.API_KEY);
    }

    public void startLoadingMovieCreditsOfPerson(Integer currentPersonId) {
        PersonDataAgentImpl.getObjectInstance().loadMovieCredits(currentPersonId, AppConstants.API_KEY);
    }

    public void startLoadingTVShowCreditsOfPerson(Integer currentPersonId) {
        PersonDataAgentImpl.getObjectInstance().loadTVShowCredits(currentPersonId, AppConstants.API_KEY);
    }
}
