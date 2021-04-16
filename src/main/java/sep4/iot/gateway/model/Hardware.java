package sep4.iot.gateway.model;

//TODO: this class might not be needed
public class Hardware {
    private int hardware_key;
    private String hardware_id;
    private int specimen_key;
    private float desired_air_temperature;
    private float desired_air_humidity;
    private int desired_air_co2;
    private int desired_light_level;

    public Hardware() {
    }

    public Hardware(int hardware_key, String hardware_id, int specimen_key, float desired_air_temperature, float desired_air_humidity, int desired_air_co2, int desired_light_level) {
        this.hardware_key = hardware_key;
        this.hardware_id = hardware_id;
        this.specimen_key = specimen_key;
        this.desired_air_temperature = desired_air_temperature;
        this.desired_air_humidity = desired_air_humidity;
        this.desired_air_co2 = desired_air_co2;
        this.desired_light_level = desired_light_level;
    }

    public int getHardware_key() {
        return hardware_key;
    }

    public void setHardware_key(int hardware_key) {
        this.hardware_key = hardware_key;
    }

    public String getHardware_id() {
        return hardware_id;
    }

    public void setHardware_id(String hardware_id) {
        this.hardware_id = hardware_id;
    }

    public int getSpecimen_key() {
        return specimen_key;
    }

    public void setSpecimen_key(int specimen_key) {
        this.specimen_key = specimen_key;
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

    public int getDesired_light_level() {
        return desired_light_level;
    }

    public void setDesired_light_level(int desired_light_level) {
        this.desired_light_level = desired_light_level;
    }
}
