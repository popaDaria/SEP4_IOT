package sep4.iot.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;
import sep4.iot.gateway.service.SensorDataService;

import java.util.ArrayList;

@RestController
@RequestMapping("/SensorData")
public class SensorDataController {

    @Autowired
    SensorDataService service;

    //CRUD-Retrieve
    @GetMapping
    public ArrayList<SensorEntry> getSensorEntry(@RequestBody final HardwareUser user){
        try {
            ArrayList<SensorEntry> entry = service.getSensorEntry(user);
            return entry;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    //CRUD-Create
    @RequestMapping("/updateData")
    @PostMapping
    public void sendDataToSensor(@RequestBody final SensorEntry sensorEntry){
        try {
            service.sendDataToSensor(sensorEntry);
            //System.out.println("SAW POST:"+sensorEntry.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //CRUD-Create
    @PostMapping("/{user_key}")
    @ResponseBody
    public void createNewUserThread(@PathVariable("user_key") final String user_key){
        try {
            int id = Integer.parseInt(user_key);
            service.createNewUserThread(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

/*    @PostMapping
    public void createNewUserThread(@RequestBody final HardwareUser user){
        try {
            service.createNewUserThread(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }*/

}
