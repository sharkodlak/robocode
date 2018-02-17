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
	private static double FIRING_RANGE_MAX = 300, FIRING_RANGE_MIN = 150, CLOSING_RANGE_MAX = 350, CLOSING_RANGE_MIN = 150;
	protected Planner aheadRightPlanner, rightPlanner;
	protected Positioner.Closer closerPlanner;
	protected Gunner aimGunner, spinGunner;
	//protected Navigator navigator;
	protected sharkodlak.robocode.radar.Operator aimRadarOperator, spinRadarOperator;

	protected void init() {
		setColors(Color.white, Color.white, Color.white, Color.white, Color.white);
		aheadRightPlanner = new FullSpeed.Ahead.Right();
		closerPlanner = new Positioner.Closer(CLOSING_RANGE_MAX, CLOSING_RANGE_MIN);
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
		Planner activePlanner = aheadRightPlanner;
		if (evasiveBearing > 0) {
			activePlanner = rightPlanner;
		} else if (closerPlanner.setRobotStatusAndDestination(robotStatus, scannedX, scannedY).isClosing()) {
			activePlanner = closerPlanner;
		}
		return activePlanner;
	}

	protected Operator getRadarOperator() {
		return isEnoughTimeToAimRadar() ? spinRadarOperator : aimRadarOperator;
	}
}
