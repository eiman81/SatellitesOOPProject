package unsw.blackout.satellites;

import unsw.blackout.superclasses.Satellite;
import unsw.utils.Angle;

public class TeleportingSatellite extends Satellite {
    private static double linearVelocity = 1000;
    private static double range = 200000;
    private int sendByteRate = 10;
    private int receiveByteRate = 15;
    private static int byteStorageSize = 200;
    private static String type = "TeleportingSatellite";
    private int direction;

    public TeleportingSatellite(String satelliteId, double height, Angle position) {
        super(satelliteId, height, position, type, linearVelocity, range, 200, 0, 201, 10, 15);
        this.direction = -1;
    }

    public void move() {
        double angularVelocity = super.getLinearVelocity() / super.getHeight();
        Angle angleChange = Angle.fromRadians(angularVelocity);
        Angle currAngle = super.getPosition();
        double currDegrees = currAngle.toDegrees();
        Angle prevAngle = currAngle;

        if (currDegrees >= 360) {
            super.setPosition(Angle.fromDegrees(currDegrees - 360));
            currAngle = super.getPosition();
        } else if (currDegrees < 0) {
            super.setPosition(Angle.fromDegrees(currDegrees + 360));
            currAngle = super.getPosition();
        }

        if (direction == -1) {
            super.setPosition(currAngle.add(angleChange));
            currAngle = super.getPosition();
        } else {
            super.setPosition(currAngle.subtract(angleChange));
            currAngle = super.getPosition();
        }

        if ((currAngle.compareTo(Angle.fromDegrees(180)) == 1 && prevAngle.compareTo(Angle.fromDegrees(180)) == -1
                && direction == -1
                || (currAngle.compareTo(Angle.fromDegrees(180))) == -1
                        && prevAngle.compareTo(Angle.fromDegrees(180)) == 1 && direction == 1)) {
            super.setPosition(new Angle());
            currAngle = super.getPosition();

            if (direction == 1) {
                direction = -1;
            } else {
                direction = 1;
            }
        }
    }
}
