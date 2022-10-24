package com.example.airport;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.airport.HelloApplication.stage;
import static com.example.airport.HelloApplication.stage1;

public class HelloController {
    @FXML
    private Button btn;
    @FXML
    private Button b1;

    @FXML
    protected void btnClick(ActionEvent event) throws IOException {
        HelloApplication.menuPassenger();
    }
    @FXML
    protected void b1Click(ActionEvent event) throws IOException {
        stage1.close();
        stage1 = new Stage();
    }
    @FXML
    protected void btnClick1(ActionEvent event) throws IOException {
        HelloApplication.menuAdministrator();
    }
//    @FXML
//    protected void btnchoice1(ActionEvent event) throws IOException {
//        HelloApplication.menuAdministrator1();
//    }
}