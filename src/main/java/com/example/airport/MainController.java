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

    private static Users user;

    public MainController() {
    }
    @FXML
    protected void btnClick(ActionEvent event) throws IOException {
        MainApp.menuPassenger();
    }
    @FXML
    protected void closeProgram(ActionEvent event) throws IOException {
        stage.close();
        user = null;
        stage = new Stage();
        MainApp k = new MainApp();

        k.start(stage);
    } // кнопка закрыть
    @FXML
    protected void login(ActionEvent event) throws IOException {
        exep2.setText("");
        user = findUser(ffa.getText(), ffa1.getText());
        ffa1.setText("");
        ffa.setText("");
        if (user instanceof Moder) MainApp.menuModerator();
        if (user instanceof Admin) MainApp.menuAdministrator((Admin)user);
        if (user == null) exep2.setText("Введены неправильные логин или пароль");
    } // ввод логина пароля



}