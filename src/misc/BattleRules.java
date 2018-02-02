package sharkodlak.robocode.misc;

public class BattleRules {
	private final int battleFieldHeight;
	private final int battleFieldWidth;
	private final double gunCoolingRate;
	private final int numRounds;
	private final int sentryBorderSize;

	public BattleRules(int battleFieldWidth, int battleFieldHeight, int numRounds, double gunCoolingRate, int sentryBorderSize) {
		this.battleFieldWidth = battleFieldWidth;
		this.battleFieldHeight = battleFieldHeight;
		this.numRounds = numRounds;
		this.gunCoolingRate = gunCoolingRate;
		this.sentryBorderSize = sentryBorderSize;
	}

	public int getBattleFieldHeight() {
		return battleFieldHeight;
	}

	public int getBattlefieldWidth() {
		return battleFieldWidth;
	}

	public double getGunCoolingRate() {
		return gunCoolingRate;
	}

	public int getNumRounds() {
		return numRounds;
	}

	public int getSentryBorderSize() {
		return sentryBorderSize;
	}
}
