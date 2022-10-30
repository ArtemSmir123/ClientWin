module com.example.airport {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.airport to javafx.fxml;
    exports com.example.airport;
    exports com.example.airport.objects;
    opens com.example.airport.objects to javafx.fxml;
}