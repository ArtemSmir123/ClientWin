package com.example.airport;
import com.example.airport.objects.Admin;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import static com.example.airport.MainApp.stage;
import static com.example.airport.MainApp.stage1;
import static com.example.airport.sqlcode.*;
public class MainController {
    @FXML
    private TextField loginField; // поле логина
    @FXML
    private PasswordField passwordField; // поле пароля
    @FXML
    private Label exeptionLabel;
    private static Users user;
    public MainController() {
    }
    @FXML
    protected void passengerButtonClick(ActionEvent event) throws IOException {
        MainApp.menuPassenger();
    }
    @FXML
    protected void closeProfile(ActionEvent event) throws IOException {
        stage.close();
        user = null;
        stage = new Stage();
        stage1 = new Stage();
        MainApp k = new MainApp();
        k.start(stage);
    } // кнопка закрыть профиль
    @FXML
    protected void closeProgram(ActionEvent event) throws IOException {
        System.exit(0);
    } // кнопка закрыть программу
    @FXML
    protected void login(ActionEvent event) throws IOException {
        exeptionLabel.setText("");
        user = findUser(loginField.getText(), passwordField.getText());
        loginField.setText("");
        passwordField.setText("");
        if (user instanceof Moder) MainApp.menuModerator((Moder)user);
        if (user instanceof Admin) MainApp.menuAdministrator((Admin)user);
        if (user == null) exeptionLabel.setText("Введены неправильные логин или пароль");
    } // ввод логина пароля



}