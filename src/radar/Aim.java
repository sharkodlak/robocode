package sharkodlak.robocode.radar;

import robocode.*;
import sharkodlak.robocode.misc.*;

public class Aim extends Base {
	public double getRight(double robotAndGunRightTurn) {
		double radarBearing = Position.getBearing(robotStatus, targetPositionX, targetPositionY, robotStatus.getRadarHeadingRadians());
		double spin = radarBearing < 0 ? -1 : 1;
		return spin * robocode.Rules.RADAR_TURN_RATE_RADIANS / 2 + radarBearing - robotAndGunRightTurn;
	}
}
