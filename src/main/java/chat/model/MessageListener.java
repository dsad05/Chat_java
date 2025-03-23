package chat.model;

public interface MessageListener {
    void onMessageReceived(String message);

    void onMessageSent(String message);
}