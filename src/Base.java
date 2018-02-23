package sharkodlak.robocode;

import java.awt.Graphics2D;
import java.util.*;
import robocode.*;
import robocode.util.*;
import sharkodlak.geometry.Round;
import sharkodlak.robocode.*;
import sharkodlak.robocode.commander.*;
import sharkodlak.robocode.gunner.*;
//import sharkodlak.robocode.navigator.*;
import sharkodlak.robocode.misc.*;
import sharkodlak.robocode.planner.*;
import sharkodlak.robocode.radar.*;

/** Base - a foundation for robots by Pavel Štětina
 */
abstract public class Base extends AdvancedRobot {
	protected boolean aimed = false;
	protected Commander commander;
	protected double evasiveBearing = Double.NaN;
	protected sharkodlak.robocode.misc.BattleRules battleRules;
	protected robocode.RobotStatus robotStatus;
	protected RobotsStatuses robotsStatuses;
	double scannedX = Double.NaN, scannedY = Double.NaN;

	final public void run() {
		battleRules = new sharkodlak.robocode.misc.BattleRules(
			(int) this.getBattleFieldWidth(),
			(int) this.getBattleFieldHeight(),
			this.getNumRounds(),
			this.getGunCoolingRate(),
			this.getSentryBorderSize()
		);
		robotsStatuses = new RobotsStatuses(robotStatus.getOthers(), out);
		init();
		while (true) {
			for (Map.Entry<String, List<sharkodlak.robocode.misc.RobotStatus>> robotStatusesEntry : robotsStatuses) {
				List<sharkodlak.robocode.misc.RobotStatus> robotStatuses = robotStatusesEntry.getValue();
				sharkodlak.robocode.misc.RobotStatus robotStatus = robotStatuses.get(robotStatuses.size() - 1);
				if (robotStatus.getEnergy() >= 0) {
					scannedX = robotStatus.getX();
					scannedY = robotStatus.getY();
					break;
				}
			}
			Planner planner = getPlanner();
			setAhead(planner.getAhead());
			double robotRightTurn = sharkodlak.robocode.planner.Rules.getRight(planner.getRight(), robotStatus.getVelocity());
			setTurnRightRadians(robotRightTurn);
			Gunner gunner = getGunner()
				.setRobotStatus(robotStatus)
				.setTarget(scannedX, scannedY);
			aimed = gunner.isAimed();
			double gunRightTurn = gunner.getRight(robotRightTurn);
			setTurnGunRightRadians(gunRightTurn);
			if (aimed && getGunHeat() == 0) {
				double bulletPower = gunner.getBulletPower();
				setFire(bulletPower);
				gunner.fire(bulletPower);
			}
			setTurnRadarRightRadians(getRadarOperator()
				.setRobotStatus(robotStatus)
				.setTarget(scannedX, scannedY)
				.getRight(robotRightTurn + gunRightTurn)
			);
			mainLoop();
			execute();
			evasiveBearing = evasiveBearing > 0 ? evasiveBearing - robotRightTurn : Double.NaN;
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

	protected robocode.RobotStatus getRobotStatus() {
		return robotStatus;
	}

	public boolean isEnoughTimeToAimGun() {
		return sharkodlak.robocode.gunner.Rules.isEnoughTimeToAim(getGunHeat(), battleRules.getGunCoolingRate());
	}

	public boolean isEnoughTimeToAimRadar() {
		return sharkodlak.robocode.radar.Rules.isEnoughTimeToAim(getGunHeat(), battleRules.getGunCoolingRate());
	}

	public void onHitRobot(HitRobotEvent event) {
		evasiveBearing = Round.PERPENDICULAR + event.getBearingRadians();
	}

	public void onHitWall(HitWallEvent event) {
		evasiveBearing = Round.PERPENDICULAR + event.getBearingRadians();
	}

	public void onPaint(Graphics2D g) {
		double MAX_LINE = battleRules.getBattlefieldWidth() + battleRules.getBattleFieldHeight();
		g.setColor(java.awt.Color.RED);
		g.drawRect((int) Math.round(scannedX - 18), (int) Math.round(scannedY - 18), 36, 36);
		g.setColor(aimed ? java.awt.Color.GREEN : java.awt.Color.RED);
		g.drawLine(
			(int) getX(),
			(int) getY(),
			(int) (getX() + Math.sin(getGunHeadingRadians()) * MAX_LINE),
			(int) (getY() + Math.cos(getGunHeadingRadians()) * MAX_LINE)
		);
		g.setColor(java.awt.Color.YELLOW);
		//g.drawRect((int) Math.round(getX() - 18), (int) Math.round(getY() - 18), 36, 36);
		g.setColor(java.awt.Color.GREEN);
		//g.drawLine((int) nextX - 18, (int) nextY - 18, (int) nextX + 18, (int) nextY + 18);
		//g.drawLine((int) nextX - 18, (int) nextY + 18, (int) nextX + 18, (int) nextY - 18);
	}

	public void onRobotDeath(RobotDeathEvent event) {
		robotsStatuses.setDeath(event.getName(), event.getTime());
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		double targetAngle = Utils.normalAbsoluteAngle(getHeadingRadians() + event.getBearingRadians());
		double distance = event.getDistance();
		robotsStatuses.setStatus(
			event.getName(),
			getX() + Math.sin(targetAngle) * distance,
			getY() + Math.cos(targetAngle) * distance,
			event.getHeadingRadians(),
			event.getVelocity(),
			event.getEnergy(),
			event.getTime()
		);
	}

	public void onStatus(StatusEvent event) {
		robotStatus = event.getStatus();
	}
}
