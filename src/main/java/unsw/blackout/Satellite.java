package unsw.blackout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

public class Satellite {
    private String satelliteId;
    private double height;
    private Angle position;
    private double LinearSpeed;
    private double range;
    private int sendByteRate;
    private int receiveByteRate;
    private int byteStorageSize;
    private int byteStorageUsed;
    private String type;

    public Satellite(String satelliteId, double height, Angle position) {
        this.satelliteId = satelliteId;
        this.height = height;
        this.position = position;
    }

    public String getSatelliteId() {
        return satelliteId;
    }

    public double getHeight() {
        return height;
    }

    public Angle getPosition() {
        return position;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
