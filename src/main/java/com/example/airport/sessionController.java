package com.example.airport;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.example.airport.sqlcode.*;

public class sessionController extends Thread{

    public void run() {
        while (MainApp.uuid != null){
            JSONObject object = new JSONObject();
            object.put("uuid", MainApp.uuid.toString());
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "updateSession")).POST(HttpRequest.BodyPublishers.ofString(object.toJSONString())).build();
            HttpResponse<String> response = null;
            JSONObject object1;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                try {
                    object1 = (JSONObject) parser.parse(response.body());
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
