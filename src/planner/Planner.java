package sharkodlak.robocode.planner;

import robocode.*;

public interface Planner {
	public double getAhead();
	public double getRight();
	public Planner setRobotStatus(robocode.RobotStatus robotStatus);
	public Planner setDestination(double x, double y);
}
