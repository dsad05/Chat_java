package chat.controller;

import chat.model.Client;
import chat.model.MessageListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class ChatController implements MessageListener {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    private Client client;

    public void initializeClient(String username) {
        this.client = new Client(username);
        this.client.setMessageListener(this);
    }

    @FXML
    private void onSendButtonClick(ActionEvent event) {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            client.sendMessage(message);
            messageField.clear();
        }
    }

    @FXML
    public void onCloseWindow() {
        client.closeEverything();
    }

    @Override
    public void onMessageReceived(String message) {
        Platform.runLater(() -> chatArea.appendText(message + "\n"));
    }

    @Override
    public void onMessageSent(String message) {
        Platform.runLater(() -> chatArea.appendText(">>" + message + "\n"));
    }
}
