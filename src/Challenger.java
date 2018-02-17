package sharkodlak.robocode;

import java.awt.Color;
import robocode.*;
import sharkodlak.robocode.gunner.*;
import sharkodlak.robocode.misc.*;
import sharkodlak.robocode.planner.*;
import sharkodlak.robocode.radar.*;

/** Challenger - a robot by Pavel Štětina
 */
public class Challenger extends Base {
	private static double FIRING_RANGE_MAX = 300, FIRING_RANGE_MIN = 150, CLOSING_RANGE_MAX = 300, CLOSING_RANGE_MIN = 150;
	protected Planner activePlanner, aheadRightPlanner, positionerPlanner, rightPlanner;
	protected Gunner aimGunner, spinGunner;
	//protected Navigator navigator;
	protected sharkodlak.robocode.radar.Operator aimRadarOperator, spinRadarOperator;

	protected void init() {
		setColors(Color.white, Color.white, Color.white, Color.white, Color.white);
		aheadRightPlanner = new FullSpeed.Ahead.Right();
		positionerPlanner = new Positioner();
		rightPlanner = new FullSpeed.Right();
		aimGunner = new sharkodlak.robocode.gunner.Aim.Proximity(FIRING_RANGE_MAX, FIRING_RANGE_MIN);
		spinGunner = new sharkodlak.robocode.gunner.Spin.Right();
		aimRadarOperator = new sharkodlak.robocode.radar.Aim();
		spinRadarOperator = new sharkodlak.robocode.radar.Spin.Right();
		//out.println("STATUS time:" + robotStatus.getTime() + ", gunHeat: " + robotStatus.getGunHeat());
	}

	protected void mainLoop() {
		// intentionally empty
	}

	protected Gunner getGunner() {
		return isEnoughTimeToAimGun() ? spinGunner : aimGunner;
	}

	protected Planner getPlanner() {
		double targetDistance = Position.getTargetDistance(robotStatus, scannedX, scannedY);
		if (evasiveBearing > 0) {
			activePlanner = rightPlanner;
		} else if (targetDistance > CLOSING_RANGE_MAX
			|| activePlanner == positionerPlanner && targetDistance > CLOSING_RANGE_MIN
		) {
			activePlanner = positionerPlanner;
		} else {
			activePlanner = aheadRightPlanner;
		}
		return activePlanner;
	}

	protected Operator getRadarOperator() {
		return isEnoughTimeToAimRadar() ? spinRadarOperator : aimRadarOperator;
	}
}
