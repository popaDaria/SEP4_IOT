package sep4.iot.gateway.service;

import org.springframework.stereotype.Service;
import sep4.iot.gateway.model.HardwareUser;
import sep4.iot.gateway.model.SensorEntry;
import sep4.iot.gateway.persistance.UserThreadFile;
import sep4.iot.gateway.webSocket.WebSocketThread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Service
public class SensorDataService implements ISensorDataService{

    //private WebSocketThread webSocketThread;
    public static ExecutorService executorService ;
    private final UserThreadFile persistence;
    private ArrayList<WebSocketThread> threads = new ArrayList<>();

    public SensorDataService() {
        if(executorService==null)
            executorService=new ScheduledThreadPoolExecutor(10); //base size for now
        persistence = new UserThreadFile();
        initialiseThreads();
        threads= new ArrayList<>();
    }

    private void initialiseThreads(){
        for (HardwareUser user: persistence.getAllThreads()) {
            String url = "wss://iotnet.teracom.dk/app?token=vnoTvgAAABFpb3RuZXQuY2liaWNvbS5ka4OBbRiJLnlvbW8x7gEMUs0=";
            WebSocketThread webSocketThread = new WebSocketThread(url, user.getUser_key());
            threads.add(webSocketThread);
            //System.out.println(threads.size()+" HOW MANY");
            executorService.submit(webSocketThread);
        }
    }

    @Override
    public ArrayList<SensorEntry> getSensorEntry(HardwareUser user) {
        ArrayList<SensorEntry> info = new ArrayList<>();
        for (WebSocketThread hd: threads) {
            if(hd.getUser_key()==user.getUser_key()){
                info = hd.getSensorEntries();
                hd.clearSensorEntries();
            }
        }
        return info;
    }

    @Override
    public void sendDataToSensor(SensorEntry sensorEntry) {

        for (HardwareUser user: persistence.getAllThreads()) {
            String url = "wss://iotnet.teracom.dk/app?token=vnoTvgAAABFpb3RuZXQuY2liaWNvbS5ka4OBbRiJLnlvbW8x7gEMUs0=";
            WebSocketThread webSocketThread = new WebSocketThread(url, user.getUser_key());
            threads.add(webSocketThread);
        }

        System.out.println(threads.size()+" how many now");
        for (WebSocketThread hd: threads) {
            System.out.println(hd.getUser_key()+" thread");
            if(hd.getUser_key()==sensorEntry.getUser_key()){
                hd.sendSensorData(sensorEntry);
                System.out.println("IN SERVICE "+hd.getUser_key());
            }
        }
        System.out.println("In method for service");
    }

    @Override
    public void createNewUserThread(int user_key) {
        //hardcoded url for now
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

    /*@Override
    public void createNewUserThread(HardwareUser user) {
        String url = "wss://iotnet.teracom.dk/app?token="+user.getAppToken()+"=";
        webSocketThread=new WebSocketThread(url,user.getUser_key());
        executorService.submit(webSocketThread);
    }*/
}
