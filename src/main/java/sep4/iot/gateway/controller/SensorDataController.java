package sep4.iot.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;
import sep4.iot.gateway.service.SensorDataService;

import java.util.ArrayList;

/**
 * The Sensor Controller handles the requests coming from the Data server,
 * and sends them forward to the service to be processed.
 *
 * @author Daria Popa
 * @author Mihai Anghelus
 * @version 1.0
 * @since 26-05-2021
 */

@RestController
@RequestMapping("/SensorData")
public class SensorDataController {

    /**
     * @param service - The Sensor Service is instantiated in the controller through the use of dependency injection
     */
    @Autowired
    SensorDataService service;

    /**
     * HTTP GET controller method
     * @param user - the user for which the sensor data is required
     * @return ArrayList of SensorEntry elements or an empty list on exception caught
     */
    //CRUD-Retrieve
    @GetMapping
    public ArrayList<SensorEntry> getSensorEntry(@RequestBody final HardwareUser user){
        try {
            ArrayList<SensorEntry> entry = service.getSensorEntry(user);
            return entry;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * HTTP POST controller method for sending downlink messages
     * @param sensorEntry - object containing the information needed to send a downlink
     */
    //CRUD-Create
    @RequestMapping("/updateData")
    @PostMapping
    public void sendDataToSensor(@RequestBody final SensorEntry sensorEntry){
        try {
            service.sendDataToSensor(sensorEntry);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * HTTP POST controller method for starting new listening threads
     * @param user_key - the unique user key needed to create a new listening user thread
     * @version hardcoded user token/URL
     */
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

}
