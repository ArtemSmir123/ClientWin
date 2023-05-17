package com.example.airport.MenuAdministrator;
import com.example.airport.MainApp;
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
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class MenuAdministratorController {
    public MenuAdministratorController(){
    }
    @FXML
    private void initialize() throws IOException, ParseException, InterruptedException {
        tableView();
        tableview2();
        us = (Admin) MainApp.user;
        //nor();
        MainApp.user = null;
    } // первичная инициализация
    sqlcodeAdmistrator ssa = new sqlcodeAdmistrator(); // инициализция SQL

    private static Admin us; // Владелец сессии
    private static Moder user; // Выбранный модератор для редактирования
    private static Plane pl; // Выбранный самолет для редактирования
    @FXML
    private TextField tx1; // Редактирование самолетов, поле модель
    @FXML
    private TextField tx2; // Редактирование самолетов, поле полное название
    @FXML
    private TextField tx3; // Редактирование самолетов, поле кол-во мест
    @FXML
    private TableView<Plane> table1; // Редактирование самолетов
    @FXML
    private TableColumn<Plane, String> k1; // Редактирование самолетов, столбец модель
    @FXML
    private TableColumn<Plane, String> k2; // Редактирование самолетов, столбец полное название
    @FXML
    private TableColumn<Plane, Integer> k3; // Редактирование самолетов, столбец кол-во мест
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
//    @FXML
//    private Button updateBtn;
    @FXML
    private Label exep2; // Добавить модератора
    @FXML
    private TextField mod1; // Добавить модератора
    @FXML
    private TextField mod2; // Добавить модератора
    @FXML
    private Label label1 = new Label(); // Добавление
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
    @FXML
    private TextField searchField;
    @FXML
    private Label errorLabel;
    
    public void setUser(Users us) {
        MenuAdministratorController.us = (Admin) us;
//        System.out.println(us.toString());
        nor();
    } // установить пользователя, как владельца сессии
    @FXML
    protected void textClean1() throws IOException, ParseException, InterruptedException {
        tx1.setText("");
        tx2.setText("");
        tx3.setText("");
        pl = null;
        tableView();
    } // очищение полей в плашке самолеты
    @FXML
    protected void savePlane() throws IOException, ParseException, InterruptedException {
        if (pl == null) {
            lab2.setText("Объект не выбран");
            //tableView();
        } else if (tx1.getText().equals("") || tx2.getText().equals("") || tx3.getText().equals("")) {
            lab2.setText("Объект содержит некорректные значения");

        } else if (!isDight(tx3.getText())) {
            lab2.setText("Объект содержит некорректные значения, количество мест в самолете должно быть цифрой");
        }
        else
//            if ((pl.getModel() = (tx1.getText())) && (tx2.getText() = (pl.getFullTitle())) && (String.valueOf(tx3.getText()) = (pl.getNumberOfSeats())))
//            lab2.setText("Вы не изменили данные\nоператора");
//        else
        {
            pl = new Plane(pl.getId_plane(), tx1.getText(), tx2.getText(), Integer.parseInt(tx3.getText()));
            boolean result = sqlcodeAdmistrator.updatePlane(pl);

            if (result) lab2.setText("Объект обновлен");
            else lab2.setText("Объект не обновлен");
            textClean1();
        }
    } // нажатие кнопки редактировать самолет (сохранение)
    @FXML
    protected void deletePlane() throws IOException, ParseException, InterruptedException {
        boolean result = sqlcodeAdmistrator.deletePlane(pl.getId_plane());
        if (result) lab2.setText("Объект удален");
        else lab2.setText("Объект не удален");
        textClean1();
    } // нажатие кнопки удалить самолет
    @FXML
    protected void tableView() throws IOException, ParseException, InterruptedException {
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
    protected void saveButtonPlane(ActionEvent event) throws IOException, ParseException, InterruptedException {
        boolean saveStatus = false;
        boolean status = isDight(plane3.getText());
        if (status){
            boolean findStatus = ssa.findPlane(plane1.getText());
            if (!findStatus) {
                exep1.setText("Такой объект уже есть");
            } else {
                saveStatus = sqlcodeAdmistrator.savePlane(plane1.getText(), plane2.getText(), Integer.parseInt(plane3.getText()));
                tableView();
                exep1.setText("Самолет сохранен");
            }
        } else {
            exep1.setText("Количество мест в самолете \nдолжно быть цифрой");
        }
    } // добавить и сохранить самолет в cистему
    @FXML
    protected void saveButtonModer(ActionEvent event) throws IOException, ParseException, InterruptedException {
        if(mod1.getText().equals("") || mod2.getText().equals("")){
            exep2.setText("Поля пустые");
        } else {
            boolean result = true;
            int login = 0;
            while (result) {
                login = 10000000 + (int) (Math.random() * 90000000);
                result = sqlcodeAdmistrator.findLogin(login);
            }
            Moder moder = new Moder(String.valueOf(login), String.valueOf(login), "moder", mod1.getText(), mod2.getText());
            boolean s = ssa.saveModer(moder);
            if (s) exep2.setText("Пользователь создан,\nлогин и пароль: " + login);
            else exep2.setText("Пользователь не создан,\nвозникли некоторые проблемы");
        }
        tableview2();
    } // создать и сохранить модера в систему
    private boolean isDight(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    } // проверка на число
    @FXML
    public void nor(){
            label1.setText(us.toString());
    } // запись о администрации
    @FXML
    public void tableview2() throws ParseException, IOException, InterruptedException {
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
    } // Отражалка выбранного объекта из таблички модераторы
    private void sel(){
        if(pl == null) lab1.setText("Объект не выбран");
        else lab1.setText("Выбран объект:" + pl.toString());
    } // отражалка выбранного объекта из таблички самолеты
    @FXML
    private void editModer() throws ParseException, IOException, InterruptedException {
        if (user == null) lab3.setText("Оператор не выбран \nвыберите оператора");
        else if (user.getName().equals(tx4.getText()) && user.getLastname().equals(tx5.getText()))
            lab3.setText("Вы не изменили данные\nоператора");
        else {
            if(sqlcodeAdmistrator.editModerQuery(user, tx4.getText(), tx5.getText())) lab3.setText("Данные изменены");
            else lab3.setText("Данные не изменены");
            textClean2();
        }
    } // Редактировать модератора
    protected void textClean2() throws ParseException, IOException, InterruptedException {
        tx4.setText("");
        tx5.setText("");
        user = null;
        if(searchField.equals("")) tableview2();
        else findModerInSystem();
    } // нажатие кнопки очистить поля в плашке модераторы
    @FXML
    protected void deleteButtonClick() throws ParseException, IOException, InterruptedException {
        if (user == null) {
            lab3.setText("Модер не выбран");
            return;
        }
        if (sqlcodeAdmistrator.deleteButtonClickSql(user)) {
            lab3.setText("Модер удален");
        }
        else {
            lab3.setText("Модер не удален");
        }
        textClean2();
    } // кнопка удалить модератора

    @FXML
    private void findModerInSystem(){
        ObservableList<Moder> forTable = null;
        try{
            forTable = ssa.findDefiniteModers(searchField.getText());
        } catch (RuntimeException ex){
            errorLabel.setText("Много слов в запросе");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
                    user = observableValue.getValue();
                    sel2();
                }
                catch (NullPointerException ignored){
                }
            }
        });
        sel2();
    }
    @FXML
    private void cleanModerButton() throws ParseException, IOException, InterruptedException {
        tableview2();
        searchField.setText("");
    }

}
