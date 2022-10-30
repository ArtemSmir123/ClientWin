package com.example.airport;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
public class MainApp extends Application {
    static Stage stage;
    static Stage stage1;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
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
//        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public static void menuAdministrator() throws IOException {
        stage.setTitle("Панель управления администратора");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApp.class.getResource("4.fxml"));

        BorderPane root = new BorderPane();
        root.setTop(fxmlLoader.load());
        root.setCenter(fxmlLoader1.load());
        Scene scene = new Scene(root);
        stage.setScene(scene);
 //       stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}