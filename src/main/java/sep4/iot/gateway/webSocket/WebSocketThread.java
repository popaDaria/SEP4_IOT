package sep4.iot.gateway.webSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import sep4.iot.gateway.model.SensorEntry;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class WebSocketThread implements Runnable{

    private WebSocketClient webSocketClient;
    private int user_key;
    private static final HttpClient httpClient= HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(10)).build();;

    public WebSocketThread(String url, int user_key) {
        webSocketClient = new WebSocketClient(url);
        this.user_key=user_key;
    }

    @Override
    public void run() {

        while (true){
            System.out.println("THREAD IS RUNNING FOR USER "+user_key);
            try {
                Thread.sleep(8100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            String upLinkMessage = webSocketClient.getUpLink();
            if(upLinkMessage!=null){

                //TODO: read info from queue and post it to the application server

                System.out.println("SENSOR ENTRY DATA: "+upLinkMessage);
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
                String dataLine="", tsLine="";
                for (String str:lines) {
                    if(str.contains("data")){
                        dataLine=str;
                    }else if(str.contains("ts")){
                        tsLine=str;
                    }
                }
                dataLine = dataLine.split("\"")[2];
                char[] data = dataLine.toCharArray();

                tsLine = tsLine.split(":")[1].trim();
                //Date date = new Date(Integer.parseInt(tsLine));

                SensorEntry sensorEntry = new SensorEntry();
                sensorEntry.setUser_key(user_key);
                sensorEntry.setEntry_time(Integer.parseInt(tsLine));

                int hum=Integer.parseInt(data[0]+data[1]+"");
                int temp=Integer.parseInt(data[2]+data[3]+"");
                sensorEntry.setAir_co2(Integer.parseInt(data[4]+data[5]+""));
                int light=Integer.parseInt(data[6]+data[7]+"");

                sensorEntry.setAir_humidity(hum);
                sensorEntry.setAir_temperature(temp);
                sensorEntry.setLight_level(light);

                System.out.println("RECEIVED SENSOR ENTRY: "+sensorEntry.toString());

                ObjectWriter objectWriter = new ObjectMapper().writer();
                String json = null;
                try {
                    json = new JSONObject(objectWriter.writeValueAsString(sensorEntry)).toString();
                } catch (JsonProcessingException | JSONException e) {
                    e.printStackTrace();
                }

                //TODO: set uri to app server one

                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .uri(URI.create("")).setHeader("User-Agent","Sensor entry")
                        .header("Content-Type","application/json").build();

                System.out.println("HTTP Req: "+request.toString());

                try {
                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.statusCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                System.out.println(user_key+ " waiting for info...");
            }
        }

    }
}
