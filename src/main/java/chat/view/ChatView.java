package chat.view;

import chat.controller.ChatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Displays the main chat window after successful login.
 * Loads the FXML layout and initializes the controller.
 */
public class ChatView {

    /**
     * Loads the chat interface and shows it in a new window.
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
