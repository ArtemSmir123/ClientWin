package com.example.airport.MenuModerator;

import com.example.airport.objects.Plane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MenuModeratorController {
    public MenuModeratorController() {
    }
    sqlcodeModerator ssa = new sqlcodeModerator();
    @FXML
    private void initialize() {
        tableView();
    }
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
    private Button examinationButton;

    Plane selectedPlane;
    @FXML
    private Label selectedPlane1;
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
        examinationButton.setText("Проверено");
    }
}
