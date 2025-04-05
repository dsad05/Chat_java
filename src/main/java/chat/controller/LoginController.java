package chat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    private Runnable onLoginSuccess;

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    @FXML
    private void onLoginButtonClick() {
        String username = usernameField.getText().trim();
        if (!username.isEmpty() && onLoginSuccess != null) {
            System.setProperty("chat.username", username);
            onLoginSuccess.run();
        }
    }
}
