package sharkodlak.robocode.radar;

import robocode.*;

public class Rules {
	public static boolean isEnoughTimeToAim(double gunHeat, double gunCoolingRate) {
		return getGunCoolingTime(gunHeat, gunCoolingRate) > getHalfRoundTurnTime();
	}

	public static double getGunCoolingTime(double gunHeat, double gunCoolingRate) {
		return gunHeat / gunCoolingRate;
	}

	public static double getHalfRoundTurnTime() {
		return sharkodlak.robocode.misc.Round.HALF_ROUND / robocode.Rules.RADAR_TURN_RATE_RADIANS;
	}
}
