package com.example.airport;
import com.example.airport.objects.Admin;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Plane;
import com.example.airport.objects.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
public class sqlcode {
    protected static Connection connection;
    protected static Statement stmt;
    protected static void connect(){
        final String USER = "postgres";
        final String PASS = "1234";
        final String DB_URL = "jdbc:postgresql://localhost:5432/Flights?currentSchema=public&user=" + USER + "&password=" + PASS ;
        try {
            connection = DriverManager.getConnection(DB_URL);
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Подключение не удалось");
        }
    } // коннект
    protected static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } // коннект

    protected static Users findUser(String login, String pass){
        connect();
        ResultSet s;
            try {
                s = stmt.executeQuery("SELECT * FROM public.\"Users\" WHERE login = '" + login + "' AND password = '" + pass + "'");
                s.next();
                disconnect();
                if (s.getString("role").equals("moder"))
                    return new Moder(s.getString("login"), s.getString("password"), s.getString("role"), s.getString("name"), s.getString("lastname"));
                else if (s.getString("role").equals("admin"))
                    return new Admin(s.getString("login"), s.getString("password"), s.getString("role"), s.getString("name"), s.getString("lastname"));
                else return null;
            } catch (SQLException ex){
                return null;
            }
    } // найти пользователя для login

    protected static boolean findLogin(int login){
        connect();
        ResultSet s;
        try {
            s = stmt.executeQuery("SELECT login FROM public.\"Users\" WHERE login = '" + String.valueOf(login)+ "'");
            s.next();

            if (s.getFetchSize() == 1) return true;
            else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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

}
