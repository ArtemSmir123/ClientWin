package com.example.airport.objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.simple.JSONObject;

public class Plane implements Objects{
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
        return " " + id_plane.toString() + " " + model.toString() + " " + fullTitle.toString() + " " + numberOfSeats.toString() ;
    }
//    public IntegerProperty id_planeProperty(){
//        return new SimpleIntegerProperty(id_plane);
//    }
//    public IntegerProperty numberOfSeatsProperty() {
//        return new SimpleIntegerProperty(numberOfSeats);
//    }
//    public StringProperty fullTitleProperty() {
//        return  new SimpleStringProperty(fullTitle);
//    }
//    public StringProperty modelProperty() {
//        return new SimpleStringProperty(model);
//    }

    public String getModel() {
        return model;
    }
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }
    public String getFullTitle() {
        return fullTitle;
    }
    public Integer getId_plane() {
        return id_plane;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        result.put("id_plane", this.getId_plane());
        result.put("model", this.getModel());
        result.put("fullTitle", this.getFullTitle());
        result.put("numberOfSeats", this.getNumberOfSeats());
        JSONObject result1 = new JSONObject();
        result1.put("Plane", result);
        return result1;
    }
    public static Objects fromJSONObject(JSONObject object) {
        JSONObject res = (JSONObject) object.get("Plane");
        return new Plane((Integer) res.get("id_plane"),
                (String) res.get("model"),
                (String) res.get("fullTitle"),
                (Integer) res.get("numberOfSeats"));
    }

}
