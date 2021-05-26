package sep4.iot.gateway.model;
import java.io.Serializable;

/**
 * HardwareUser object contains the needed user-side information for starting and handling
 * user listening threads for sensor data
 *
 * @see java.io.Serializable
 * @author Daria Popa
 * @version 1.0
 * @since 26-05-2021
 */
public class HardwareUser implements Serializable {

    private int user_key;
    private String appToken;

    /**
     * Default constructor
     */
    public HardwareUser(){}

    /**
     * Base constructor for HardwareUser
     * @param user_key - unique identifier for the user
     * @param appToken - unique app token used to connect to Loriot network
     */
    public HardwareUser(int user_key, String appToken) {
        this.user_key = user_key;
        this.appToken = appToken;
    }

    /**
     * Getter for the user_key
     * @return an integer value representing the user_key of the HardwareUser
     */
    public int getUser_key() {
        return user_key;
    }

    /**
     * Setter for the user_key
     * @param user_key - the new value for the unique identifier of the HardwareUser
     */
    public void setUser_key(int user_key) {
        this.user_key = user_key;
    }

    /**
     * Getter for the appToken
     * @return a reference to the String value of the application token of the HardwareUser
     */
    public String getAppToken() {
        return appToken;
    }

    /**
     * Setter for the appToken
     * @param appToken - the new String value for the application token of the HardwareUser
     */
    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}
