package chat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller for the login window.
 * Handles username input and login logic.
 */
public class LoginController {
    @FXML
    private TextField usernameField;

    private Runnable onLoginSuccess;

    /**
     * Sets the action to perform when login is successful.
     *
     * @param onLoginSuccess the callback to execute on success
     */
    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    /**
     * Called when the login button is clicked.
     * Stores the username and transitions to the chat window.
     */
    @FXML
    private void onLoginButtonClick() {
        String username = usernameField.getText().trim();
        if (!username.isEmpty() && onLoginSuccess != null) {
            System.setProperty("chat.username", username);
            onLoginSuccess.run();
        }
    }
}
