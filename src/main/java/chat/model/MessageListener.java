package chat.model;

/**
 * Listener interface for handling incoming and outgoing messages.
 */
public interface MessageListener {

    /**
     * Called when a message is received from the server.
     *
     * @param message the received message
     */
    void onMessageReceived(String message);

    /**
     * Called when a message is successfully sent to the server.
     *
     * @param message the sent message
     */
    void onMessageSent(String message);
}