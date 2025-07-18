package chat.view;

import chat.controller.ChatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Represents the chat view window of the application.
 * Loads the chat interface and initializes the ChatController.
 */
public class ChatView {

    /**
     * Displays the chat window and initializes the client connection.
     */
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chat-view.fxml"));
            Scene scene = new Scene(loader.load());

            ChatController controller = loader.getController();
            controller.initializeClient(System.getProperty("chat.username"));

            Stage stage = new Stage();
            stage.setTitle("Chat - " + System.getProperty("chat.username"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
