package com.example.airport.MenuAdministrator;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Plane;
import com.example.airport.sqlcode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class sqlcodeAdmistrator extends sqlcode {
    protected ObservableList<Plane> findPlanes(){
        connect();
        ResultSet s;
        ObservableList<Plane> result = FXCollections.observableArrayList();
        try {
            s = stmt.executeQuery("SELECT * FROM public.\"Planes\"" );
            connection.close();
            while (s.next()) {
                result.add(new Plane(s.getInt(1), s.getString("model"), s.getString("fullTitle"), s.getInt("numberOfSeats")));
            }
        } catch (SQLException e) {
        }
        return result;
    } // ищем самолеты для выгрузки
    protected boolean findPlane(String model){
        connect();
        ResultSet s;
        try {
            s = sqlcode.stmt.executeQuery("SELECT model FROM public.\"Planes\" WHERE model = '" + model + "'");
            s.next();
            disconnect();
            if (s.getString("model").equals(model)) return true;
        } catch (SQLException ex){
        }
        return false;
    } // найти самолет, если нашли то true
    protected static Plane findPlane(Integer id){
        connect();
        ResultSet s;
        Plane plane = null;
        try {
            s = sqlcode.stmt.executeQuery("SELECT * FROM public.\"Planes\" WHERE id_planes = '" + id + "'");
            s.next();
            disconnect();
            String mod = s.getString("model");
            String tit = s.getString("fulltitle");
            Integer i = s.getInt("numberofseats");

            plane = new Plane(id, s.getString("model"), s.getString("fulltitle"), s.getInt("numberofseats"));
        } catch (SQLException ex){
            System.out.println();
            ex.printStackTrace();
        }
        return plane;
    } // найти самолет и вернуть объект самолет
    protected static boolean deletePlane(Integer id){
        connect();
        int v = 0;
        try {
            v = stmt.executeUpdate("DELETE FROM public.\"Planes\" WHERE id_planes = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return v == 1;
    } // удалить самолет
    protected static boolean savePlane(String plane1, String plane2, int plane3) {
        connect();
        int v = 0;
        try {
            v = stmt.executeUpdate("INSERT INTO public.\"Planes\" (model,fullTitle, numberOfSeats)\n" +
                    "VALUES ('" + plane1 + "', '" + plane2 + "', " + plane3 + ")");
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return v == 1;
    } // сохранить самолет в БД
    protected static boolean updatePlane(@NotNull Plane plane){
        connect();
        int v = 0;
        try {
            v = stmt.executeUpdate("UPDATE public.\"Planes\"" + " SET model = '" + plane.getModel() +  "', fulltitle = '" + plane.getFullTitle() +  "', numberofseats = " + plane.getNumberOfSeats() +  " WHERE id_planes = " + plane.getId_plane());
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return v == 1;
    } // редактирование самолета в БД

    protected static boolean findLogin(int login){
        return sqlcode.findLogin(login);
    }
    protected boolean saveModer(Moder moder){
        connect();
        int v = 0;
        try {
            v = stmt.executeUpdate("INSERT INTO public.\"Users\"" + " VALUES ( '" + moder.getLogin() +  "', '" + moder.getPassword() + "', '" + moder.getRole() + "', '" + moder.getName() + "' , '" + moder.getLastname() + "')");
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return v == 1;
    }
}
