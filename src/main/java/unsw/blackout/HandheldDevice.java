package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

public class HandheldDevice extends Device {
    private static int range = 50000;
    private static String type = "HandheldDevice";

    public HandheldDevice(String deviceId, Angle position) {
        super(deviceId, position);
        super.setType(type);
    }
}
