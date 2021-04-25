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

                SensorEntry sensorEntry = new SensorEntry();
                sensorEntry.setUser_key(user_key);
                sensorEntry.setEntry_time(new Date());

                //System.out.println("RECEIVED SENSOR ENTRY: "+sensorEntry.toString());

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

               /* try {
                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.statusCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                */
            }else{
                System.out.println("Null value");
            }
        }

    }
}
