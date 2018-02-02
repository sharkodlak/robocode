package sharkodlak.robocode.planner;

abstract public class FullSpeed implements Planner {
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
}
