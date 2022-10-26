module com.example.airport {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.airport to javafx.fxml;
    exports com.example.airport;
}