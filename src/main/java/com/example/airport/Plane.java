package com.example.airport;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Plane {
    private final IntegerProperty id_plane;
    private StringProperty model;
    private StringProperty fullTitle;
    private IntegerProperty numberOfSeats;
    public Plane (int id_plane, String model, String fullTitle, int numberOfSeats){
        this.id_plane = new SimpleIntegerProperty(id_plane);
        this.model = new  SimpleStringProperty (model);
        this.fullTitle = new SimpleStringProperty (fullTitle);
        this.numberOfSeats = new SimpleIntegerProperty(numberOfSeats) ;
    }

//    public int getNumberOfSeats() {
//        return numberOfSeats;
//    }
//    public String getFullTitle() {
//        return fullTitle;
//    }
//    public String getModel() {
//        return model;
//    }
//    public int getId_plane() {
//        return id_plane;
//    }
//    public void setModel(String model) {
//        this.model = model;
//    }
//    public void setFullTitle(String fullTitle) {
//        this.fullTitle = fullTitle;
//    }
//    public void setNumberOfSeats(int numberOfSeats) {
//        this.numberOfSeats = numberOfSeats;
//    }
    @Override
    public String toString() {
        return id_plane.toString() + model.toString()  + fullTitle.toString()  + numberOfSeats.toString() ;
    }
    public IntegerProperty id_planeProperty(){
        return id_plane;
    }
    public IntegerProperty numberOfSeatsProperty() {
        return (SimpleIntegerProperty) numberOfSeats;
    }
    public StringProperty fullTitleProperty() {
        return fullTitle;
    }
    public StringProperty modelProperty() {
        return model;
    }

}
