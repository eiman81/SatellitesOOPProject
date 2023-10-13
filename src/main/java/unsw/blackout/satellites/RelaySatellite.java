package unsw.blackout.satellites;

import unsw.blackout.superclasses.Satellite;
import unsw.utils.Angle;

public class RelaySatellite extends Satellite {
    private static double linearVelocity = 1500;
    private static double range = 300000;
    private static String type = "RelaySatellite";
    private int direction;

    public RelaySatellite(String satelliteId, double height, Angle position) {
        super(satelliteId, height, position, type, linearVelocity, range, 0, 0, 0, -1, -1);
        this.direction = 1;
    }

    public void move() {
        double angularVelocity = super.getLinearVelocity() / super.getHeight();
        Angle angleChange = Angle.fromRadians(angularVelocity);
        Angle currAngle = super.getPosition();
        double currDegrees = currAngle.toDegrees();

        if (currDegrees <= 140 && direction == 1) {
            Angle newAngle = currAngle.add(angleChange);
            super.setPosition(newAngle);
            this.direction = -1;
        } else if (currDegrees >= 190 && direction == -1) {
            Angle newAngle = currAngle.subtract(angleChange);
            super.setPosition(newAngle);
            this.direction = 1;
        }

        if (currDegrees >= 140 && currDegrees <= 190) {
            if (direction == 1) {
                Angle newAngle = currAngle.subtract(angleChange);
                super.setPosition(newAngle);
            } else {
                Angle newAngle = currAngle.add(angleChange);
                super.setPosition(newAngle);
            }
        } else {
            if (currDegrees >= 345) {
                Angle newAngle = currAngle.subtract(angleChange);
                super.setPosition(newAngle);
                this.direction = -1;
            } else if (currDegrees < 345) {
                Angle newAngle = currAngle.add(angleChange);
                super.setPosition(newAngle);
            }
        }

        if (currDegrees >= 360) {
            super.setPosition(Angle.fromDegrees(currDegrees - 360));
            currAngle = super.getPosition();
        } else if (currDegrees < 0) {
            super.setPosition(Angle.fromDegrees(currDegrees + 360));
            currAngle = super.getPosition();
        }
    }
}
