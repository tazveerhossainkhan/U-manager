module com.example.umanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.umanager to javafx.fxml;
    exports com.example.umanager;
}