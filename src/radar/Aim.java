package sharkodlak.robocode.radar;

import robocode.*;
import robocode.util.*;

public class Aim extends Base {
	public double getRight(double robotAndGunRightTurn) {
		double radarHeading = Utils.normalRelativeAngle(robotStatus.getRadarHeadingRadians());
		double radarBearing = Utils.normalRelativeAngle(getTargetAngle() - radarHeading);
		double spin = radarBearing < 0 ? -1 : 1;
		return spin * robocode.Rules.RADAR_TURN_RATE_RADIANS / 2 + radarBearing - robotAndGunRightTurn;
	}
}
