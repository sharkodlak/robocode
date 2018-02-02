package sharkodlak.robocode.gunner;

import robocode.*;

public interface Gunner {
	public static final double AIM_ACCEPTABLE_DEVIATION = Math.PI / 180 / 60;
	public double getBulletPower();
	public double getRight(double robotRightTurn);
	public boolean isAimed();
	public Gunner setRobotStatus(RobotStatus robotStatus);
	public Gunner setTarget(double x, double y);
}
