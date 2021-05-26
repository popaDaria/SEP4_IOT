package sep4.iot.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "41003"));
        app.run(args);
    }

    //SensorEntry sensorEntry = new SensorEntry(1,"sdsfd",2,1343567654,23,23,23,23,24,26,123,320);
}
