package sharkodlak.robocode;

//import java.awt.Color;
import robocode.*;
import robocode.util.*;
import sharkodlak.robocode.*;
import sharkodlak.robocode.gunner.*;
//import sharkodlak.robocode.navigator.*;
import sharkodlak.robocode.planner.*;
import sharkodlak.robocode.radar.*;

/** Base - a foundation for robots by Pavel Štětina
 */
abstract public class Base extends AdvancedRobot {
	protected sharkodlak.robocode.misc.BattleRules battleRules;
	protected RobotStatus robotStatus;
	protected double scannedX, scannedY;

	final public void run() {
		battleRules = new sharkodlak.robocode.misc.BattleRules(
			(int) this.getBattleFieldWidth(),
			(int) this.getBattleFieldHeight(),
			this.getNumRounds(),
			this.getGunCoolingRate(),
			this.getSentryBorderSize()
		);
		init();
		while (true) {
			Planner planner = getPlanner();
			setAhead(planner.getAhead());
			double robotRightTurn = sharkodlak.robocode.planner.Rules.getRight(planner.getRight(), robotStatus.getVelocity());
			setTurnRightRadians(robotRightTurn);
			Gunner gunner = getGunner()
				.setRobotStatus(robotStatus)
				.setTarget(scannedX, scannedY);
			double gunRightTurn = gunner.getRight(robotRightTurn);
			setTurnGunRightRadians(gunRightTurn);
			if (gunner.isAimed()) {
				setFire(gunner.getBulletPower());
			}
			setTurnRadarRightRadians(getRadarOperator()
				.setRobotStatus(robotStatus)
				.setTarget(scannedX, scannedY)
				.getRight(robotRightTurn + gunRightTurn)
			);
			mainLoop();
			execute();
		}
	}

	abstract protected void init();
	abstract protected void mainLoop();
	abstract protected Gunner getGunner();
	abstract protected Planner getPlanner();
	abstract protected Operator getRadarOperator();

	protected sharkodlak.robocode.misc.BattleRules getBattleRules() {
		return battleRules;
	}

	protected RobotStatus getRobotStatus() {
		return robotStatus;
	}

	public boolean isEnoughTimeToAimGun() {
		return sharkodlak.robocode.gunner.Rules.isEnoughTimeToAim(getGunHeat(), battleRules.getGunCoolingRate());
	}

	public boolean isEnoughTimeToAimRadar() {
		return sharkodlak.robocode.radar.Rules.isEnoughTimeToAim(getGunHeat(), battleRules.getGunCoolingRate());
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		double targetAngle = Utils.normalAbsoluteAngle(getHeadingRadians() + event.getBearingRadians());
		double distance = event.getDistance();
		scannedX = getX() + Math.sin(targetAngle) * distance;
		scannedY = getY() + Math.cos(targetAngle) * distance;
	}

	public void onStatus(StatusEvent e) {
		robotStatus = e.getStatus();
	}
}
