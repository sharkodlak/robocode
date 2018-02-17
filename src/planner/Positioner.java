package sharkodlak.robocode.planner;

import robocode.*;
import sharkodlak.robocode.misc.*;

public class Positioner implements Planner {
	protected robocode.RobotStatus robotStatus;
	protected double destinationX, destinationY;
	private double direction = 1;

	public double getAhead() {
		return direction * Position.getTargetDistance(robotStatus, destinationX, destinationY);
	}

	public double getRight() {
		double bearing = Position.getTargetBearing(robotStatus, destinationX, destinationY, robotStatus.getHeadingRadians());
		double spin = bearing >= 0 ? 1 : -1;
		direction = Math.abs(bearing) <= Round.PERPENDICULAR ? 1 : -1;
		if (direction < 0) {
			bearing -= spin * Round.HALF_ROUND;
		}
		return bearing;
	}

	public Planner setRobotStatus(robocode.RobotStatus robotStatus) {
		this.robotStatus = robotStatus;
		return this;
	}

	public Planner setDestination(double x, double y) {
		destinationX = x;
		destinationY = y;
		return this;
	}
}
