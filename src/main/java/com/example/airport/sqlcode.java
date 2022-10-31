package com.example.airport;
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
