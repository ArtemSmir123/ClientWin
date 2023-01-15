package com.example.airport.objects;

import java.util.GregorianCalendar;

public class Flight {
    private Integer id_flight;
    final Integer id_user;
    final GregorianCalendar creation_date;
    GregorianCalendar departure_date;
    GregorianCalendar arrival_date;
    String departure_city;
    String arrival_city;

    Flight(Integer id_flight, Integer id_user, GregorianCalendar creation_date, GregorianCalendar departure_date, GregorianCalendar arrival_date, String departure_city,String arrival_city) {
        this.id_flight = id_flight;
        this.id_user = id_user;
        this.creation_date = creation_date;
        this.departure_date = departure_date;
        this.arrival_date = arrival_date;
        this.departure_city = departure_city;
        this.arrival_city = arrival_city;
    }
    Flight( Integer id_user, GregorianCalendar creation_date, GregorianCalendar departure_date, GregorianCalendar arrival_date, String departure_city,String arrival_city) {
        this.id_user = id_user;
        this.creation_date = creation_date;
        this.departure_date = departure_date;
        this.arrival_date = arrival_date;
        this.departure_city = departure_city;
        this.arrival_city = arrival_city;
    }
}

