package com.example.airport.MenuAdministrator;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Plane;
import com.example.airport.objects.Users;
import com.example.airport.sqlcode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class sqlcodeAdmistrator extends sqlcode {

    @Override
    protected ObservableList<Plane> findPlanes() throws IOException, ParseException, InterruptedException {
        return super.findPlanes();
    }
    protected boolean findPlane(String model) throws IOException, InterruptedException, ParseException {
        StringBuilder query = new StringBuilder(model);
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("query", query.toString());

        HttpPost httpPost = httpPostQuery(objectQuery, "findPlane"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );
        JSONObject result;
        result = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        return Boolean.parseBoolean(result.get("result").toString());
    } // найти самолет, если нашли то true
    protected static boolean deletePlane(Integer id) throws IOException, InterruptedException, ParseException {
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("query", id);

        HttpPost httpPost = httpPostQuery(objectQuery, "deletePlane"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );
        JSONObject result = new JSONObject();
        result = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        return Boolean.parseBoolean(result.get("result").toString());
    } // удалить самолет
    protected static boolean savePlane(String plane1, String plane2, int plane3) throws IOException, InterruptedException, ParseException {
        JSONObject query1 = new JSONObject();
        query1.put("model", plane1);
        query1.put("fullTitle", plane2);
        query1.put("numberOfSeats", String.valueOf(plane3));
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("Plane", query1);

        HttpPost httpPost = httpPostQuery(objectQuery, "savePlane"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );

        JSONObject result = new JSONObject();
        result = (JSONObject) parser.parse(String.valueOf(stringBuilder));

        return Boolean.parseBoolean(result.get("result").toString());
    } // сохранить самолет в БД
    protected static boolean updatePlane(@NotNull Plane plane) throws IOException, InterruptedException, ParseException {
        JSONObject query1 = new JSONObject();
        query1.put("id_plane", plane.getId_plane().toString());
        query1.put("model", plane.getModel());
        query1.put("fullTitle", plane.getFullTitle());
        query1.put("numberOfSeats", plane.getNumberOfSeats().toString());
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("Plane", query1);

        HttpPost httpPost = httpPostQuery(objectQuery, "savePlane"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );

        JSONObject result;
        result = (JSONObject) parser.parse(String.valueOf(stringBuilder));
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
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("Moder", query1);

        HttpPost httpPost = httpPostQuery(objectQuery, "saveModer"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );
        JSONObject result;
        result = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        return Boolean.parseBoolean(result.get("result").toString());
    } // сохранение модератора
    protected ObservableList<Moder> findModers() throws ParseException, IOException, InterruptedException {

        HttpGet httpget = httpGetQuery("findModers");
        CloseableHttpResponse httpresponse = httpclient.execute(httpget);

        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );

        JSONObject result = (JSONObject) parser.parse(String.valueOf(stringBuilder));
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

    protected static boolean editModerQuery(Moder user, String name, String lastname) throws IOException, InterruptedException, ParseException {
        JSONObject query = new JSONObject();
        query.put("login", user.getLogin());
        query.put("password", user.getPassword());
        query.put("role", user.getRole());
        query.put("name", user.getName());
        query.put("lastname", user.getLastname());
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("Moder", query);
        objectQuery.put("name", name);
        objectQuery.put("lastname", lastname);

        System.out.println(objectQuery);
        HttpPost httpPost = httpPostQuery(objectQuery, "editModer"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );
        JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        return Boolean.parseBoolean(jsonObject.get("result").toString());
    }
    protected static boolean deleteButtonClickSql(Moder user) throws IOException, InterruptedException, ParseException {
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("login", user.getLogin());
        objectQuery.put("password", user.getPassword());
        objectQuery.put("role", user.getRole());
        objectQuery.put("name", user.getName());
        objectQuery.put("lastname", user.getLastname());

        HttpPost httpPost = httpPostQuery(objectQuery, "deleteModer"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );

        JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        return Boolean.parseBoolean(jsonObject.get("result").toString());
    }

    protected ObservableList<Moder> findDefiniteModers(String searchField) throws IOException, InterruptedException, ParseException {
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("queryString", searchField);

        HttpPost httpPost = httpPostQuery(objectQuery, "findDefiniteModers"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );

        JSONObject result = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        ObservableList<Moder> finalresult = FXCollections.observableArrayList();
        JSONArray result1 = (JSONArray) result.get("Moder");
        for (int i = 0; i < result1.size(); i++){
            JSONObject result3 = (JSONObject) result1.get(i);
            finalresult.add(new Moder(
                    String.valueOf(result3.get("login")),
                    String.valueOf(result3.get("password")),
                    String.valueOf(result3.get("role")),
                    String.valueOf(result3.get("name")),
                    String.valueOf(result3.get("lastname"))));
        }
        return finalresult;
    } // ищем определенного модера для выгрузки
}


