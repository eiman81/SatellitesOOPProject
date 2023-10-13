package unsw.blackout.superclasses;

import java.util.List;
import unsw.utils.Angle;
import unsw.utils.MathsHelper;

public abstract class Satellite {
    private String satelliteId;
    private double height;
    private Angle position;
    private double linearVelocity;
    private double range;
    private int sendByteRate;
    private int receiveByteRate;
    private int byteStorageSize;
    private int byteStorageUsed;
    private int maxFiles;
    private String type;
    private List<File> files;

    public Satellite(String satelliteId, double height, Angle position, String type, double linearVelocity,
            double range, int byteStorageSize, int byteStorageUsed, int maxFiles, int sendByteRate,
            int receiveByteRate) {
        this.satelliteId = satelliteId;
        this.height = height;
        this.position = position;
        this.type = type;
        this.linearVelocity = linearVelocity;
        this.range = range;
    }

    public Satellite() {
        this.satelliteId = "";
        this.height = 0;
        this.position = Angle.fromDegrees(0);
        this.type = "";
        this.linearVelocity = 0;
        this.range = 0;
    }

    /**
     * getters and setters
     */

    public String getSatelliteId() {
        return satelliteId;
    }

    public double getHeight() {
        return height;
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

    public void setPosition(Angle position) {
        this.position = position;
    }

    public double getLinearVelocity() {
        return linearVelocity;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public double getRange() {
        return range;
    }

    public int getSendByteRate() {
        return sendByteRate;
    }

    public int getReceiveByteRate() {
        return receiveByteRate;
    }

    public int getByteStorageSize() {
        return byteStorageSize;
    }

    public int getByteStorageUsed() {
        return byteStorageUsed;
    }

    public int getMaxFiles() {
        return maxFiles;
    }

    /**
     * move abstract function: different for every satellite
     */
    public abstract void move();

    /**
     * Check communicable entities in range of the satellite
     * @param entitiesInRange
     * @param satellites
     * @param devices
     */
    public void communicableEntitiesInRange(List<String> entitiesInRange, List<Satellite> satellites,
            List<Device> devices) {

        // Check communicable devices in range of the satellite
        for (Device device : devices) {
            if (type.equals("StandardSatellite")) {
                String deviceType = device.getType();
                if (!(deviceType.equals("HandheldDevice") || deviceType.equals("LaptopDevice"))) {
                    continue;
                }
            }

            if (!MathsHelper.isVisible(height, position, device.getPosition())) {
                continue;
            }

            if (MathsHelper.getDistance(height, position, device.getPosition()) > range) {
                continue;
            }

            if (!entitiesInRange.contains(device.getDeviceId())) {
                entitiesInRange.add(device.getDeviceId());
            }
        }

        // check communicable satellites in range of this satellite
        for (Satellite satellite : satellites) {
            if (satellite.getSatelliteId().equals(satelliteId)) {
                continue;
            }

            if (!MathsHelper.isVisible(height, position, satellite.height, satellite.position)) {
                continue;
            }

            if (MathsHelper.getDistance(height, position, satellite.getHeight(), satellite.getPosition()) > range) {
                continue;
            }

            if (!entitiesInRange.contains(satellite.getSatelliteId())
                    && satellite.getSatelliteId().equals("RelaySatellite")) {
                entitiesInRange.add(satellite.getSatelliteId());
                satellite.communicableEntitiesInRange(entitiesInRange, satellites, devices);
            }

            if (!entitiesInRange.contains(satellite.getSatelliteId())) {
                entitiesInRange.add(satellite.getSatelliteId());
            }
        }
    }

}
