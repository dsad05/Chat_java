package chat.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onLoginButtonClick() {
        String username = usernameField.getText().trim();
        if (!username.isEmpty()) {
            openChatWindow(username);
        }
    }

    private void openChatWindow(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chat-view.fxml"));
            Parent root = loader.load();

            ChatController chatController = loader.getController();
            chatController.initializeClient(username);

            Stage chatStage = new Stage();
            chatStage.setTitle("Chat - " + username);
            chatStage.setScene(new Scene(root));
            chatStage.show();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
