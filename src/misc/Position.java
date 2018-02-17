package sharkodlak.robocode.misc;

import robocode.*;
import robocode.util.*;

public class Position {
	private final double x, y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public static double getTargetAngle(robocode.RobotStatus robotStatus, double targetX, double targetY) {
		return getTargetAngle(robotStatus.getX(), robotStatus.getY(), targetX, targetY);
	}

	public static double getTargetAngle(double x, double y, double targetX, double targetY) {
		return Math.atan2(targetX - x, targetY - y);
	}

	public static double getTargetBearing(robocode.RobotStatus robotStatus, double targetX, double targetY, double heading) {
		return getTargetBearing(robotStatus.getX(), robotStatus.getY(), targetX, targetY, heading);
	}

	public static double getTargetBearing(double x, double y, double targetX, double targetY, double heading) {
		double targetAngle = getTargetAngle(x, y, targetX, targetY);
		return Utils.normalRelativeAngle(targetAngle - Utils.normalRelativeAngle(heading));
	}

	public static double getTargetDistance(robocode.RobotStatus robotStatus, double targetX, double targetY) {
		return getTargetDistance(robotStatus.getX(), robotStatus.getY(), targetX, targetY);
	}

	public static double getTargetDistance(double x, double y, double targetX, double targetY) {
		return Math.hypot(targetX - x, targetY - y);
	}
}
