package unsw.blackout.devices;

import java.util.ArrayList;
import java.util.List;

import unsw.blackout.superclasses.Device;
import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

public class HandheldDevice extends Device {
    private static int range = 50000;
    private static String type = "HandheldDevice";

    public HandheldDevice(String deviceId, Angle position) {
        super(deviceId, position, type, range);
    }
}
