package projects.nyinyihtunlwin.zcar.events;

public class ConnectionEvent {
    private String message;
    private int type;

    public ConnectionEvent(String message, int type) {
        this.message = message;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
