package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

public class Device {
    private String deviceId;
    private Angle position;
    private String type;
    private static List<File> files = new ArrayList<File>();

    public Device(String deviceId, Angle position) {
        this.deviceId = deviceId;
        this.position = position;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void addNewFile(String fileName, String content) {
        File newFile = new File(fileName, content);
        files.add(newFile);
    }

    public Angle getPosition() {
        return position;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static List<File> getFiles() {
        return files;
    }
}
