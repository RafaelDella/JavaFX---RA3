module com.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.biblioteca;
    exports com.biblioteca.view;
    exports com.biblioteca.model;

    opens com.biblioteca to javafx.fxml;
    opens com.biblioteca.view to javafx.fxml;
    opens com.biblioteca.model to javafx.fxml;
    exports com.biblioteca.util;
    opens com.biblioteca.util to javafx.fxml;
}
