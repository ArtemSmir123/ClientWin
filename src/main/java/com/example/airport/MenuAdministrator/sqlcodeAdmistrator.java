package com.example.airport.MenuAdministrator;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Plane;
import com.example.airport.objects.Users;
import com.example.airport.sqlcode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class sqlcodeAdmistrator extends sqlcode {

    @Override
    protected ObservableList<Plane> findPlanes() throws IOException, ParseException, InterruptedException {
        return super.findPlanes();
    }
    protected boolean findPlane(String model) throws IOException, InterruptedException, ParseException {
        StringBuilder query = new StringBuilder(model);
        JSONObject query1 = new JSONObject();
        query1.put("query", query.toString());
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "findPlane")).POST(HttpRequest.BodyPublishers.ofString(query1.toJSONString())).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject result = new JSONObject();
        result = (JSONObject) parser.parse(response.body());
        return Boolean.parseBoolean(result.get("result").toString());
    } // найти самолет, если нашли то true
    protected static boolean deletePlane(Integer id) throws IOException, InterruptedException, ParseException {
        JSONObject query1 = new JSONObject();
        query1.put("query", id);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "deletePlane")).POST(HttpRequest.BodyPublishers.ofString(query1.toJSONString())).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject result = new JSONObject();
        result = (JSONObject) parser.parse(response.body());
        return Boolean.parseBoolean(result.get("result").toString());
    } // удалить самолет
    protected static boolean savePlane(String plane1, String plane2, int plane3) throws IOException, InterruptedException, ParseException {
        JSONObject query1 = new JSONObject();
        query1.put("model", plane1);
        query1.put("fullTitle", plane2);
        query1.put("numberOfSeats", String.valueOf(plane3));
        JSONObject query2 = new JSONObject();
        query2.put("Plane", query1);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "savePlane")).POST(HttpRequest.BodyPublishers.ofString(query2.toJSONString())).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject result = new JSONObject();
        result = (JSONObject) parser.parse(response.body());

        return Boolean.parseBoolean(result.get("result").toString());
    } // сохранить самолет в БД
    protected static boolean updatePlane(@NotNull Plane plane) throws IOException, InterruptedException, ParseException {
        JSONObject query1 = new JSONObject();
        query1.put("id_plane", plane.getId_plane().toString());
        query1.put("model", plane.getModel());
        query1.put("fullTitle", plane.getFullTitle());
        query1.put("numberOfSeats", plane.getNumberOfSeats().toString());
        JSONObject query2 = new JSONObject();
        query2.put("Plane", query1);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "updatePlane")).POST(HttpRequest.BodyPublishers.ofString(query2.toJSONString())).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject result;
        result = (JSONObject) parser.parse(response.body());
        return Boolean.parseBoolean(result.get("result").toString());
    } // редактирование самолета в БД

    protected static boolean findLogin(int login) throws IOException, ParseException, InterruptedException {
        return sqlcode.findLogin(login);
    } // проверка по логину
    protected boolean saveModer(Moder moder) throws IOException, InterruptedException, ParseException {
        JSONObject query1 = new JSONObject();
        query1.put("login", moder.getLogin());
        query1.put("password", moder.getPassword());
        query1.put("role", moder.getRole());
        query1.put("name", moder.getName());
        query1.put("lastname", moder.getLastname());
        JSONObject query2 = new JSONObject();
        query2.put("Moder", query1);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "saveModer")).POST(HttpRequest.BodyPublishers.ofString(query2.toJSONString())).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject result;
        result = (JSONObject) parser.parse(response.body());
        return Boolean.parseBoolean(result.get("result").toString());
    } // сохранение модератора
    protected ObservableList<Moder> findModers() throws ParseException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "findModers")).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject result = (JSONObject) parser.parse(response.body());
        JSONArray result1 = (JSONArray) result.get("Moder");
        ObservableList<Moder> finalResult = FXCollections.observableArrayList();

        for (int i = 0; i < result1.size(); i++){
            JSONObject result3 = (JSONObject) result1.get(i);
            finalResult.add(new Moder(
                    String.valueOf(result3.get("login")),
                    String.valueOf(result3.get("password")),
                    String.valueOf(result3.get("role")),
                    String.valueOf(result3.get("name")),
                    String.valueOf(result3.get("lastname"))));
        }
        return finalResult;
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


