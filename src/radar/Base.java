package sharkodlak.robocode.radar;

import robocode.*;

abstract public class Base implements Operator {
	protected RobotStatus robotStatus;
	protected double targetPositionX, targetPositionY;

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
