package sharkodlak.robocode.gunner;

import robocode.*;
import robocode.util.*;

public class Aim extends Base {
	public double getRight(double robotRightTurn) {
		double nextGunHeading = Utils.normalAbsoluteAngle(robotStatus.getGunHeadingRadians() + robotRightTurn);
		double gunBearing = Utils.normalRelativeAngle(getTargetAngle() - nextGunHeading);
		aimed = Math.abs(gunBearing) < robocode.Rules.GUN_TURN_RATE_RADIANS;
		//out.println("aimed: " + (aimed ? "true" : "false") + ", gunBearing: |" + Math.toDegrees(gunBearing) + "|° < " + robocode.Rules.GUN_TURN_RATE);
		return gunBearing;
	}
}
