package com.example.airport.MenuAdministrator;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Plane;
import com.example.airport.objects.Users;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MenuAdministratorController {
    public MenuAdministratorController(){
    }
    sqlcodeAdmistrator ssa = new sqlcodeAdmistrator();
    private static Plane pl;
    private static int idPP;
    private static Users user;

    public static void setUser(Users user) {
        MenuAdministratorController.user = user;
    }

    @FXML
    private Button bb1;
    @FXML
    private Button bb2;
    @FXML
    private Button bb3;
    @FXML
    private TextField tx1;
    @FXML
    private TextField tx2;
    @FXML
    private TextField tx3;
    @FXML
    private TableView<Plane> table1;
    @FXML
    private TableColumn<Plane, String> k1;
    @FXML
    private TableColumn<Plane, String> k2;
    @FXML
    private TableColumn<Plane, Integer> k3;
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
    private Button updateBtn;
    @FXML
    private Label lab1;
    @FXML
    private Label lab2;
    @FXML
    private TextField mod1;
    @FXML
    private TextField mod2;
    @FXML
    private Label label1;

    @FXML
    protected void textClean1(){
        tx1.setText("");
        tx2.setText("");
        tx3.setText("");
        idPP = 0;
        tableView();
    } // нажатие кнопки очистить поля

    @FXML
    protected void bb2Click(){
        if (idPP == 0) {
            lab2.setText("Объект не выбран");
            tableView();
        } else if (tx1.getText().equals("") || tx2.getText().equals("") || tx3.getText().equals("")) {
            lab2.setText("Объект содержит некорректные значения");

        } else if (!isDight(tx3.getText())) {
            lab2.setText("Объект содержит некорректные значения, количество мест в самолете должно быть цифрой");
        }
        else {
            pl = new Plane(idPP, tx1.getText(), tx2.getText(), Integer.parseInt(tx3.getText()));
            boolean result = sqlcodeAdmistrator.updatePlane(pl);

            if (result) lab2.setText("Объект обновлен");
            else lab2.setText("Объект не обновлен");
            textClean1();
        }
    } // нажатие кнопки редактировать
    @FXML
    protected void bb3Click(){
        boolean result = sqlcodeAdmistrator.deletePlane(idPP);
        if (result) lab2.setText("Объект удален");
        else lab2.setText("Объект не удален");
        textClean1();
    } // нажатие кнопки удалить
    @FXML
    protected void tableView(){
        ObservableList<Plane> forTable = ssa.findPlanes();
        table1.setItems(forTable);
        k1.setCellValueFactory(new PropertyValueFactory<Plane, String>("model"));
        k2.setCellValueFactory(new PropertyValueFactory<Plane, String>("fullTitle"));
        k3.setCellValueFactory(new PropertyValueFactory<Plane, Integer>("numberOfSeats"));

        TableView.TableViewSelectionModel<Plane> selectionModel = table1.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Plane>(){
            @Override
                public void changed (ObservableValue < ? extends Plane > observableValue, Plane plane, Plane t1){
                    try {
                        tx1.setText(observableValue.getValue().getModel());
                        tx2.setText(observableValue.getValue().getFullTitle());
                        tx3.setText(String.valueOf(observableValue.getValue().getNumberOfSeats()));
                        idPP = observableValue.getValue().getId_plane();
                        sel();
                    }
                    catch (NullPointerException ignored){
                    }

                }
        });
        sel();
    } //табличка с самолетами
    @FXML
    protected void saveBtn1(ActionEvent event){
        boolean saveStatus = false;
        boolean findStatus = ssa.findPlane(plane1.getText());
        if (!findStatus) {
            try {
                saveStatus = sqlcodeAdmistrator.savePlane(plane1.getText(), plane2.getText(), Integer.parseInt(plane3.getText()));
                exep1.setText("сохранено");
            } catch (NumberFormatException exception) {
                exep1.setText("В поле количество необходимо \nпоставить цифру");
            }
            if (saveStatus) exep1.setText("Данные сохранены");
        } else {
            exep1.setText("Такой объект уже есть");
        }
    } // сохранить самолет в БД
    @FXML
    protected void saveBtn2(ActionEvent event){
        boolean result = true;
        int login = 0;
        while (result){
            login = 10000000 + (int) (Math.random() * 99999999);
            result = sqlcodeAdmistrator.findLogin(login);
        }
        Moder moder = new Moder(String.valueOf(login), String.valueOf(login), "moder", mod1.getText(), mod2.getText());
        boolean s = ssa.saveModer(moder);
        if (s) exep2.setText("Пользователь создан,\nлогин и пароль: " + login);
        else exep2.setText("Пользователь не создан,\nвозникли некоторые проблемы");
    } // создать модера
    private boolean isDight(String s){
        try {
            Integer.parseInt(tx3.getText());
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    } // проверка на число
    private void sel(){
        if(idPP == 0) lab1.setText("Объект не выбран");
        else lab1.setText("Выбран объект:" + sqlcodeAdmistrator.findPlane(idPP).toString());
    } // отражалка выбранного объекта
    @FXML
    public void nor(){
        label1.setText(user.toString());
    }
}
