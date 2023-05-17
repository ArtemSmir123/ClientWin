module com.example.airport {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;
    requires jdk.httpserver;
    requires json.simple;
    requires java.net.http;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.httpcomponents.httpcore;
//    requires com.sqareup.okhttp;


    opens com.example.airport to javafx.fxml;
    exports com.example.airport.objects;
    opens com.example.airport.objects to javafx.fxml;
    exports com.example.airport.MenuAdministrator;
    opens com.example.airport.MenuAdministrator to javafx.fxml;
    exports com.example.airport.MenuModerator;
    opens com.example.airport.MenuModerator to javafx.fxml;
    exports com.example.airport;
}