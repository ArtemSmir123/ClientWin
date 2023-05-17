package com.example.airport;
import com.example.airport.MenuAdministrator.MenuAdministratorController;
import com.example.airport.MenuModerator.MenuModeratorController;
import com.example.airport.objects.Admin;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Users;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static com.example.airport.sqlcode.*;
import static javafx.application.Application.launch;


public class MainApp extends Application {
    static Stage stage;
    static Stage stage1;
    public static Users user;
    public static UUID uuid;
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
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        }); // при закрытии окна (главного)

    } // загрузка главного меню
    private static void translate(Object user){
        MenuAdministratorController m = new MenuAdministratorController();
        m.setUser((Users) user);
        //m.nor();
        MainApp.user = null;
    } // трансляция пользователя в подсистему
    private static void translate1(Object user){
        MenuModeratorController m = new MenuModeratorController();
        m.setUser((Users) user);
        //m.nor();
        MainApp.user = null;
    } // трансляция модератора в подсистему
    public static void menuModerator(Moder moder) throws IOException {
        stage.setTitle("Панель управления модератора");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApp.class.getResource("MenuModerator/Moder.fxml"));
        BorderPane root = new BorderPane();
        root.setTop(fxmlLoader.load());
        root.setCenter(fxmlLoader1.load());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        translate1(moder);
        stage.show();
        StageCont cont = new StageCont();
    } // загрузка панели модератора
    public static void menuAdministrator(Admin admin) throws IOException {
        stage.setTitle("Панель управления администратора");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApp.class.getResource("MenuAdministrator/Admin.fxml"));
        BorderPane root = new BorderPane();
        root.setTop(fxmlLoader.load());
        root.setCenter(fxmlLoader1.load());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // user = admin;
        translate(admin);
        stage.show();
    } // загрузка панели администратора
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
    } // загрузка меню пассажира

    public static void main(String[] args) {
        launch();
    }
}
class StageCont extends Thread{
    public void run() {
        while (MainApp.stage.isShowing() || MainApp.stage1.isShowing()){
            continue;
        }
        System.exit(0);
    }
}