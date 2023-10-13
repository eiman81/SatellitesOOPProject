package unsw.blackout.devices;

import unsw.blackout.superclasses.Device;
import unsw.utils.Angle;

public class DesktopDevice extends Device {
    private static int range = 200000;
    private static String type = "DesktopDevice";

    public DesktopDevice(String deviceId, Angle position) {
        super(deviceId, position, type, range);
    }
}
