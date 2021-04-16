package sep4.iot.gateway.service;

import org.springframework.stereotype.Service;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;
import sep4.iot.gateway.webSocket.WebSocketThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Service
public class SensorDataService implements ISensorDataService{

    private WebSocketThread webSocketThread;
    public static ExecutorService executorService ;

    public SensorDataService() {
        if(executorService==null)
            executorService=new ScheduledThreadPoolExecutor(10); //base size for now
    }

    @Override
    public SensorEntry getSensorEntry(HardwareUser user) {
        return null;
    }

    @Override
    public void sendDataToSensor(SensorEntry sensorEntry) {
    }

    @Override
    public void createNewUserThread(HardwareUser user) {
        String url = "wss://iotnet.teracom.dk/app?token="+user.getAppToken()+"=";
        webSocketThread=new WebSocketThread(url,user.getUser_key());
        executorService.submit(webSocketThread);
    }
}
