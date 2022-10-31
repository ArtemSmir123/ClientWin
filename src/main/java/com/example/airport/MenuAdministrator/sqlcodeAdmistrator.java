package com.example.airport.MenuAdministrator;
import com.example.airport.objects.Plane;
import com.example.airport.sqlcode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class sqlcodeAdmistrator extends sqlcode {
    sqlcodeAdmistrator(){

    }
    protected ObservableList<Plane> findPlanes(){
        connect();
        ResultSet s;
        ObservableList<Plane> result = FXCollections.observableArrayList();
        try {
            s = stmt.executeQuery("SELECT * FROM public.\"Planes\"" );
            //   s.next();
            connection.close();
            List<Plane> planes = new ArrayList<>();
            while (s.next()) {
                result.add(new Plane(s.getInt(1), s.getString("model"), s.getString("fullTitle"), s.getInt("numberOfSeats")));
            }
        } catch (SQLException e) {
            System.out.println("dfjdk");
        }
        return result;
    }
    protected boolean findPlane(String model){
        connect();
        ResultSet s;
        try {
            s = sqlcode.stmt.executeQuery("SELECT model FROM public.\"Planes\" WHERE model = '" + model + "'");
            s.next();
            sqlcode.connection.close();
            if (s.getString("model").equals(model)) return false;
        } catch (SQLException ex){
        }
        return true;
    } // найти самолет
}
