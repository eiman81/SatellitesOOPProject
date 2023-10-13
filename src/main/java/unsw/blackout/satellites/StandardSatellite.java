package unsw.blackout.satellites;

import unsw.blackout.superclasses.Satellite;
import unsw.utils.Angle;

public class StandardSatellite extends Satellite {
    private static double linearVelocity = 2500;
    private static double range = 150000;
    private int sendByteRate = 1;
    private int receiveByteRate = 1;
    private static int byteStorageSize = 80;
    private static String type = "StandardSatellite";

    public StandardSatellite(String satelliteId, double height, Angle position) {
        super(satelliteId, height, position, type, linearVelocity, range, 80, 0, 3, 1, 1);
    }

    public StandardSatellite() {
        super();
    }

    public void move() {
        double angularVelocity = super.getLinearVelocity() / super.getHeight();
        Angle angleChange = Angle.fromRadians(angularVelocity);
        Angle currAngle = super.getPosition();
        double currDegrees = currAngle.toDegrees();

        if (currDegrees >= 360) {
            super.setPosition(Angle.fromDegrees(currDegrees - 360));
            currAngle = super.getPosition();
        } else if (currDegrees <= 0) {
            super.setPosition(Angle.fromDegrees(currDegrees + 360));
            currAngle = super.getPosition();
        }

        Angle newAngle = currAngle.subtract(angleChange);
        super.setPosition(newAngle);
    }
}
