package sep4.iot.gateway.webSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import sep4.iot.gateway.model.SensorEntry;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class WebSocketThread implements Runnable{

    private WebSocketClient webSocketClient;

    private int user_key;
    //private static final HttpClient httpClient= HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(10)).build();;
    private ArrayList<SensorEntry> sensorEntries;


    public WebSocketThread(String url, int user_key) {
        webSocketClient = new WebSocketClient(url);
        this.user_key=user_key;
        sensorEntries = new ArrayList<>();
    }

    public synchronized int getUser_key() {
        return user_key;
    }

    public synchronized ArrayList<SensorEntry> getSensorEntries() {
        return sensorEntries;
    }

    public synchronized void clearSensorEntries() {
        sensorEntries.clear();
    }

    public synchronized void sendSensorData(SensorEntry sensorEntry){
        //TODO: transform sensor entry to jsonTelegram and post it
        //webSocketClient.sendDownLink("");
    }

    @Override
    public void run() {

        while (true){
            System.out.println("THREAD IS RUNNING FOR USER "+user_key);
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            String upLinkMessage = webSocketClient.getUpLink();
            if(upLinkMessage!=null){
                System.out.println("SENSOR ENTRY DATA FROM THREAD: "+upLinkMessage);
                /*
                eg:{
                    "rssi": -116,
                    "seqno": 39,
                    "data": "000d0018026bda02",
                    "toa": 0,
                    "freq": 867300000,
                    "ack": false,
                    "fcnt": 0,
                    "dr": "SF12 BW125 4\/5",
                    "offline": false,
                    "bat": 255,
                    "port": 1,
                    "snr": -13,
                    "EUI": "0004A30B002528D3",
                    "cmd": "rx",
                    "ts": 1619347683413
                }
                */

                String[] lines = upLinkMessage.split(",");
                String dataLine="", tsLine="", euiLine="";
                for (String str:lines) {
                    if(str.contains("data")){
                        dataLine=str;
                    }else if(str.contains("ts")){
                        tsLine=str;
                    }else if(str.contains("EUI")){
                        euiLine=str;
                    }
                }
                System.out.println("data line: "+dataLine);
                System.out.println("ts: "+tsLine);
                System.out.println("EUI: "+euiLine);

                SensorEntry sensorEntry = new SensorEntry();
                sensorEntry.setUser_key(user_key);

                dataLine = dataLine.split("\"")[3];
                char[] data = dataLine.toCharArray();

                euiLine = euiLine.split("\"")[3];
                sensorEntry.setHweui(euiLine);

                tsLine = tsLine.split(":")[1].trim();
                tsLine = tsLine.split(" ")[0].trim();

                sensorEntry.setEntry_time(Long.parseLong(tsLine));

                int hum=Integer.parseInt(data[0]+data[1]+"");
                int temp=Integer.parseInt(data[2]+data[3]+"");
                sensorEntry.setAir_co2(Integer.parseInt(data[4]+data[5]+""));
                int light=Integer.parseInt(data[6]+data[7]+"");

                sensorEntry.setAir_humidity((float)hum/10);
                sensorEntry.setAir_temperature((float)temp/10);
                sensorEntry.setLight_level(light);

                System.out.println("RECEIVED SENSOR ENTRY: "+sensorEntry.toString());
                sensorEntries.add(sensorEntry);
            }
        }

    }

}


