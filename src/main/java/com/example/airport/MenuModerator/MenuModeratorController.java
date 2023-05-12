package com.example.airport.MenuModerator;
import com.example.airport.MainApp;
import com.example.airport.objects.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
public class MenuModeratorController {
    public MenuModeratorController() {
    }
    sqlcodeModerator ssa = new sqlcodeModerator();
    @FXML
    private void initialize() throws IOException, ParseException, InterruptedException {
        tableView();
    }
    private static Moder us; // Владелец сесси
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Plane> table1; // Редактирование самолетов
    @FXML
    private TableColumn<Plane, String> k1; // Редактирование самолетов
    @FXML
    private TableColumn<Plane, String> k2; // Редактирование самолетов
    @FXML
    private TableColumn<Plane, Integer> k3; // Редактирование самолетов
    @FXML
    private Button examinationButton; // Кнопка проверки
    @FXML
    private DatePicker dateOfFlight;
    @FXML
    private TextField timeField;
    @FXML
    private TextField timeField1;
    @FXML
    private TextField timeField2;
    @FXML
    private TextField timeField3;
    @FXML
    private TextField departureCity;
    @FXML
    private TextField arrivalCity;
    Plane selectedPlane;
    @FXML
    private Label selectedPlane1;
    public void setUser(Users us) {
        MenuModeratorController.us = (Moder) us;
    } // установить пользователя, как владельца сессии
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
            public void changed (ObservableValue< ? extends Plane > observableValue, Plane plane, Plane t1){
                try {
                    selectedPlane = selectionModel.getSelectedItem();
                    selectedPlane1.setText("Выбран самолет: " + selectedPlane.toString());
                }
                catch (NullPointerException ignored){
                }
            }
        });
    } // Табличка самолетов
    @FXML
    private void examination(){
        boolean exam = true; // все хорошо
        if(selectedPlane == null){
            errorLabel.setText("Не выбран самолет");
            exam = false;
        }
        if(!isDightHour(timeField.getText())) {
            errorLabel.setText("Одно из чисел - не число или не соответствует формату");
            timeField.setText("");
            exam = false;
        }
        if(!isDightMinute(timeField1.getText())) {
            errorLabel.setText("Одно из чисел - не число или не соответствует формату");
            timeField1.setText("");
            exam = false;
        }
        if(!isDightHour(timeField2.getText())) {
            errorLabel.setText("Одно из чисел - не число или не соответствует формату");
            timeField2.setText("");
            exam = false;
        }
        if(!isDightMinute(timeField3.getText())) {
            errorLabel.setText("Одно из чисел - не число или не соответствует формату");
            timeField3.setText("");
            exam = false;
        }
        LocalDate las = dateOfFlight.getValue();
        Calendar sd = new GregorianCalendar(las.getYear(), las.getMonth().getValue() - 1, las.getDayOfMonth());
        long sd1 = sd.getTimeInMillis();
        long sd2 = (new GregorianCalendar()).getTimeInMillis();
        if(sd1 < sd2){
            errorLabel.setText("Дата раньше, чем сегодня");
            exam = false;
        }
        if(exam) {
            examinationButton.setText("Проверено");
            errorLabel.setText("Проверено");
        }
    } // Проверки для выгрузки в БД рейса
    @FXML
    private void saveFlight(){
        if(examinationButton.getText().equals("Проверено")) {
            LocalDate las = dateOfFlight.getValue();
            Calendar departure_date = new GregorianCalendar(las.getYear(), las.getMonth().getValue(), las.getDayOfMonth());
            departure_date.set(Calendar.HOUR, Integer.parseInt(timeField.getText()));
            departure_date.set(Calendar.MINUTE, Integer.parseInt(timeField1.getText()));
            Calendar currentDate = new GregorianCalendar();
            Calendar arrival_date = new GregorianCalendar();
            arrival_date = (Calendar) departure_date.clone();
            arrival_date.add(Calendar.HOUR, Integer.parseInt(timeField2.getText()));
            arrival_date.add(Calendar.MINUTE, Integer.parseInt(timeField3.getText()));
            ssa.saveFlight(new Flight(selectedPlane.getId_plane(), us.getLogin(), currentDate.getTime(), departure_date.getTime(), arrival_date.getTime(), departureCity.getText() , arrivalCity.getText()));
        } else {
            errorLabel.setText("Проверки не пройдены");
        }
    } // Сохранить рейс
    private boolean isDightHour(String s){
        try {
            int res = Integer.parseInt(s);
            if (res >= 0 && res < 24) {
                return true;
            } else throw new NumberFormatException();
        } catch (NumberFormatException ex){
            return false;
        }
    } // проверка на число и часы
    private boolean isDightMinute(String s){
        try {
            int res = Integer.parseInt(s);
            if (res >= 0 && res < 60) {
                return true;
            } else throw new NumberFormatException();
        } catch (NumberFormatException ex){
            return false;
        }
    } // проверка на число и минуты
    @FXML
    private void changed(){
        examinationButton.setText("Проверить");
    } // событие "Поле изменилось"
}
