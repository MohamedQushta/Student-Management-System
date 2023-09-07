import java.io.Serializable;

public class Request implements Serializable {
    public RequestType type;
    public boolean isProcessed;
    public User requester;
    Request()
    {
        isProcessed = false;
    }
    Request(RequestType type, User requester)
    {
        this.type = type;
        isProcessed = false;
        this.requester = requester;
    }



    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", isProcessed=" + isProcessed +
                ", requester=" + requester.getEmail() +
                '}';
    }
}
