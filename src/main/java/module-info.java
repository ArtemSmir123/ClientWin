module com.example.airport {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;
    requires jdk.httpserver;
    requires java.net.http;
    requires json.simple;


    opens com.example.airport to javafx.fxml;
    exports com.example.airport.objects;
    opens com.example.airport.objects to javafx.fxml;
    exports com.example.airport.MenuAdministrator;
    opens com.example.airport.MenuAdministrator to javafx.fxml;
    exports com.example.airport.MenuModerator;
    opens com.example.airport.MenuModerator to javafx.fxml;
    exports com.example.airport;
}