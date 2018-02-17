package sharkodlak.robocode.planner;

import robocode.*;
import sharkodlak.robocode.misc.*;

public class Positioner implements Planner {
	protected robocode.RobotStatus robotStatus;
	protected double destinationX, destinationY;
	protected double direction = 1;

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

	public Positioner setRobotStatusAndDestination(robocode.RobotStatus robotStatus, double x, double y) {
		setRobotStatus(robotStatus);
		return setDestination(x, y);
	}

	public Positioner setRobotStatus(robocode.RobotStatus robotStatus) {
		this.robotStatus = robotStatus;
		return this;
	}

	public Positioner setDestination(double x, double y) {
		destinationX = x;
		destinationY = y;
		return this;
	}

	public static class Closer extends Positioner {
		private double rangeMax, rangeMin;
		private double ahead;
		private boolean closing = false;

		public Closer(double rangeMax, double rangeMin) {
			if (rangeMax < rangeMin) {
				throw new IllegalArgumentException(String.format(
					"Argument rangeMax must be greater or equal to rangeMin, %.2f and %.2f given.",
					rangeMax,
					rangeMin
				));
			}
			this.rangeMax = rangeMax;
			this.rangeMin = rangeMin;
		}

		public double getAhead() {
			return ahead;
		}

		public boolean isClosing() {
			return closing;
		}

		public Closer setRobotStatusAndDestination(robocode.RobotStatus robotStatus, double x, double y) {
			super.setRobotStatusAndDestination(robotStatus, x, y);
			ahead = super.getAhead();
			if (Math.abs(ahead) > rangeMax) {
				closing = true;
			} else if (Math.abs(ahead) < rangeMin) {
				closing = false;
			}
			return this;
		}
	}
}
