package sharkodlak.robocode.radar;

public abstract class Spin extends Base {
	public static class Right extends Spin {
		public double getRight(double robotAndGunTurnRateRadians) {
			return Double.POSITIVE_INFINITY;
		}
	}
}
