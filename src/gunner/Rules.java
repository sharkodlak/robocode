package sharkodlak.robocode.gunner;

import sharkodlak.geometry.Round;

public class Rules {
	public static boolean isEnoughTimeToAim(double gunHeat, double gunCoolingRate) {
		return getGunCoolingTime(gunHeat, gunCoolingRate) > getHalfRoundTurnTime();
	}

	public static double getGunCoolingTime(double gunHeat, double gunCoolingRate) {
		return gunHeat / gunCoolingRate;
	}

	public static double getHalfRoundTurnTime() {
		return Round.HALF_ROUND / robocode.Rules.GUN_TURN_RATE_RADIANS;
	}
}
