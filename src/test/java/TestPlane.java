import com.example.airport.objects.Admin;
import com.example.airport.objects.Autorit;
import com.example.airport.objects.Moder;
import com.example.airport.objects.Plane;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class TestPlane {
    static CloseableHttpClient httpclient = HttpClients.createDefault();
    protected static JSONParser parser = new JSONParser();
    protected static String socket = "https://9759-94-140-137-201.ngrok-free.app/";
    @Test
    public void TestPlane(){
        Plane plane = new Plane(1, "de", "dsf", 14);
        boolean result = true;
        int login = 10000000 + (int) (Math.random() * 89999999);
        //  Assertions.assertTrue(result = findLogin(login));
    }
    @Test
    public void testTest() throws IOException, ParseException {
        // создаем клиента
        JSONParser parser = new JSONParser();
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // объект get
//        HttpGet httpget = new HttpGet("https://9759-94-140-137-201.ngrok-free.app/findPlanes");
////        System.out.println("Request Type: " + httpget.getMethod());
//        HttpResponse httpresponse = httpclient.execute(httpget);
////        System.out.println(((CloseableHttpResponse) httpresponse).getEntity().getContent().toString());
////        JSONObject result = (JSONObject) parser.parse(httpresponse.getReasonPhrase())

//        Scanner sc = new Scanner(((CloseableHttpResponse) httpresponse).getEntity().getContent());
//        while(sc.hasNext()) {
//            System.out.println(sc.nextLine());
//        }
        Moder moder = new Moder("12334452","123344557", "moder", "aaa", "aaa");
        JSONObject query = new JSONObject();
        query.put("login", moder.getLogin());
        query.put("password", moder.getPassword());
        query.put("role", moder.getRole());
        query.put("name", moder.getName());
        query.put("lastname", moder.getLastname());

        JSONObject query1 = new JSONObject();
        query1.put("Moder", query);
        StringEntity entity = new StringEntity(query1.toJSONString());
        HttpPost httpPost = new HttpPost("https://9759-94-140-137-201.ngrok-free.app/" + "saveModer");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
//        System.out.println("Request Type: " + httpget.getMethod());
        HttpResponse httpresponse = httpclient.execute(httpPost);
//        System.out.println(((CloseableHttpResponse) httpresponse).getEntity().getContent().toString());
//        JSONObject result = (JSONObject) parser.parse(httpresponse.getReasonPhrase())

        Scanner sc = new Scanner(((CloseableHttpResponse) httpresponse).getEntity().getContent());
        while(sc.hasNext()) {
            System.out.println(sc.nextLine());
        }
    }
    @Test
    public void testAutor() throws IOException, ParseException {
        Autorit autorit = new Autorit("12345678", "12345678");
        JSONObject object = autorit.toJSONObject();
        System.out.println(object);
        StringEntity entity = new StringEntity(object.toJSONString());
        HttpPost httpPost = new HttpPost(socket + "findUser");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse httpresponse = httpclient.execute(httpPost);
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(socket + "findUser")).POST(HttpRequest.BodyPublishers.ofString(object.toJSONString())).build();
//        HttpResponse<String> response = null;
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException e){
//            return null;
//        }
//        StringBuilder res = new StringBuilder();
//        Scanner sc = new Scanner(((CloseableHttpResponse) httpresponse).getEntity().getContent());
//        System.out.println(sc);
//        while(sc.hasNext()) {
//            res.append(sc.nextLine());
//        }
        InputStream input = httpresponse.getEntity().getContent();
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );

        JSONObject query;
        try {
            query = (JSONObject) parser.parse(String.valueOf(stringBuilder));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(query);
        JSONObject result = (JSONObject) parser.parse(String.valueOf(query));
    }

}
