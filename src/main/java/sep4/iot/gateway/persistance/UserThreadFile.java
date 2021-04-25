package sep4.iot.gateway.persistance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import sep4.iot.gateway.model.HardwareUser;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserThreadFile {

    private ArrayList<HardwareUser> threads;
    private final String filename = "C:\\FOURTH_SEMESTER\\SEP4\\CODE\\Gateway\\src\\userKeys.txt";
    private File file;
    private ObjectMapper mapper;

    public UserThreadFile(){
        threads = new ArrayList<>();
        threads=readThreadsList();
    }

    public void addThread(HardwareUser user){
        threads.add(user);
        updateThreadList();
    }
    public ArrayList<HardwareUser> getAllThreads(){
        threads=readThreadsList();
        return threads;
    }
    public boolean isUserThreadStarted(int user_key){
        for (HardwareUser user:threads) {
            if(user.getUser_key()==user_key)
                return true;
        }
        return false;
    }

    private void updateThreadList(){
        FileWriter out = null;
        mapper = new ObjectMapper();
        try {
            file = new File(filename);
            out = new FileWriter(file);
            for (HardwareUser user:threads) {
                String string = mapper.writeValueAsString(user);
                out.append(string+"\n");
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private ArrayList<HardwareUser> readThreadsList(){
        ArrayList<HardwareUser> list = new ArrayList<>();
        Scanner in = null;
        try {
            file = new File(filename);
            mapper = new ObjectMapper();
            in = new Scanner(file);
                while (in.hasNext()) {
                    String jsonLine = in.nextLine();
                    HardwareUser user = mapper.readValue(jsonLine, HardwareUser.class);
                    list.add(user);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            in.close();
        }
        return list;
    }
}
