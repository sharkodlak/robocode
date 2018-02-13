package sharkodlak.robocode.gunner;

import robocode.*;

public interface Gunner {
	public static final double AIM_ACCEPTABLE_DEVIATION = Math.PI / 180;
	public static double MAX_BULLET_SPEED = robocode.Rules.getBulletSpeed(robocode.Rules.MIN_BULLET_POWER);
	public static double MIN_BULLET_SPEED = robocode.Rules.getBulletSpeed(robocode.Rules.MAX_BULLET_POWER);
	public Gunner fire(double energy);
	public double getBulletPower();
	public double getRight(double robotRightTurn);
	public boolean isAimed();
	public Gunner setRobotStatus(RobotStatus robotStatus);
	public Gunner setTarget(double x, double y);
}
