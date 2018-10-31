package projects.nyinyihtunlwin.freetime.data.models;

import projects.nyinyihtunlwin.freetime.network.PersonDataAgentImpl;
import projects.nyinyihtunlwin.freetime.utils.AppConstants;

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
