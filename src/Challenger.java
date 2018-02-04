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
	protected Planner aheadRightPlanner, rightPlanner;
	protected Gunner aimGunner, spinGunner;
	//protected Navigator navigator;
	protected sharkodlak.robocode.radar.Operator aimRadarOperator, spinRadarOperator;

	protected void init() {
		setColors(Color.white, Color.white, Color.white, Color.white, Color.white);
		aheadRightPlanner = new FullSpeed.Ahead.Right();
		rightPlanner = new FullSpeed.Right();
		aimGunner = new sharkodlak.robocode.gunner.Aim();
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
		Planner planner = aheadRightPlanner;
		if (evasiveBearing > 0) {
			planner = rightPlanner;
		}
		return planner;
	}

	protected Operator getRadarOperator() {
		return isEnoughTimeToAimRadar() ? spinRadarOperator : aimRadarOperator;
	}
}
