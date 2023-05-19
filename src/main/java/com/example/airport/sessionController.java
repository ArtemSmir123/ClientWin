package com.example.airport;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.airport.sqlcode.*;

public class sessionController extends Thread{

    public void run() {
        while (MainApp.uuid != null){
            JSONObject objectQuery = new JSONObject();
            objectQuery.put("uuid", MainApp.uuid.toString());
//            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "updateSession")).POST(HttpRequest.BodyPublishers.ofString(object.toJSONString())).build();
//            HttpResponse<String> response = null;

            HttpPost httpPost = httpPostQuery(objectQuery, "updateSession"); // Сконфигурировали запрос
//            System.out.println(objectQuery);
            CloseableHttpResponse httpresponse;
            InputStream input = null;


            JSONObject object1;
            try {
                httpresponse = httpclient.execute(httpPost); // Отправили
                input = httpresponse.getEntity().getContent(); // Получили ответ
                StringBuilder stringBuilder = new StringBuilder();
                new BufferedReader(new InputStreamReader(input))
                        .lines()
                        .forEach( (String s) -> stringBuilder.append(s + "\n") );
//                System.out.println(stringBuilder);
                try {
                    object1 = (JSONObject) parser.parse(String.valueOf(stringBuilder));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (!Boolean.parseBoolean(object1.get("result").toString())){
                    MainApp.uuid = null;
                }
                Thread.sleep(60000);
            } catch (IOException | InterruptedException ignored) {
                System.out.println("SDASDSLDKLSKD");
            }
        }
        System.exit(0);
    }
}
