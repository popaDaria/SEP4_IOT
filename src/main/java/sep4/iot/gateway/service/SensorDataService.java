package sep4.iot.gateway.service;

import org.springframework.stereotype.Service;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;
import sep4.iot.gateway.persistance.UserThreadFile;
import sep4.iot.gateway.webSocket.WebSocketThread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Implementation of the Sensor service interface
 *
 * @author Daria Popa
 * @author Natali Munk-Jakobsen
 * @version 1.0
 * @since 26-05-2021
 */
@Service
public class SensorDataService implements ISensorDataService{

    public static ExecutorService executorService ;
    private final UserThreadFile persistence;
    private static ArrayList<WebSocketThread> threads = new ArrayList<>();

    /**
     * Constructor used to initialise the persistence and listening threads
     */
    public SensorDataService() {
        if(executorService==null)
            executorService=new ScheduledThreadPoolExecutor(10); //base size for now
        persistence = new UserThreadFile();
        initialiseThreads();
    }

    /**
     * Method used to start the WebSocketThreads for the users saved in the local persistence
     *
     * @version hardcoded URL
     */
    private void initialiseThreads(){
        for (HardwareUser user: persistence.getAllThreads()) {
            String url = "wss://iotnet.teracom.dk/app?token=vnoTvgAAABFpb3RuZXQuY2liaWNvbS5ka4OBbRiJLnlvbW8x7gEMUs0=";
            WebSocketThread webSocketThread = new WebSocketThread(url, user.getUser_key());
            threads.add(webSocketThread);
            executorService.submit(webSocketThread);
        }
    }

    /**
     * Getter for the sensor data from a user's threads
     * @param user - the user for which the data is requested
     * @return an ArrayList with the SensorEntry elements
     */
    @Override
    public ArrayList<SensorEntry> getSensorEntry(HardwareUser user) {
        ArrayList<SensorEntry> info = new ArrayList<>();
        for (WebSocketThread hd: threads) {
            if(hd.getUser_key()==user.getUser_key()){
                info.addAll(hd.getSensorEntries());
                hd.clearSensorEntries();
                return info;
            }
        }
        return info;
    }

    /**
     * Method for sending data to the hardware
     * @param sensorEntry - object containing the information needed to construct the downlink
     */
    @Override
    public void sendDataToSensor(SensorEntry sensorEntry) {
        for (WebSocketThread hd: threads) {
            if(hd.getUser_key()==sensorEntry.getUser_key()){
                hd.sendSensorData(sensorEntry);
                break;
            }
        }
    }

    /**
     * Method for creating and starting a new user's listening thread
     * @param user_key - the unique key of the new user
     * @version hardcoded URL
     */
    @Override
    public void createNewUserThread(int user_key) {
        if(!persistence.isUserThreadStarted(user_key)) {
            String url = "wss://iotnet.teracom.dk/app?token=vnoTvgAAABFpb3RuZXQuY2liaWNvbS5ka4OBbRiJLnlvbW8x7gEMUs0=";
            HardwareUser user = new HardwareUser(user_key, "vnoTvgAAABFpb3RuZXQuY2liaWNvbS5ka4OBbRiJLnlvbW8x7gEMUs0");
            persistence.addThread(user);
            WebSocketThread webSocketThread = new WebSocketThread(url, user_key);
            threads.add(webSocketThread);
            executorService.submit(webSocketThread);
        }else{
            System.out.println("THREAD ALREADY STARTED FOR USER "+user_key+"!!!");
        }
    }

    /**
     * Method used to remove a user's thread
     * @param user_key - the unique key of the user
     */
    @Override
    public void destroyUserThread(int user_key) {
        for (WebSocketThread thread : threads)
            if (thread.getUser_key() == user_key)
            {
                thread.stop();
                threads.remove(thread);
                persistence.removeThread(new HardwareUser(user_key, ""));
                break;
            }
    }

}
