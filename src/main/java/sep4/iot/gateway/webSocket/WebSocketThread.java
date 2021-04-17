package sep4.iot.gateway.webSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONException;
import org.json.JSONObject;
import sep4.iot.gateway.model.SensorEntry;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class WebSocketThread implements Runnable{

    private WebSocketClient webSocketClient;
    private int user_key;
    private static final HttpClient httpClient= HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10)).build();;

    public WebSocketThread(String url, int user_key) {
        webSocketClient = new WebSocketClient(url);
        this.user_key=user_key;
    }

    @Override
    public void run() {

        while (true){

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            String upLinkMessage = webSocketClient.getUpLink();
            if(upLinkMessage!=null){

                //TODO: read info from queue and post it to the server application server

                SensorEntry sensorEntry = new SensorEntry();
                sensorEntry.setUser_key(user_key);

                ObjectWriter objectWriter = new ObjectMapper().writer();
                String json = null;
                try {
                    json = new JSONObject(objectWriter.writeValueAsString(sensorEntry)).toString();
                } catch (JsonProcessingException | JSONException e) {
                    e.printStackTrace();
                }

                //TODO: set uri

                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .uri(URI.create("")).setHeader("User-Agent","Sensor entry")
                        .header("Content-Type","application/json").build();

                try {
                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.statusCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
