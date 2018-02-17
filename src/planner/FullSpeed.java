package sharkodlak.robocode.planner;

import robocode.*;

abstract public class FullSpeed implements Planner {
	public Planner setRobotStatus(RobotStatus robotStatus) {
		return this;
	}

	public Planner setDestination(double x, double y) {
		return this;
	}

	public static class Ahead extends FullSpeed {
		public double getAhead() {
			return Double.POSITIVE_INFINITY;
		}

		public double getRight() {
			return 0;
		}

		public static class Right extends Ahead {
			public double getRight() {
				return Double.POSITIVE_INFINITY;
			}
		}
	}

	public static class Right extends FullSpeed {
		public double getAhead() {
			return 0;
		}

		public double getRight() {
			return Double.POSITIVE_INFINITY;
		}
	}
}
