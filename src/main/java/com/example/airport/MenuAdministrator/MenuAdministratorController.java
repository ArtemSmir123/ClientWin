package com.example.airport.MenuAdministrator;

import com.example.airport.objects.Plane;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

//import static com.example.airport.sqlcode.findPlane;
//import static com.example.airport.sqlcode.findPlanes;

public class MenuAdministratorController {
    public MenuAdministratorController(){

    }
    sqlcodeAdmistrator ssa = new sqlcodeAdmistrator();
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
    private TextField plane1;
    @FXML
    private TextField plane2;
    @FXML
    private TextField plane3;
    @FXML
    protected void bb1Click(){
        tx1.setText("");
        tx2.setText("");
        tx3.setText("");
    }
    @FXML
    protected void tableView(){
        ObservableList<Plane> forTable = ssa.findPlanes();
        table1.setItems(forTable);
        k1.setCellValueFactory(new PropertyValueFactory<Plane, String>("model"));
        k2.setCellValueFactory(new PropertyValueFactory<Plane, String>("fullTitle"));
        k3.setCellValueFactory(new PropertyValueFactory<Plane, Integer>("numberOfSeats"));
    } //табличка с самолетами
    @FXML
    protected void saveBtn1(ActionEvent event){
        boolean saveStatus = false;
        boolean findStatus = ssa.findPlane(plane1.getText());
        if (findStatus) {
            try {
                saveStatus = sqlcodeAdmistrator.savePlane(plane1.getText(), plane2.getText(), Integer.parseInt(plane3.getText()));
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

}
