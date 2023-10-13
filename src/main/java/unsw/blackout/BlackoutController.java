package unsw.blackout;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import unsw.blackout.FileTransferException.*;
import unsw.blackout.devices.*;
import unsw.blackout.satellites.*;
import unsw.blackout.superclasses.*;
import unsw.response.models.*;
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
    // List to store all files
    List<File> files = new ArrayList<File>();

    /**
     * create a Device
     * @param deviceId
     * @param type
     * @param position
     */
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

    /**
     * remove a device
     * @param deviceId
     */
    public void removeDevice(String deviceId) {
        for (Device device : devices) {
            String device_Id = device.getDeviceId();
            if (device_Id.equals(deviceId)) {
                devices.remove(device);
                break;
            }
        }
    }

    /**
     * create a satellite
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
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

    /**
     * remove a satellite
     * @param satelliteId
     */
    public void removeSatellite(String satelliteId) {
        for (Satellite satellite : satellites) {
            String currId = satellite.getSatelliteId();
            if (currId.equals(satelliteId)) {
                satellites.remove(satellite);
                break;
            }
        }
    }

    /**
     * list device ids
     * @return list of devices ids
     */
    public List<String> listDeviceIds() {
        List<String> deviceIds = new ArrayList<String>();

        for (Device curr : devices) {
            deviceIds.add(curr.getDeviceId());
        }
        return deviceIds;
    }

    /**
     * list satellite ids
     * @return list of satellite ids
     */
    public List<String> listSatelliteIds() {
        List<String> satelliteIds = new ArrayList<String>();

        for (Satellite curr : satellites) {
            satelliteIds.add(curr.getSatelliteId());
        }
        return satelliteIds;
    }

    /**
     * add file to device
     * @param deviceId
     * @param filename
     * @param content
     */
    public void addFileToDevice(String deviceId, String filename, String content) {
        for (Device device : devices) {
            String device_Id = device.getDeviceId();
            if (device_Id.equals(deviceId)) {
                device.addNewFile(filename, content);
                break;
            }
        }
    }

    /**
     * get information of an entity
     * @param id
     * @return entity info
     */
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

    /**
     * simulate movement of satellite
     */
    public void simulate() {
        for (Satellite satellite : satellites) {
            satellite.move();
        }
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

    /**
     * get communicable entities in range of a given entity
     * @param id
     * @return list of communicable entities
     */
    public List<String> communicableEntitiesInRange(String id) {
        List<String> entitiesInRange = new ArrayList<String>();
        Boolean isSatellite = false;

        for (Satellite satellite : satellites) {
            if (satellite.getSatelliteId().equals(id)) {
                isSatellite = true;
                satellite.communicableEntitiesInRange(entitiesInRange, satellites, devices);
                break;
            }
        }

        if (isSatellite == false) {

            for (Device device : devices) {
                if (device.getDeviceId().equals(id)) {
                    device.communicableEntitiesInRange(entitiesInRange, satellites, devices);
                    break;
                }
            }
        }

        return entitiesInRange;
    }

    /**
     * send file from an entity to another
     * @param fileName
     * @param fromId
     * @param toId
     * @throws FileTransferException
     */
    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        List<String> entitiesInRange = communicableEntitiesInRange(fromId);
        if (!entitiesInRange.contains(toId)) {
            return;
        }

        EntityInfoResponse senderInfo = getInfo(fromId);
        EntityInfoResponse receiverInfo = getInfo(toId);

        if (senderInfo.getType().contains("Device")) {
            Device sender = new Device();
            for (Device device : devices) {
                if (device.getDeviceId().equals(senderInfo.getDeviceId())) {
                    sender = device;
                    break;
                }
            }

            if (receiverInfo.getType().contains("Device") || (senderInfo.getType().equals("DesktopDevice")
                    && receiverInfo.getType().equals("StandardSatellite"))) {
                return;
            }

            Satellite receiver = new StandardSatellite();
            for (Satellite satellite : satellites) {
                if (satellite.getSatelliteId().equals(receiverInfo.getDeviceId())) {
                    receiver = satellite;
                    break;
                }
            }

            List<File> files = sender.getFiles();
            File sentFile = new File();
            for (File file : files) {
                if (file.getFileName().equals(fileName)) {
                    sentFile = file;
                    break;
                }
            }

            if (sentFile.equals(new File()) || !sentFile.isFileComplete()) {
                throw new VirtualFileNotFoundException(fileName);
            }

            List<File> receiversfiles = receiver.getFiles();
            for (File file : files) {
                if (file.getFileName().equals(fileName)) {
                    throw new VirtualFileAlreadyExistsException(fileName);
                }
            }

            if (receiver.getByteStorageUsed() + sentFile.getFileSize() >= receiver.getByteStorageSize()) {
                throw new VirtualFileNoStorageSpaceException("File Size Exceeds Max Storage");
            }

            if (receiver.getFiles().size() == receiver.getMaxFiles()) {
                throw new VirtualFileNoStorageSpaceException("Max Number Of Files Reached");
            }

            File newFile = new File(sentFile.getFileName(), sentFile.getContent(), sentFile.getFileSize(),
                    senderInfo.getDeviceId(), receiverInfo.getDeviceId(), sentFile.getProgress(), false);
            receiversfiles.add(newFile);
            receiver.setFiles(receiversfiles);
            files.add(newFile);

        } else if (senderInfo.getType().contains("Satellite")) {
            if (receiverInfo.getType().contains("Device")) {
                if (receiverInfo.getType().equals("DesktopDevice")
                        && senderInfo.getType().equals("StandardSatellite")) {
                    return;
                }

                Satellite sender = new StandardSatellite();
                for (Satellite satellite : satellites) {
                    if (satellite.getSatelliteId().equals(senderInfo.getDeviceId())) {
                        sender = satellite;
                        break;
                    }
                }

                Device receiver = new Device();
                for (Device device : devices) {
                    if (device.getDeviceId().equals(receiverInfo.getDeviceId())) {
                        receiver = device;
                        break;
                    }
                }

                List<File> sendersFiles = sender.getFiles();
                File sentFile = new File();
                for (File file : files) {
                    if (file.getFileName().equals(fileName)) {
                        sentFile = file;
                        break;
                    }
                }

                File newFile = new File(sentFile.getFileName(), sentFile.getContent(), sentFile.getFileSize(),
                        senderInfo.getDeviceId(), receiverInfo.getDeviceId(), sentFile.getProgress(), false);
                sendersFiles.add(newFile);
                receiver.setFiles(sendersFiles);
                files.add(newFile);
            } else if (receiverInfo.getType().contains("Satellite")) {
                Satellite sender = new StandardSatellite();
                for (Satellite satellite : satellites) {
                    if (satellite.getSatelliteId().equals(senderInfo.getDeviceId())) {
                        sender = satellite;
                        break;
                    }
                }

                Satellite receiver = new StandardSatellite();
                for (Satellite satellite : satellites) {
                    if (satellite.getSatelliteId().equals(receiverInfo.getDeviceId())) {
                        receiver = satellite;
                        break;
                    }
                }

                List<File> sendersFiles = sender.getFiles();
                File sentFile = new File();
                for (File file : files) {
                    if (file.getFileName().equals(fileName)) {
                        sentFile = file;
                        break;
                    }
                }

                if (sentFile.equals(new File()) || !sentFile.isFileComplete()) {
                    throw new VirtualFileNotFoundException(fileName);
                }

                List<File> receiversfiles = receiver.getFiles();
                for (File file : files) {
                    if (file.getFileName().equals(fileName)) {
                        throw new VirtualFileAlreadyExistsException(fileName);
                    }
                }

                if (receiver.getByteStorageSize() + sentFile.getFileSize() >= receiver.getByteStorageSize()) {
                    throw new VirtualFileNoStorageSpaceException("File Size Exceeds Max Storage");
                }

                if (receiver.getFiles().size() == receiver.getMaxFiles()) {
                    throw new VirtualFileNoStorageSpaceException("Max Number Of Files Reached");
                }

                File newFile = new File(sentFile.getFileName(), sentFile.getContent(), sentFile.getFileSize(),
                        senderInfo.getDeviceId(), receiverInfo.getDeviceId(), sentFile.getProgress(), false);
                receiversfiles.add(newFile);
                receiver.setFiles(receiversfiles);
                files.add(newFile);
            }
        }
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
