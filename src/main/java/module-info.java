module chat.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    exports chat.controller;
    opens chat.controller to javafx.fxml;
    exports chat.view;
    opens chat.view to javafx.fxml;
}