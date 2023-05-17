package com.example.airport.objects;

import org.json.simple.JSONObject;

import java.util.Date;
import java.util.GregorianCalendar;

public class Flight implements Objects{
    private Integer id_flight;
    final private String id_user;
    final private Date creation_date;
    private Date departure_date;
    private Date arrival_date;
    private String departure_city;
    private String arrival_city;

    private int id_plane;
    public Flight(Integer id_flight, String id_user, Date creation_date, Date departure_date, Date arrival_date, String departure_city, String arrival_city, int id_plane) {
        this.id_flight = id_flight;
        this.id_user = id_user;
        this.creation_date = creation_date;
        this.departure_date = departure_date;
        this.arrival_date = arrival_date;
        this.departure_city = departure_city;
        this.arrival_city = arrival_city;
        this.id_plane = id_plane;
    }
//    public Flight(String id_user, Date creation_date, Date departure_date, Date arrival_date, String departure_city, String arrival_city) {
//        this.id_user = id_user;
//        this.creation_date = creation_date;
//        this.departure_date = departure_date;
//        this.arrival_date = arrival_date;
//        this.departure_city = departure_city;
//        this.arrival_city = arrival_city;
//    }
    public String getId_user() {
        return id_user;
    }
    public Date getCreation_date() {
        return creation_date;
    }
    public Date getDeparture_date() {
        return departure_date;
    }
    public Date getArrival_date() {
        return arrival_date;
    }
    public String getArrival_city() {
        return arrival_city;
    }
    public String getDeparture_city() {
        return departure_city;
    }
    public Integer getId_flight() {
        return id_flight;
    }

    public int getId_plane() {
        return id_plane;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        result.put("id_flight", this.getId_flight());
        result.put("id_user", this.getId_user());
        result.put("creation_date", this.getCreation_date());
        result.put("departure_date", this.getDeparture_date());
        result.put("arrival_date", this.getArrival_date());
        result.put("departure_city", this.getDeparture_city());
        result.put("arrival_city", this.getArrival_city());
        result.put("id_plane", this.getId_plane());
        JSONObject result1 = new JSONObject();
        result1.put("Flight", result);
        return result1;
    }
    public static Objects fromJSONObject(JSONObject object) {
        JSONObject res = (JSONObject) object.get("Flight");
        return new Flight((Integer) res.get("id_flight"),
                (String) res.get("id_user"),
                (Date) res.get("creation_date"),
                (Date) res.get("departure_date"),
                (Date) res.get("arrival_date"),
                (String) res.get("departure_city"),
                (String) res.get("arrival_city"),
                (Integer) res.get("id_plane")
                );
    }
    @Override
    public String toString(){
        return id_flight + " " + departure_city + " - " + arrival_city + " " + departure_date;
    }
}

