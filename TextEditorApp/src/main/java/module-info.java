module com.example.texteditorapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.texteditorapp to javafx.fxml;
    exports com.example.texteditorapp;
}