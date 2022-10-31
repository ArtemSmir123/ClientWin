package com.example.airport.objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Plane {
    private final Integer id_plane;
    private String model;
    private String fullTitle;
    private Integer numberOfSeats;
    public Plane (int id_plane, String model, String fullTitle, int numberOfSeats){
        this.id_plane = id_plane;
        this.model = model;
        this.fullTitle = fullTitle;
        this.numberOfSeats = numberOfSeats ;
    }
    @Override
    public String toString() {
        return id_plane.toString() + model.toString()  + fullTitle.toString()  + numberOfSeats.toString() ;
    }
    public IntegerProperty id_planeProperty(){
        return new SimpleIntegerProperty(id_plane);
    }
    public IntegerProperty numberOfSeatsProperty() {
        return new SimpleIntegerProperty(numberOfSeats);
    }
    public StringProperty fullTitleProperty() {
        return  new SimpleStringProperty(fullTitle);
    }
    public StringProperty modelProperty() {
        return new SimpleStringProperty(model);
    }

}
