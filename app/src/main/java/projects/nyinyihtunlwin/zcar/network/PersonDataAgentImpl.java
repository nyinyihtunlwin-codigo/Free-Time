package projects.nyinyihtunlwin.zcar.network;

import org.greenrobot.eventbus.EventBus;

import projects.nyinyihtunlwin.zcar.events.MovieDetailsEvent;
import projects.nyinyihtunlwin.zcar.events.PersonDetailsEvent;
import projects.nyinyihtunlwin.zcar.network.responses.PersonDetailsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public class PersonDataAgentImpl extends BaseDataAgentImpl implements PersonDataAgent {

    private static PersonDataAgentImpl objectInstance;

    public static PersonDataAgentImpl getObjectInstance() {
        if (objectInstance == null) {
            objectInstance = new PersonDataAgentImpl();
        }
        return objectInstance;
    }

    @Override
    public void loadPersonDetails(int personId, String apiKey) {
        Call<PersonDetailsResponse> personDetailsResponseCall = movieAPI.loadPersonDetails(personId, apiKey);
        personDetailsResponseCall.enqueue(new Callback<PersonDetailsResponse>() {
            @Override
            public void onResponse(Call<PersonDetailsResponse> call, Response<PersonDetailsResponse> response) {
                PersonDetailsResponse personDetailsResponse = response.body();
                EventBus.getDefault().post(new PersonDetailsEvent.PersonDetailsDataLoadedEvent(personDetailsResponse));
            }

            @Override
            public void onFailure(Call<PersonDetailsResponse> call, Throwable t) {
                MovieDetailsEvent.ErrorInvokingAPIEvent errorEvent
                        = new MovieDetailsEvent.ErrorInvokingAPIEvent("Can't load data. Try again.");
                EventBus.getDefault().post(errorEvent);
            }
        });
    }
}
