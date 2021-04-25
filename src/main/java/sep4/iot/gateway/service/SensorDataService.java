package sep4.iot.gateway.service;

import org.springframework.stereotype.Service;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;
import sep4.iot.gateway.persistance.UserThreadFile;
import sep4.iot.gateway.webSocket.WebSocketThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Service
public class SensorDataService implements ISensorDataService{

    private WebSocketThread webSocketThread;
    public static ExecutorService executorService ;
    private UserThreadFile persistance;

    public SensorDataService() {
        if(executorService==null)
            executorService=new ScheduledThreadPoolExecutor(10); //base size for now
        persistance = new UserThreadFile();
        initialiseThreads();
    }

    private void initialiseThreads(){
        for (HardwareUser user: persistance.getAllThreads()) {
            String url = "wss://iotnet.teracom.dk/app?token=vnoTvgAAABFpb3RuZXQuY2liaWNvbS5ka4OBbRiJLnlvbW8x7gEMUs0=";
            webSocketThread = new WebSocketThread(url, user.getUser_key());
            executorService.submit(webSocketThread);
        }
    }

    @Override
    public SensorEntry getSensorEntry(HardwareUser user) {
        return null;
    }

    @Override
    public void sendDataToSensor(SensorEntry sensorEntry) {
    }

    @Override
    public void createNewUserThread(int user_key) {
        //hardcoded url for now
        if(!persistance.isUserThreadStarted(user_key)) {
            String url = "wss://iotnet.teracom.dk/app?token=vnoTvgAAABFpb3RuZXQuY2liaWNvbS5ka4OBbRiJLnlvbW8x7gEMUs0=";
            HardwareUser user = new HardwareUser(user_key, "vnoTvgAAABFpb3RuZXQuY2liaWNvbS5ka4OBbRiJLnlvbW8x7gEMUs0");
            persistance.addThread(user);
            webSocketThread = new WebSocketThread(url, user_key);
            executorService.submit(webSocketThread);
        }else{
            System.out.println("THREAD ALREADY STARTED FOR USER "+user_key+"!!!");
        }
    }

    /*@Override
    public void createNewUserThread(HardwareUser user) {
        String url = "wss://iotnet.teracom.dk/app?token="+user.getAppToken()+"=";
        webSocketThread=new WebSocketThread(url,user.getUser_key());
        executorService.submit(webSocketThread);
    }*/
}
