package com.example.airport.MenuModerator;

import com.example.airport.objects.Flight;
import com.example.airport.objects.Plane;
import com.example.airport.sqlcode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class sqlcodeModerator extends sqlcode {
    @Override
    protected ObservableList<Plane> findPlanes() throws IOException, ParseException, InterruptedException {
        return super.findPlanes();
    }

    protected boolean saveFlight(Flight flight) throws java.text.ParseException, IOException, InterruptedException, ParseException {
        JSONObject objectQuery = new JSONObject();
        objectQuery.put("id_flight", "null");
        objectQuery.put("id_user", flight.getId_user());
        objectQuery.put("creation_date", flight.getCreation_date().toString());
        objectQuery.put("departure_date", flight.getDeparture_date().toString());
        objectQuery.put("arrival_date", flight.getArrival_date().toString());
        objectQuery.put("departure_city", flight.getDeparture_city());
        objectQuery.put("arrival_city", flight.getArrival_city());
        objectQuery.put("id_plane", String.valueOf(flight.getId_plane()));
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//        cal.setTime(sdf.parse("Sun Jan 29 19:15:14 YEKT 2023"));// all done
        HttpPost httpPost = httpPostQuery(objectQuery, "saveFlight"); // Сконфигурировали запрос
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost); // Отправили
        InputStream input = httpresponse.getEntity().getContent(); // Получили ответ
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );
        JSONObject result = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        return (Boolean)(result.get("result"));
    }

}
