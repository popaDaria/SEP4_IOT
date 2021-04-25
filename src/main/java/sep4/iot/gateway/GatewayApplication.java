package sep4.iot.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Date;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);

        /*
        SensorEntry sensorEntry = new SensorEntry(1,3,"jjgfjgf",
                123,12,25,13, 245);
        ObjectWriter objectWriter = new ObjectMapper().writer();
        String json = null;
        try {
            json = new JSONObject(objectWriter.writeValueAsString(sensorEntry)).toString();
        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        */


    }

}
