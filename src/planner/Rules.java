package sharkodlak.robocode.planner;

import robocode.*;

public class Rules {
	public static double getRight(double turnRight, double velocity) {
		double turnRate = robocode.Rules.getTurnRateRadians(velocity);
		double spin = turnRight < 0 ? -1 : 1;
		return Math.abs(turnRight) > turnRate ? spin * turnRate : turnRight;
	}
}
