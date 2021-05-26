package sep4.iot.gateway.model;

import java.io.Serializable;

public class HardwareUser implements Serializable {
    private int user_key;
    private String user_token;

    public HardwareUser(){

    }
    public HardwareUser(int user_key, String user_token) {
        this.user_key = user_key;
        this.user_token = user_token;
    }

    public int getUser_key() {
        return user_key;
    }

    public void setUser_key(int user_key) {
        this.user_key = user_key;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HardwareUser))
            return false;
        return ((HardwareUser) object).user_key == user_key;
    }
}
