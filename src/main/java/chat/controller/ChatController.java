package chat.controller;

import chat.model.Client;
import chat.model.MessageListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

/**
 * Controller responsible for managing the chat interface.
 * Handles sending and receiving messages, as well as initializing the client.
 */
public class ChatController implements MessageListener {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    private Client client;

    /**
     * Initializes the chat client with the specified username.
     * @param username the username for this chat session
     */
    public void initializeClient(String username) {
        this.client = new Client(username);
        this.client.setMessageListener(this);
    }

    /**
     * Sends the message typed by the user when the send button is clicked.
     * @param event the action event triggered by the send button
     */
    @FXML
    private void onSendButtonClick(ActionEvent event) {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            client.sendMessage(message);
            messageField.clear();
        }
    }

    /**
     * Callback method triggered when a message is received from the server.
     * @param message the received message
     */
    @Override
    public void onMessageReceived(String message) {
        Platform.runLater(() -> chatArea.appendText(message + "\n"));
    }

    /**
     * Callback method triggered after a message has been successfully sent.
     * @param message the message that was sent
     */
    @Override
    public void onMessageSent(String message) {
        Platform.runLater(() -> chatArea.appendText(">>" + message + "\n"));
    }
}
