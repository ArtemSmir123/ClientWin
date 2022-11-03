package objects;

import com.example.airport.objects.Plane;
import org.junit.jupiter.api.Test;


public class TestPlane {
    @Test
    public void TestPlane(){
        Plane plane = new Plane(1, "de", "dsf", 14);
        boolean result = true;
        int login = 10000000 + (int) (Math.random() * 89999999);
        //  Assertions.assertTrue(result = findLogin(login));
    }

}
