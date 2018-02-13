package sharkodlak.robocode.gunner;

import robocode.*;
import robocode.util.*;

abstract public class Base implements Gunner {
	protected boolean aimed = false;
	protected java.io.PrintStream out;
	protected RobotStatus robotStatus;
	protected double targetPositionX, targetPositionY;

	public Gunner fire(double energy) {
		aimed = false;
		return this;
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

	public Gunner setOut(java.io.PrintStream out) {
		this.out = out;
		return this;
	}
}
