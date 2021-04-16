package sep4.iot.gateway.service;

import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;

public interface ISensorDataService {

    SensorEntry getSensorEntry(HardwareUser user);
    void sendDataToSensor(SensorEntry sensorEntry);
}
