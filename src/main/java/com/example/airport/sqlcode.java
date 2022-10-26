package com.example.airport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;

public class sqlcode {
    static Scanner input = new Scanner(System.in);
    static Connection connection;
    static Statement stmt;
    private static void connect(){
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
    public static boolean savePlane(String plane1, String plane2, int plane3) {
        connect();
        int v = 0;
        try {
            v = stmt.executeUpdate("INSERT INTO public.\"Planes\" (model,fullTitle, numberOfSeats)\n" +
                    "VALUES ('" + plane1 + "', '" + plane2 + "', " + plane3 + ")");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return v == 1;
    } // сохранить самолет
    protected static boolean findPlane(String model){
        connect();
        ResultSet s;
        try {
            s = stmt.executeQuery("SELECT model FROM public.\"Planes\" WHERE model = '" + model + "'");
            s.next();
            connection.close();
            if (s.getString("model").equals(model)) return false;
        } catch (SQLException ex){
        }
        return true;
    } // найти самолет
    protected static ObservableList<Plane> findPlanes(){
        connect();
        ResultSet s;
        ObservableList<Plane> result = FXCollections.observableArrayList();
        try {
            s = stmt.executeQuery("SELECT * FROM public.\"Planes\"" );
            s.next();
            connection.close();
            List<Plane> planes = new ArrayList<>();
            while (s.next()) {
                result.add(new Plane(s.getInt(1), s.getString("model"), s.getString("fullTitle"), s.getInt("numberOfSeats")));
            }
        } catch (SQLException e) {
            System.out.println("dfjdk");
        }
        return result;
    } // найти самолеты
    protected static int findUser(String login, String pass){
        connect();
        ResultSet s;
            try {
                s = stmt.executeQuery("SELECT role FROM public.\"Users\" WHERE login = '" + login + "' AND password = '" + pass + "'");
                s.next();
                connection.close();
                if (s.getString(1).equals("moder")) return 1;
                else if (s.getString(1).equals("admin")) return 0;
                else return -1;
            } catch (SQLException ex){
                return -1;
            }
    } // найти пользователя для login

}
