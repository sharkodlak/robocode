package sharkodlak.robocode.gunner;

import robocode.*;

abstract public class Spin implements Gunner {
	public Gunner fire(double energy) {
		return this;
	}

	public double getBulletPower() {
		return Double.NaN;
	}

	public boolean isAimed() {
		return false;
	}

	public Gunner setRobotStatus(RobotStatus robotStatus) {
		return this;
	}

	public Gunner setTarget(double x, double y) {
		return this;
	}

	static public class Right extends Spin {
		public double getRight(double robotRightTurn) {
			return Double.POSITIVE_INFINITY;
		}
	}
}
