package unsw.blackout;

import unsw.utils.Angle;

public class RelaySatellite extends Satellite {
    private static double LinearSpeed = 1500;
    private static double range = 300000;
    private static String type = "RelaySatellite";

    public RelaySatellite(String satelliteId, double height, Angle position) {
        super(satelliteId, height, position);
        super.setType(type);
    }
}
