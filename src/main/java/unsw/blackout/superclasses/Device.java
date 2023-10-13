package unsw.blackout.superclasses;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ranges.Range;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;
import unsw.utils.MathsHelper;

public class Device {
    private String deviceId;
    private Angle position;
    private String type;
    private static List<File> files = new ArrayList<File>();
    private int range;

    public Device(String deviceId, Angle position, String type, int range) {
        this.deviceId = deviceId;
        this.position = position;
        this.type = type;
        this.range = range;
    }

    public Device() {
        this.deviceId = "";
        this.position = Angle.fromDegrees(0);
        this.type = "";
        this.range = 0;
    }

    /**
     * getters and setters
     */
    public String getDeviceId() {
        return deviceId;
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

    public static void setFiles(List<File> files) {
        Device.files = files;
    }

    /**
     * add new file
     * @param fileName
     * @param content
     */
    public void addNewFile(String fileName, String content) {
        File newFile = new File(fileName, content);
        files.add(newFile);
    }

    /**
     * check for communicable entities in range
     * @param entitiesInRange
     * @param satellites
     * @param devices
     */
    public void communicableEntitiesInRange(List<String> entitiesInRange, List<Satellite> satellites,
            List<Device> devices) {
        for (Satellite satellite : satellites) {
            if (!MathsHelper.isVisible(satellite.getHeight(), satellite.getPosition(), position)) {
                continue;
            }

            if (MathsHelper.getDistance(satellite.getHeight(), satellite.getPosition(), position) > range) {
                continue;
            }

            if (!entitiesInRange.contains(satellite.getSatelliteId()) && satellite.getType().equals("RelaySatellite")) {
                entitiesInRange.add(satellite.getSatelliteId());
                satellite.communicableEntitiesInRange(entitiesInRange, satellites, devices);
            }

            if (!entitiesInRange.contains(satellite.getSatelliteId())) {
                entitiesInRange.add(satellite.getSatelliteId());
            }
        }
    }
}
