package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

public class StandardSatellite extends Satellite {
    private static double LinearSpeed = 2500;
    private static double range = 150000;
    private int sendByteRate = 1;
    private int receiveByteRate = 1;
    private static int byteStorageSize = 80;
    private static String type = "StandardSatellite";

    public StandardSatellite(String satelliteId, double height, Angle position) {
        super(satelliteId, height, position);
        super.setType(type);
    }
}
