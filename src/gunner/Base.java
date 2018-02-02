package sharkodlak.robocode.gunner;

import robocode.*;
import robocode.util.*;

abstract public class Base implements Gunner {
	protected double aimAcceptableDeviation = Double.NaN;
	protected boolean aimed = false;
	protected RobotStatus robotStatus;
	protected double targetPositionX, targetPositionY;

	public Base() {
		this(AIM_ACCEPTABLE_DEVIATION);
	}

	public Base(double aimAcceptableDeviation) {
		this.aimAcceptableDeviation = aimAcceptableDeviation;
	}

	public double getBulletPower() {
		return 1;
	}

	public double getTargetAngle() {
		return Math.atan2(targetPositionX - robotStatus.getX(), targetPositionY - robotStatus.getY());
	}

	public boolean isAimed() {
		return aimed;
	}

	public Gunner setRobotStatus(RobotStatus robotStatus) {
		this.robotStatus = robotStatus;
		return this;
	}

	public Gunner setTarget(double x, double y) {
		targetPositionX = x;
		targetPositionY = y;
		return this;
	}
}