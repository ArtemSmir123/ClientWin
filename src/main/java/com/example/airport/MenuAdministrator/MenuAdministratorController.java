package com.example.airport.MenuAdministrator;
import com.example.airport.objects.Admin;
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
//    private static int idPP;
  //  private static String idModer = new String("");
    private static Admin us;

    private static Moder user;

    @FXML
    private TextField tx1; // Редактирование самолетов
    @FXML
    private TextField tx2; // Редактирование самолетов
    @FXML
    private TextField tx3; // Редактирование самолетов
    @FXML
    private TableView<Plane> table1; // Редактирование самолетов
    @FXML
    private TableColumn<Plane, String> k1; // Редактирование самолетов
    @FXML
    private TableColumn<Plane, String> k2; // Редактирование самолетов
    @FXML
    private TableColumn<Plane, Integer> k3; // Редактирование самолетов
    @FXML
    private Label lab1; // Редактирование самолетов
    @FXML
    private Label lab2; // Редактирование самолетов
    @FXML
    private Label exep1; // Добавить самолет
    @FXML
    private TextField plane1; // Добавить самолет
    @FXML
    private TextField plane2; // Добавить самолет
    @FXML
    private TextField plane3; // Добавить самолет
    @FXML
    private Button updateBtn;
    @FXML
    private Label exep2; // Добавить модератора
    @FXML
    private TextField mod1; // Добавить модератора
    @FXML
    private TextField mod2; // Добавить модератора
    @FXML
    private Label label1; // Добавление
    @FXML
    private TableView<Moder> table3;
    @FXML
    private TableColumn<Moder, String> k4;
    @FXML
    private TableColumn<Moder, String> k5;
    @FXML
    private TableColumn<Moder, String> k6;
    @FXML
    private TextField tx4;
    @FXML
    private TextField tx5;
    @FXML
    private Label lab3;
    @FXML
    private Label lab4;
    public void setUser(Users us) {
        MenuAdministratorController.us = (Admin) us;
    }
    @FXML
    protected void textClean1(){
        tx1.setText("");
        tx2.setText("");
        tx3.setText("");
        //idPP = 0;
        pl = null;
        tableView();
    } // нажатие кнопки очистить поля

    @FXML
    protected void bb2Click(){
        if (pl == null) {
            lab2.setText("Объект не выбран");
            tableView();
        } else if (tx1.getText().equals("") || tx2.getText().equals("") || tx3.getText().equals("")) {
            lab2.setText("Объект содержит некорректные значения");

        } else if (!isDight(tx3.getText())) {
            lab2.setText("Объект содержит некорректные значения, количество мест в самолете должно быть цифрой");
        }
        else {
            pl = new Plane(pl.getId_plane(), tx1.getText(), tx2.getText(), Integer.parseInt(tx3.getText()));
            boolean result = sqlcodeAdmistrator.updatePlane(pl);

            if (result) lab2.setText("Объект обновлен");
            else lab2.setText("Объект не обновлен");
            textClean1();
        }
    } // нажатие кнопки редактировать самолет
    @FXML
    protected void bb3Click(){
        boolean result = sqlcodeAdmistrator.deletePlane(pl.getId_plane());
        if (result) lab2.setText("Объект удален");
        else lab2.setText("Объект не удален");
        textClean1();
    } // нажатие кнопки удалить самолет
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
                    //    idPP = observableValue.getValue().getId_plane();
                        pl = observableValue.getValue();
                        sel();
                    }
                    catch (NullPointerException ignored){
                    }

                }
        });
        sel();
    } // Табличка самолетов
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
        if(pl == null) lab1.setText("Объект не выбран");
        else lab1.setText("Выбран объект:" + pl.toString());
    } // отражалка выбранного объекта
    @FXML
    public void nor(){
        label1.setText(us.toString());
    } // запись о администрации
    @FXML
    public void tableview2(){
        ObservableList<Moder> forTable = ssa.findModers();
        table3.setItems(forTable);
        k4.setCellValueFactory(new PropertyValueFactory<Moder, String>("login"));
        k5.setCellValueFactory(new PropertyValueFactory<Moder, String>("lastname"));
        k6.setCellValueFactory(new PropertyValueFactory<Moder, String>("name"));
        TableView.TableViewSelectionModel<Moder> selectionModel = table3.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Moder>(){
            @Override
            public void changed (ObservableValue < ? extends Moder > observableValue, Moder moder, Moder t1){
                try {
                    tx4.setText(observableValue.getValue().getName());
                    tx5.setText(observableValue.getValue().getLastname());
                  //  idModer = observableValue.getValue().getLogin();
                    user = observableValue.getValue();
                    sel2();
                }
                catch (NullPointerException ignored){
                }

            }
        });
        sel2();
    } // Таблица модераторов
    private void sel2(){
        if(user == null) lab4.setText("Объект не выбран");
        else lab4.setText("Выбран объект:\n" + user.toString());
    } // Отражалка выбранного объекта
    @FXML
    private void editModer() {
        if (user == null) lab3.setText("Оператор не выбран \nвыберите оператора");
        else if (user.getName().equals(tx4.getText()) && user.getLastname().equals(tx5.getText()))
            lab3.setText("Вы не изменили данные\nоператора");
        else {
            if(sqlcodeAdmistrator.editModerQuery(user, tx4.getText(), tx5.getText())) lab3.setText("Данные изменены");
            else lab3.setText("Данные не изменены");
            textClean2();
        }
    } // Редактировать модератора
    protected void textClean2(){
        tx4.setText("");
        tx5.setText("");
        user = null;
        tableview2();
    } // нажатие кнопки очистить поля
}
