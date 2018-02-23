package sharkodlak.robocode.misc;

import robocode.*;
import robocode.util.*;
import sharkodlak.geometry.*;

public class Position extends sharkodlak.geometry.Position {
	public Position(double x, double y) {
		super(x, y);
	}

	public static double getBearing(robocode.RobotStatus robotStatus, double destinationX, double destinationY, double heading) {
		return getBearing(robotStatus.getX(), robotStatus.getY(), destinationX, destinationY, heading);
	}

	public static double getBearing(double x, double y, double destinationX, double destinationY, double heading) {
		double destinationAngle = getAngle(x, y, destinationX, destinationY);
		return Utils.normalRelativeAngle(destinationAngle - Utils.normalRelativeAngle(heading));
	}

	public static double getDistance(robocode.RobotStatus robotStatus, double destinationX, double destinationY) {
		return getDistance(robotStatus.getX(), robotStatus.getY(), destinationX, destinationY);
	}
}
