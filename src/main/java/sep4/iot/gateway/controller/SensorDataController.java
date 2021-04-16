package sep4.iot.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;
import sep4.iot.gateway.service.SensorDataService;

@RestController
@RequestMapping("/SensorData")
public class SensorDataController {

    @Autowired
    SensorDataService service;

    //CRUD-Retrieve
    public SensorEntry getSensorEntry(@RequestBody final HardwareUser user){
        try {
            SensorEntry entry = service.getSensorEntry(user);
            return entry;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    //CRUD-Create
    @PostMapping
    public void sendDataToSensor(@RequestBody final SensorEntry sensorEntry){
        try {
            service.sendDataToSensor(sensorEntry);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //CRUD-Create
    public void createNewUserThread(@RequestBody final HardwareUser user){
        try {
            service.createNewUserThread(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
