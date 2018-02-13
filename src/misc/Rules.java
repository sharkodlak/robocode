package sharkodlak.robocode.misc;

public interface Rules {
	public static final double ROBOT_WIDTH = 36;
	public static final double ROBOT_HEIGHT = 36;

	public static double normalizeProbability(double probability) {
		if (probability < 0) {
			return 0;
		} else if (probability > 1) {
			return 1;
		}
		return probability;
	}
}
