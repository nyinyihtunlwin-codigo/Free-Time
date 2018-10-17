package projects.nyinyihtunlwin.zcar.events;

import projects.nyinyihtunlwin.zcar.network.responses.PersonDetailsResponse;

public class PersonDetailsEvent {
    public static class ErrorInvokingAPIEvent {
        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class ConnectionEvent {
        private String message;

        public ConnectionEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class PersonDetailsDataLoadedEvent {
        private PersonDetailsResponse mPersonDetailsResponse;

        public PersonDetailsDataLoadedEvent(PersonDetailsResponse personDetailsResponse) {
            this.mPersonDetailsResponse = personDetailsResponse;
        }

        public PersonDetailsResponse getPersonDetails() {
            return mPersonDetailsResponse;
        }
    }
}
