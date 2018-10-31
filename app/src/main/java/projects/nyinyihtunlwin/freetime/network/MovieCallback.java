package projects.nyinyihtunlwin.freetime.network;

import org.greenrobot.eventbus.EventBus;

import projects.nyinyihtunlwin.freetime.events.MoviesiEvents;
import projects.nyinyihtunlwin.freetime.network.responses.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nyi Nyi Htun Lwin on 12/9/2017.
 */

public abstract class MovieCallback<T extends BaseResponse> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response == null) {
            MoviesiEvents.ErrorInvokingAPIEvent errorEvent
                    = new MoviesiEvents.ErrorInvokingAPIEvent("No data could be load for now. Please try again later.");
            EventBus.getDefault().post(errorEvent);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        MoviesiEvents.ErrorInvokingAPIEvent errorEvent
                = new MoviesiEvents.ErrorInvokingAPIEvent("Can't load data. Please try again.");
        EventBus.getDefault().post(errorEvent);
    }
}
