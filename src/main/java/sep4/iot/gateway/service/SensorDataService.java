package sep4.iot.gateway.service;

import org.springframework.stereotype.Service;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;

@Service
public class SensorDataService implements ISensorDataService{

    public SensorDataService() {
    }

    @Override
    public SensorEntry getSensorEntry(HardwareUser user) {
        return null;
    }

    @Override
    public void sendDataToSensor(SensorEntry sensorEntry) {
    }
}
