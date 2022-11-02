package com.example.airport;
import com.example.airport.objects.Admin;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Users;

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
    protected static Users findUser(String login, String pass){
        connect();
        ResultSet s;
            try {
                s = stmt.executeQuery("SELECT * FROM public.\"Users\" WHERE login = '" + login + "' AND password = '" + pass + "'");
                s.next();
                connection.close();
                if (s.getString("role").equals("moder"))
                    return new Moder(s.getString("login"), s.getString("password"), s.getString("role"), s.getString("name"), s.getString("lastname"));
                else if (s.getString("role").equals("admin"))
                    return new Admin(s.getString("login"), s.getString("password"), s.getString("role"), s.getString("name"), s.getString("lastname"));
                else return null;
            } catch (SQLException ex){
                return null;
            }
    } // найти пользователя для login

}
