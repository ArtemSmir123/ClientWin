package com.example.airport;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

import static com.example.airport.HelloApplication.stage1;
import static com.example.airport.sqlcode.*;

public class HelloController {
    @FXML
    private Button btn;
    @FXML
    private Button b1;
    @FXML
    private TextField ffa;
    @FXML
    private PasswordField ffa1;
    @FXML
    private Label exep1;
    @FXML
    private Label exep2;
    @FXML
    private TextField plane1;
    @FXML
    private TextField plane2;
    @FXML
    private TextField plane3;
    @FXML
    private TableView<Plane> table1;
    @FXML
    private TableColumn<Plane, String> k1;
    @FXML
    private TableColumn<Plane, String> k2;
    @FXML
    private TableColumn<Plane, Integer> k3;

    public HelloController() {
    }

    @FXML
    protected void btnClick(ActionEvent event) throws IOException {
        HelloApplication.menuPassenger();
    }
    @FXML
    protected void closeProgram(ActionEvent event) {
        stage1.close();
        stage1 = new Stage();
    } // кнопка закрыть
    @FXML
    protected void login(ActionEvent event) throws IOException {
        int k = -1;
        exep2.setText("");
        k = findUser(ffa.getText(), ffa1.getText());
        ffa1.setText("");
        ffa.setText("");
     //   if (k == 1) HelloApplication.menuModerator();
        if (k == 0) HelloApplication.menuAdministrator();
        if (k == -1) exep2.setText("Введены неправильные логин или пароль");
    } // ввод логина пароля
    @FXML
    protected void saveBtn1(ActionEvent event){
        boolean saveStatus = false;
        boolean findStatus = findPlane(plane1.getText());
        if (findStatus) {
            try {
                saveStatus = sqlcode.savePlane(plane1.getText(), plane2.getText(), Integer.parseInt(plane3.getText()));
                exep1.setText("сохранено");
            } catch (NumberFormatException exception) {
                exep1.setText("пробуем еще");
                System.out.println("пробуем еще");
            }
            if (saveStatus) exep1.setText("Данные сохранены");
            if (!saveStatus) exep1.setText("Данные не сохранены");
        } else {
            exep1.setText("Такой объект уже есть");
        }
    } // сохранить самолет
    @FXML
    protected void tableView(){
        ObservableList<Plane> forTable = findPlanes();
        table1.setItems(forTable);
        k1.setCellValueFactory(new PropertyValueFactory<Plane, String>("model"));
        k2.setCellValueFactory(new PropertyValueFactory<Plane, String>("fullTitle"));
        k3.setCellValueFactory(new PropertyValueFactory<Plane, Integer>("numberOfSeats"));

    } //табличка с самолетами
}