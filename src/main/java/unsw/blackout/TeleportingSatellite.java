package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

public class TeleportingSatellite extends Satellite {
    private static double LinearSpeed = 1000;
    private static double range = 200000;
    private int sendByteRate = 10;
    private int receiveByteRate = 15;
    private static int byteStorageSize = 200;
    private static String type = "TeleportSatellite";

    public TeleportingSatellite(String satelliteId, double height, Angle position) {
        super(satelliteId, height, position);
        super.setType(type);
    }
}
