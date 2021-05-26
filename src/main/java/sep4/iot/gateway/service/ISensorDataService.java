package sep4.iot.gateway.service;

import org.springframework.stereotype.Service;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;

import java.util.ArrayList;

@Service
public interface ISensorDataService {

    ArrayList<SensorEntry> getSensorEntry(HardwareUser user);
    //ArrayList<SensorEntry> getSensorEntry(int user_key);
    void sendDataToSensor(SensorEntry sensorEntry);
    //void createNewUserThread(HardwareUser user);
    void createNewUserThread(int user_key);
    void destroyUserThread(int user_key);
}
