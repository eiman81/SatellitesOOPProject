package unsw.blackout.devices;

import unsw.blackout.superclasses.Device;
import unsw.utils.Angle;

public class HandheldDevice extends Device {
    private static int range = 50000;
    private static String type = "HandheldDevice";

    public HandheldDevice(String deviceId, Angle position) {
        super(deviceId, position, type, range);
    }
}
