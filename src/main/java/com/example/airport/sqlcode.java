package com.example.airport;
import com.example.airport.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;

public class sqlcode {
    protected static Connection connection;
    protected static Statement stmt;
    protected static HttpClient client = HttpClient.newHttpClient();
    protected static JSONParser parser = new JSONParser();
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

    protected static Users findUser(String login, String pass) throws IOException, InterruptedException, ParseException {

        JSONObject object = new JSONObject();
        object.put("login", login);
        object.put("pass", pass);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8000/findUser")).POST(HttpRequest.BodyPublishers.ofString(object.toJSONString())).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject result = (JSONObject) parser.parse(response.body());
        if (result.get("role").equals("admin")){
            return new Admin((String) result.get("login"), (String) result.get("password"), (String) result.get("role"), (String) result.get("name"), (String) result.get("lastname"));
        } else if (result.get("role").equals("moder")){
            return new Moder((String) result.get("login"), (String) result.get("password"), (String) result.get("role"), (String) result.get("name"), (String) result.get("lastname"));
        }
        return null;
    } // найти пользователя для login

    protected static boolean findLogin(int login) throws IOException, InterruptedException, ParseException {
        JSONObject object = new JSONObject();
        object.put("query", login);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8000/findLogin")).POST(HttpRequest.BodyPublishers.ofString(object.toJSONString())).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject result = (JSONObject) parser.parse(response.body());
        return (boolean) result.get("result");
    }
    protected ObservableList<Plane> findPlanes() throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8000/findPlanes")).build();
        HttpResponse<String> response = null;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject result = (JSONObject) parser.parse(response.body());
        JSONArray result1 = (JSONArray) result.get("Plane");
        ObservableList<Plane> finalResult = FXCollections.observableArrayList();

        for (int i = 0; i < result1.size(); i++){
            JSONObject result3 = (JSONObject) result1.get(i);
            finalResult.add(new Plane(
                    Integer.parseInt(result3.get("id_plane").toString()),
                    String.valueOf(result3.get("model")),
                    String.valueOf(result3.get("fullTitle")),
                    Integer.parseInt(result3.get("numberOfSeats").toString())));
        }
        return finalResult;
    } // ищем самолеты для выгрузки

}
