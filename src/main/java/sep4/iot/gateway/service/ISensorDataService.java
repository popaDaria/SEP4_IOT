package sep4.iot.gateway.service;

import org.springframework.stereotype.Service;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;

import java.util.ArrayList;

/**
 * Sensor service interface
 *
 * @author Mihai Anghelus
 * @author Daria Popa
 * @version 1.0
 * @since 26-05-2021
 */
public interface ISensorDataService {

    ArrayList<SensorEntry> getSensorEntry(HardwareUser user);
    void sendDataToSensor(SensorEntry sensorEntry);
    void createNewUserThread(int user_key);
    void destroyUserThread(int user_key);
}
