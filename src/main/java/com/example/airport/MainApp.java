package com.example.airport;
import com.example.airport.MenuAdministrator.MenuAdministratorController;
import com.example.airport.objects.Admin;
import com.example.airport.objects.Users;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;


public class MainApp extends Application {
    static Stage stage;
    static Stage stage1;
    static Users user;
    @Override
    public void start(Stage stag) throws IOException {
        stage = stag;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Airport");
        stage.setScene(scene);
        stage1 = new Stage();
        stage.setResizable(false);
        stage1.setResizable(false);
        stage1.initOwner(stage);
        stage.show();
    }

    public static void menuPassenger() throws IOException {
        stage.setTitle("Регистрация на рейс");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApp.class.getResource("2.fxml"));
        BorderPane root = new BorderPane();
        root.setTop(fxmlLoader.load());
        root.setCenter(fxmlLoader1.load());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void menuAdministrator(Admin admin) throws IOException {
        stage.setTitle("Панель управления администратора");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApp.class.getResource("MenuAdministrator/Admin.fxml"));
        BorderPane root = new BorderPane();
        root.setTop(fxmlLoader.load());
        root.setCenter(fxmlLoader1.load());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        user = admin;
        translate(user);
        stage.show();
    }
    private static void translate(Users user){
        MenuAdministratorController.setUser(user);
        MainApp.user = null;
    }
    public static void menuModerator() throws IOException {
        stage.setTitle("Панель управления модератора");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApp.class.getResource("MenuModerator/Moder.fxml"));
        BorderPane root = new BorderPane();
        root.setTop(fxmlLoader.load());
        root.setCenter(fxmlLoader1.load());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}