package com.example.airport.MenuAdministrator;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Plane;
import com.example.airport.objects.Users;
import com.example.airport.sqlcode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class sqlcodeAdmistrator extends sqlcode {

    @Override
    protected ObservableList<Plane> findPlanes() throws IOException, ParseException, InterruptedException {
        return super.findPlanes();
    }
    protected boolean findPlane(String model){
        connect();
        ResultSet s;
        try {
            s = sqlcode.stmt.executeQuery("SELECT model FROM public.\"Planes\" WHERE model = '" + model + "'"); // переделать
            disconnect();
            s.next();
            if (s.getRow() != 0) return false;
            else return true;
        } catch (SQLException ex){
            return true;
        }
    } // найти самолет, если нашли то true
//    protected static Plane findPlane(Integer id){
//        connect();
//        ResultSet s;
//        Plane plane = null;
//        try {
//            s = sqlcode.stmt.executeQuery("SELECT * FROM public.\"Planes\" WHERE id_planes = '" + id + "'");
//            s.next();
//            disconnect();
//            String mod = s.getString("model");
//            String tit = s.getString("fulltitle");
//            Integer i = s.getInt("numberofseats");
//
//            plane = new Plane(id, s.getString("model"), s.getString("fulltitle"), s.getInt("numberofseats"));
//        } catch (SQLException ex){
//            System.out.println();
//            ex.printStackTrace();
//        }
//        return plane;
//    } // найти самолет и вернуть объект самолет
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

    protected static boolean findLogin(int login) throws IOException, ParseException, InterruptedException {
        return sqlcode.findLogin(login);
    } // проверка по логину
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
    } // сохранение модератора
    protected ObservableList<Moder> findModers(){
        connect();
        ResultSet s;
        ObservableList<Moder> result = FXCollections.observableArrayList();
        try {
            s = stmt.executeQuery("SELECT * FROM public.\"Users\" WHERE role = 'moder'" );
            connection.close();
            while (s.next()) {
                result.add(new Moder(s.getString("login"), s.getString("password"), s.getString("role"), s.getString("name"), s.getString("lastname")));
            }
        } catch (SQLException e) {
        }
        return result;
    } // ищем модеров для выгрузки\

    protected static boolean editModerQuery(Moder user, String name, String lastname) {
        connect();
        int v = 0;
        try {
            v = stmt.executeUpdate("UPDATE public.\"Users\"" + " SET name = '" + name + "', lastname = '" + lastname + "' WHERE login = '" + user.getLogin() + "'");
            disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return v == 1;
    }
//    protected static Moder findModer(String idModer){
//        connect();
//        ResultSet s;
//        Moder result;
//        try {
//            s = stmt.executeQuery("SELECT * FROM public.\"Users\" WHERE login = '" + idModer + "'" );
//            connection.close();
//            s.next();
//            result = new Moder(s.getString("login"), s.getString("password"), s.getString("role"), s.getString("name"), s.getString("lastname"));
//            return result;
//        } catch (SQLException e) {
//            return null;
//        }
//    } // ищем модера для отображения
    protected static boolean deleteButtonClickSql(Moder user){
        connect();
        int v = 0;
        try{
            v = sqlcode.stmt.executeUpdate("DELETE FROM public.\"Users\" WHERE login = '"+ user.getLogin().toString() + "'");
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return v == 1;
    }

    protected ObservableList<Moder> findDefiniteModers(String searchField){
        connect();
        ResultSet s;
        ObservableList<Moder> result = FXCollections.observableArrayList();
        String searchField1[];

        searchField = searchField.toLowerCase();
        searchField1 = searchField.split(" ");
        System.out.println(searchField1.length);
        if(searchField1.length > 2) throw new RuntimeException();
        // модуль преобразования строки
        else if (searchField1.length == 2) {
            try {

                if(isDight(searchField)) {
                    s = stmt.executeQuery("SELECT *\n" + "FROM public.\"Users\"\n" + "WHERE role = 'moder' AND ((login LIKE '%"+ searchField +"%') OR (login LIKE '%" + searchField + "') OR (login LIKE '" + searchField + "%'))"); // поиск по логину
                } else {
                    s = stmt.executeQuery("SELECT * \n" +
                            "FROM public.\"Users\"\n" +
                            "WHERE role = 'moder' AND (\n" +
                            "\t(\n" +
                            "\t\t((name COLLATE \"ru_RU\") ILIKE '%" + searchField1[0] + "%') AND\n" +
                            "\t\t((lastname COLLATE \"ru_RU\") ILIKE '%" + searchField1[1] + "%') \n" +
                            "\t) or (\n" +
                            "\t\t((name COLLATE \"ru_RU\") ILIKE '%" + searchField1[1] + "%') AND\n" +
                            "\t\t((lastname COLLATE \"ru_RU\") ILIKE '%" + searchField1[0] + "%') \n" +
                            "\t) or (\n" +
                            "\t\t((name COLLATE \"ru_RU\") ILIKE '%" + searchField1[0] + "%')\n" +
                            "\t) or (\n" +
                            "\t\t((name COLLATE \"ru_RU\") ILIKE '%" + searchField1[1] + "%')\n" +
                            "\t) or (\n" +
                            "\t\t((lastname COLLATE \"ru_RU\") ILIKE '%" + searchField1[0] + "%') \n" +
                            "\t) or (\n" +
                            "\t\t((lastname COLLATE \"ru_RU\") ILIKE '%" + searchField1[1] + "%') \n" +
                            "\t)\n" +
                            ")\n ORDER BY CASE \n" +
                            "WHEN (\n" +
                            "\t((name COLLATE \"ru_RU\") ILIKE '%" + searchField1[0] + "%') AND\n" +
                            "\t((lastname COLLATE \"ru_RU\") ILIKE '%" + searchField1[1] + "%')\n" +
                            ") THEN 1\n" +
                            "WHEN (((name COLLATE \"ru_RU\") ILIKE '%" + searchField1[1] + "%') AND\n" +
                            "\t((lastname COLLATE \"ru_RU\") ILIKE '%" + searchField1[0] + "%')\n" +
                            ") THEN 1 \n" +
                            "ELSE 2\n" +
                            "END"); // поиск по параметрам
                }
                connection.close();
                while (s.next()) {
                    result.add(new Moder(s.getString("login"), s.getString("password"), s.getString("role"), s.getString("name"), s.getString("lastname")));
                }
            } catch (SQLException e) {
            }
        }
        else {
            try {

                if(isDight(searchField)) {
                    s = stmt.executeQuery("SELECT *\n" + "FROM public.\"Users\"\n" + "WHERE role = 'moder' AND ((login LIKE '%"+ searchField +"%') OR (login LIKE '%" + searchField + "') OR (login LIKE '" + searchField + "%'))"); // поиск по логину
                } else {
                    s = stmt.executeQuery("SELECT * \n" +
                            "FROM public.\"Users\"\n" +
                            "WHERE role = 'moder' AND (\n" +
                            "\t(\n" +
                            "\t\t((name COLLATE \"ru_RU\") ILIKE '%" + searchField1[0] + "%')\n" +
                            "\t) OR \n" +
                            "\t(\n" +
                            "\t\t((lastname COLLATE \"ru_RU\") ILIKE '%" + searchField1[0] + "%') \n" +
                            "\t)\n" +
                            ")\n"); // поиск по параметрам
                }
                connection.close();
                while (s.next()) {
                    result.add(new Moder(s.getString("login"), s.getString("password"), s.getString("role"), s.getString("name"), s.getString("lastname")));
                }
            } catch (SQLException e) {
            }
        }
        return result;
    } // ищем определенного модера для выгрузки
    private boolean isDight(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    } // проверка на число
}


