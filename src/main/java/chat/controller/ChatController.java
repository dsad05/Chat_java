package chat.controller;

import chat.model.Client;
import chat.model.MessageListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

/**
 * Controller for the chat window.
 * Handles sending messages and updating the chat area.
 */
public class ChatController implements MessageListener {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    private Client client;

    /**
     * Initializes the chat client with a given username.
     *
     * @param username the username of the current user
     */
    public void initializeClient(String username) {
        this.client = new Client(username);
        this.client.setMessageListener(this);
    }

    /**
     * Called when the send button is clicked.
     *
     * @param event the action event
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
     * Cleans up resources when the window is closed.
     */
    @FXML
    public void onCloseWindow() {
        client.closeEverything();
    }

    /**
     * Updates the chat area with a new message from the server.
     *
     * @param message the received message
     */
    @Override
    public void onMessageReceived(String message) {
        Platform.runLater(() -> chatArea.appendText(message + "\n"));
    }

    /**
     * Displays the message the user just sent.
     *
     * @param message the sent message
     */
    @Override
    public void onMessageSent(String message) {
        Platform.runLater(() -> chatArea.appendText(">>" + message + "\n"));
    }
}
