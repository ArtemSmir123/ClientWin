package com.example.airport;

import com.example.airport.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.UUID;
import static com.example.airport.MainApp.uuid;
import static org.apache.http.params.CoreProtocolPNames.HTTP_CONTENT_CHARSET;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

public class sqlcode {
    protected static CloseableHttpClient httpclient = HttpClients.createDefault();

    protected static JSONParser parser = new JSONParser();
    protected static String socket = "https://9759-94-140-137-201.ngrok-free.app/";
    protected static HttpPost httpPostQuery( JSONObject object, String param1){
        HttpPost httpPost = new HttpPost(socket + param1);
        StringEntity entity = new StringEntity(object.toJSONString(), Charset.defaultCharset());
//        System.out.println(entity);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "text/plain");
        httpPost.setHeader("Content-type", "text/plain; charset=UTF-8");
//        httpPost.setHeader(HTTP_CONTENT_CHARSET, "UTF-8");
        return httpPost;
    }
    protected static HttpGet httpGetQuery(String param1){
        HttpGet httpGet = new HttpGet(socket + param1);
//        StringEntity entity = new StringEntity(object.toJSONString());
//        httpPost.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
        return httpGet;
    }
    protected static Users findUser(String login, String pass) throws IOException, ParseException {
        Autorit autorit = new Autorit(login, pass); // Сделали объект
        JSONObject object = autorit.toJSONObject(); // Перевели в JSON
        HttpPost httpPost = httpPostQuery(object, "findUser"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );
        JSONObject res1;
        try {
            res1 = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject result = (JSONObject) parser.parse(String.valueOf(res1));
//        System.out.println(result);
        if (!Boolean.parseBoolean(String.valueOf(result.get("result")))){
            return null;
        } else {
            JSONObject regist = (JSONObject) result.get("Regist");
            uuid = UUID.fromString(String.valueOf(regist.get("uuid")));
            sessionController sessionController = new sessionController();
            sessionController.start();

            if (result.containsKey("Admin")) {
                JSONObject result2 = (JSONObject) result.get("Admin");
                return new Admin((String) result2.get("login"), (String) result2.get("password"), (String) result2.get("role"), (String) result2.get("name"), (String) result2.get("lastname"));
            } else if (result.containsKey("Moder")) {
                JSONObject result2 = (JSONObject) result.get("Moder");
                return new Moder((String) result2.get("login"), (String) result2.get("password"), (String) result2.get("role"), (String) result2.get("name"), (String) result2.get("lastname"));
            }
            return null;
        }
    } // найти пользователя для login

    protected static boolean findLogin(int login) throws IOException, InterruptedException, ParseException {
        JSONObject object = new JSONObject();
        object.put("query", login);
        object.put("uuid", uuid.toString());

        HttpPost httpPost = httpPostQuery(object, "findLogin"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );
        JSONObject res1;
        try {
            res1 = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (boolean) res1.get("result");

    }
    protected ObservableList<Plane> findPlanes() throws IOException, InterruptedException, ParseException {
        HttpGet httpget = httpGetQuery("findPlanes");
        CloseableHttpResponse httpresponse = httpclient.execute(httpget);

        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );

        JSONObject result = (JSONObject) parser.parse(String.valueOf(stringBuilder));
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
