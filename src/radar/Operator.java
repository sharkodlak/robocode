package sharkodlak.robocode.radar;

import robocode.*;

public interface Operator {
	public double getRight(double robotAndGunRightTurn);
	public Operator setRobotStatus(RobotStatus robotStatus);
	public Operator setTarget(double x, double y);
}
