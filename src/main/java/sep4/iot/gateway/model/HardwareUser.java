package sep4.iot.gateway.model;

import java.io.Serializable;

public class HardwareUser implements Serializable {
    private int user_key;
    private String appToken;

    public HardwareUser(){

    }
    public HardwareUser(int user_key, String appToken) {
        this.user_key = user_key;
        this.appToken = appToken;
    }

    public int getUser_key() {
        return user_key;
    }

    public void setUser_key(int user_key) {
        this.user_key = user_key;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}
