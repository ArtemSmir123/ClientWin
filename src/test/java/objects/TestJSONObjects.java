package objects;

import com.example.airport.objects.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestJSONObjects {
    @Test
    public void testJSONAdmin(){
        Admin admin = new Admin("12345678", "12345678","12345678","12345678","12345678");
        JSONObject result = admin.toJSONObject();
        System.out.println(result.toJSONString());

        Admin admin1 = (Admin) Admin.fromJSONObject(result);
        System.out.println(admin1);
    }

    @Test
    public void testJSONModer(){
        Moder moder = new Moder("12345678", "12345678","12345678","12345678","12345678");
        JSONObject result = moder.toJSONObject();
        System.out.println(result.toJSONString());

        Moder moder1 = (Moder) Moder.fromJSONObject(result);
        System.out.println(moder1);
    }
    @Test
    public void testJSONAutorit(){
        Autorit autorit = new Autorit("12345678", "12345678");
        JSONObject result = autorit.toJSONObject();
        System.out.println(result.toJSONString());

        Autorit autorit1 = (Autorit) Autorit.fromJSONObject(result);
        System.out.println(autorit1.getLogin() + " " + autorit1.getPass());
    }
    @Test
    public void testJSONFlight(){
        Flight flight = new Flight(12, "123", new Date(), new Date(), new Date(), "das", "sad", 12);
        JSONObject result = flight.toJSONObject();
        System.out.println(result.toJSONString());

        Flight flight1 = (Flight) Flight.fromJSONObject(result);
        System.out.println(flight1);
    }
    @Test
    public void testJSONPlane(){
        Plane plane = new Plane(12, "dfs", "adf", 143);
        JSONObject result = plane.toJSONObject();
        System.out.println(result.toJSONString());

        Plane plane1 = (Plane) Plane.fromJSONObject(result);
        System.out.println(plane1);
    }
}
