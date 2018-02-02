package sharkodlak.robocode.radar;

import robocode.*;

abstract public class Base implements Operator {
	protected RobotStatus robotStatus;
	protected double targetPositionX, targetPositionY;

	public double getTargetAngle() {
		return Math.atan2(targetPositionX - robotStatus.getX(), targetPositionY - robotStatus.getY());
	}

	public Operator setRobotStatus(RobotStatus robotStatus) {
		this.robotStatus = robotStatus;
		return this;
	}

	public Operator setTarget(double x, double y) {
		targetPositionX = x;
		targetPositionY = y;
		return this;
	}
}
