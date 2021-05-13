package sep4.iot.gateway.model;

public class DownlinkMessage {

    private String cmd;
    private String EUI;
    private int port;
    private boolean confirmed;
    private String data;

    public DownlinkMessage(String cmd, String EUI, int port, boolean confirmed, String data) {
        this.cmd = cmd;
        this.EUI = EUI;
        this.port = port;
        this.confirmed = confirmed;
        this.data = data;
    }

    public DownlinkMessage(String EUI, String data) {
        cmd="tx";
        this.EUI = EUI;
        port=2;
        confirmed=false;
        this.data = data;
    }

    @Override
    public String toString() {
        return "DownlinkMessage{" +
                "cmd='" + cmd + '\'' +
                ", EUI='" + EUI + '\'' +
                ", port=" + port +
                ", confirmed=" + confirmed +
                ", data='" + data + '\'' +
                '}';
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getEUI() {
        return EUI;
    }

    public void setEUI(String EUI) {
        this.EUI = EUI;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
