package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

public class LaptopDevice extends Device {
    private static int range = 100000;
    private static String type = "LaptopDevice";

    public LaptopDevice(String deviceId, Angle position) {
        super(deviceId, position);
        super.setType(type);
    }
}
