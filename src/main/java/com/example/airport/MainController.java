package com.example.airport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import static com.example.airport.MainApp.stage;
import static com.example.airport.sqlcode.*;
public class MainController {
    @FXML
    private Button btn;
    @FXML
    private TextField ffa;
    @FXML
    private PasswordField ffa1;
    @FXML
    private Label exep2;



    public MainController() {
    }
    @FXML
    protected void btnClick(ActionEvent event) throws IOException {
        MainApp.menuPassenger();
    }
    @FXML
    protected void closeProgram(ActionEvent event) throws IOException {
        stage.close();
        stage = new Stage();
        MainApp k = new MainApp();
        k.start(stage);
    } // кнопка закрыть
    @FXML
    protected void login(ActionEvent event) throws IOException {
        int k = -1;
        exep2.setText("");
        k = findUser(ffa.getText(), ffa1.getText());
        ffa1.setText("");
        ffa.setText("");
     //   if (k == 1) MainApp.menuModerator();
        if (k == 0) {
            MainApp.menuAdministrator();
        }
        if (k == -1) exep2.setText("Введены неправильные логин или пароль");
    } // ввод логина пароля



}