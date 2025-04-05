package chat.view;

import chat.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point of the application.
 * Displays the login window and launches the chat on success.
 */
public class LoginView extends Application {

    /**
     * Starts the JavaFX application by showing the login window.
     *
     * @param stage the main stage
     * @throws Exception if FXML loading fails
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        LoginController controller = fxmlLoader.getController();
        controller.setOnLoginSuccess(() -> {
            stage.close();
            new ChatView().show();
        });

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
