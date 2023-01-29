package com.example.airport.MenuModerator;

import com.example.airport.objects.Flight;
import com.example.airport.objects.Plane;
import com.example.airport.sqlcode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

class sqlcodeModerator extends sqlcode {
    @Override
    protected ObservableList<Plane> findPlanes(){
        return super.findPlanes();
    }

    protected boolean saveFlight(Flight flight){
        connect();
        int v = 0;
        try {
            v = stmt.executeUpdate("INSERT INTO public.\"Flights\" (id_user, creation_date, departure_date, arrival_date, departure_city, arrival_city, id_plane)\n" +
                    "VALUES ('" + flight.getId_user() + "', '" + flight.getCreation_date() + "', '" + flight.getDeparture_date() + "','" + flight.getArrival_date() + "', '" + flight.getDeparture_city()+ "', '" + flight.getArrival_city() + "', '" + flight.getId_flight() + "')");
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return v == 1;
    }

}
