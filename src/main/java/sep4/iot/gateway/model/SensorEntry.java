package sep4.iot.gateway.model;

import java.io.Serializable;
import java.util.Date;

public class SensorEntry implements Serializable {
    private int entry_key;
    private int user_key;
    private String hweui;
    private Date entry_time;

    private float air_temperature;
    private float air_humidity;
    private int air_co2;
    private float light_level;

    private float desired_air_temperature;
    private float desired_air_humidity;
    private int desired_air_co2;
    private float desired_light_level;

    public SensorEntry() {
    }

    public SensorEntry(int entry_key, String hweui, int user_key, Date entry_time, float air_temperature, float air_humidity, int air_co2, float light_level, float desired_air_temperature, float desired_air_humidity, int desired_air_co2, float desired_light_level) {
        this.entry_key = entry_key;
        this.user_key = user_key;
        this.hweui = hweui;
        this.entry_time = entry_time;
        this.air_temperature = air_temperature;
        this.air_humidity = air_humidity;
        this.air_co2 = air_co2;
        this.light_level = light_level;
        this.desired_air_temperature = desired_air_temperature;
        this.desired_air_humidity = desired_air_humidity;
        this.desired_air_co2 = desired_air_co2;
        this.desired_light_level = desired_light_level;
    }

    public SensorEntry(int entry_key, int user_key, String hweui, Date entry_time, float air_temperature, float air_humidity, int air_co2, float light_level) {
        this.entry_key = entry_key;
        this.user_key = user_key;
        this.hweui = hweui;
        this.entry_time = entry_time;
        this.air_temperature = air_temperature;
        this.air_humidity = air_humidity;
        this.air_co2 = air_co2;
        this.light_level = light_level;

        //dummy values for desired fields to indicate received data
        desired_air_co2=-1;
        desired_air_humidity=-1;
        desired_light_level=-1;
        desired_air_temperature=-1;
    }

    public String getHweui() {
        return hweui;
    }

    public void setHweui(String hweui) {
        this.hweui = hweui;
    }

    public float getDesired_air_temperature() {
        return desired_air_temperature;
    }

    public void setDesired_air_temperature(float desired_air_temperature) {
        this.desired_air_temperature = desired_air_temperature;
    }

    public float getDesired_air_humidity() {
        return desired_air_humidity;
    }

    public void setDesired_air_humidity(float desired_air_humidity) {
        this.desired_air_humidity = desired_air_humidity;
    }

    public int getDesired_air_co2() {
        return desired_air_co2;
    }

    public void setDesired_air_co2(int desired_air_co2) {
        this.desired_air_co2 = desired_air_co2;
    }

    public float getDesired_light_level() {
        return desired_light_level;
    }

    public void setDesired_light_level(float desired_light_level) {
        this.desired_light_level = desired_light_level;
    }

    public int getEntry_key() {
        return entry_key;
    }

    public void setEntry_key(int entry_key) {
        this.entry_key = entry_key;
    }

    public int getUser_key() {
        return user_key;
    }

    public void setUser_key(int user_key) {
        this.user_key = user_key;
    }

    public Date getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(Date entry_time) {
        this.entry_time = entry_time;
    }

    public float getAir_temperature() {
        return air_temperature;
    }

    public void setAir_temperature(float air_temperature) {
        this.air_temperature = air_temperature;
    }

    public float getAir_humidity() {
        return air_humidity;
    }

    public void setAir_humidity(float air_humidity) {
        this.air_humidity = air_humidity;
    }

    public int getAir_co2() {
        return air_co2;
    }

    public void setAir_co2(int air_co2) {
        this.air_co2 = air_co2;
    }

    public float getLight_level() {
        return light_level;
    }

    public void setLight_level(float light_level) {
        this.light_level = light_level;
    }
}
