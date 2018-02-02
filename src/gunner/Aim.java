package sharkodlak.robocode.gunner;

import robocode.*;
import robocode.util.*;

public class Aim extends Base {
	public double getRight(double robotRightTurn) {
		double nextGunHeading = Utils.normalAbsoluteAngle(robotStatus.getGunHeadingRadians() + robotRightTurn);
		double gunBearing = Utils.normalRelativeAngle(getTargetAngle() - nextGunHeading);
		aimed = gunBearing < aimAcceptableDeviation;
		return gunBearing;
	}
}
