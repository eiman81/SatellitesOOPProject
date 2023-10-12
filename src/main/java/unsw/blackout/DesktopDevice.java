package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

public class DesktopDevice extends Device {
    private static int range = 200000;
    private static String type = "DesktopDevice";

    public DesktopDevice(String deviceId, Angle position) {
        super(deviceId, position);
        super.setType(type);
    }
}
