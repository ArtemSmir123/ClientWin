package com.example.airport;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.stage.Modality.WINDOW_MODAL;

public class HelloApplication extends Application {
    static Stage stage;
    static Stage stage1;
    static BorderPane k;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("1.fxml"));
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
        stage1.setTitle("Регистрация на рейс");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("3.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("2.fxml"));
        BorderPane root = new BorderPane();
        root.setTop(fxmlLoader.load());
        root.setCenter(fxmlLoader1.load());
        Scene scene = new Scene(root);
        stage1.setScene(scene);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }
    public static void menuAdministrator() throws IOException {
        stage1.setTitle("Панель управления администратора");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("3.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("4.fxml"));

        BorderPane root = new BorderPane();
        root.setTop(fxmlLoader.load());
        root.setCenter(fxmlLoader1.load());
        Scene scene = new Scene(root);
        stage1.setScene(scene);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

    public static void main(String[] args) {
        launch();
    }
}