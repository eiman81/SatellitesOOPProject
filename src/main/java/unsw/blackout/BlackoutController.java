package unsw.blackout;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.management.relation.RelationException;

import unsw.response.models.EntityInfoResponse;
import unsw.response.models.FileInfoResponse;
import unsw.utils.Angle;

import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;

/**
 * The controller for the Blackout system.
 *
 * WARNING: Do not move this file or modify any of the existing method
 * signatures
 */
public class BlackoutController {
    // List to store all devices
    public List<Device> devices = new ArrayList<Device>();
    // List to store all satellites
    public List<Satellite> satellites = new ArrayList<Satellite>();

    public void createDevice(String deviceId, String type, Angle position) {
        // Initialise new device as null
        Device newDevice = null;

        if (type.equals("HandheldDevice")) {
            newDevice = new HandheldDevice(deviceId, position);
        } else if (type.equals("LaptopDevice")) {
            newDevice = new LaptopDevice(deviceId, position);
        } else if (type.equals("DesktopDevice")) {
            newDevice = new DesktopDevice(deviceId, position);
        }

        if (newDevice != null) {
            devices.add(newDevice);
        }
    }

    public void removeDevice(String deviceId) {
        for (Device device : devices) {
            String device_Id = device.getDeviceId();
            if (device_Id.equals(deviceId)) {
                devices.remove(device);
                break;
            }
        }
    }

    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        Satellite newSatellite = null;

        if (type.equals("StandardSatellite")) {
            newSatellite = new StandardSatellite(satelliteId, height, position);
        } else if (type.equals("TeleportingSatellite")) {
            newSatellite = new TeleportingSatellite(satelliteId, height, position);
        } else if (type.equals("RelaySatellite")) {
            newSatellite = new RelaySatellite(satelliteId, height, position);
        }

        if (newSatellite != null) {
            satellites.add(newSatellite);
        }
    }

    public void removeSatellite(String satelliteId) {
        for (Satellite satellite : satellites) {
            String currId = satellite.getSatelliteId();
            if (currId.equals(satelliteId)) {
                satellites.remove(satellite);
                break;
            }
        }
    }

    public List<String> listDeviceIds() {
        List<String> deviceIds = new ArrayList<String>();

        for (Device curr : devices) {
            deviceIds.add(curr.getDeviceId());
        }
        return deviceIds;
    }

    public List<String> listSatelliteIds() {
        List<String> satelliteIds = new ArrayList<String>();

        for (Satellite curr : satellites) {
            satelliteIds.add(curr.getSatelliteId());
        }
        return satelliteIds;
    }

    public void addFileToDevice(String deviceId, String filename, String content) {
        for (Device device : devices) {
            String device_Id = device.getDeviceId();
            if (device_Id.equals(deviceId)) {
                device.addNewFile(filename, content);
                break;
            }
        }
    }

    public EntityInfoResponse getInfo(String id) {
        EntityInfoResponse info = null;

        for (Device curr : devices) {
            String currId = curr.getDeviceId();
            if (currId.equals(id)) {
                Map<String, FileInfoResponse> fileMap = new HashMap<>();
                List<File> files = curr.getFiles();
                for (File file : files) {
                    fileMap.put(file.getFileName(), new FileInfoResponse(file.getFileName(), file.getContent(),
                            file.getFileSize(), file.isFileComplete()));
                }
                info = new EntityInfoResponse(id, curr.getPosition(), RADIUS_OF_JUPITER, curr.getType(), fileMap);
            }
        }

        if (info == null) {
            for (Satellite curr1 : satellites) {
                String currId1 = curr1.getSatelliteId();
                if (currId1.equals(id)) {
                    info = new EntityInfoResponse(id, curr1.getPosition(), curr1.getHeight(), curr1.getType());
                }
            }
        }
        return info;
    }

    public void simulate() {
        // TODO: Task 2a)
    }

    /**
     * Simulate for the specified number of minutes. You shouldn't need to modify
     * this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();
        }
    }

    public List<String> communicableEntitiesInRange(String id) {
        // TODO: Task 2 b)
        return new ArrayList<>();
    }

    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        // TODO: Task 2 c)
    }

    public void createDevice(String deviceId, String type, Angle position, boolean isMoving) {
        createDevice(deviceId, type, position);
        // TODO: Task 3
    }

    public void createSlope(int startAngle, int endAngle, int gradient) {
        // TODO: Task 3
        // If you are not completing Task 3 you can leave this method blank :)
    }
}
