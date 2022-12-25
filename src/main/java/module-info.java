module com.example.webviewexamples {
    requires transitive javafx.controls;

    opens com.example.webviewexamples to javafx.fxml;
    exports com.example.webviewexamples;
}