package sharkodlak.robocode;

import java.awt.*;
import java.util.*;
import robocode.*;
import robocode.util.*;
import sharkodlak.robocode.*;
import sharkodlak.robocode.gunner.*;
//import sharkodlak.robocode.navigator.*;
import sharkodlak.robocode.misc.*;
import sharkodlak.robocode.planner.*;
import sharkodlak.robocode.radar.*;

/** Base - a foundation for robots by Pavel Štětina
 */
abstract public class Base extends AdvancedRobot {
	protected boolean aimed = false;
	protected double evasiveBearing = Double.NaN;
	protected sharkodlak.robocode.misc.BattleRules battleRules;
	protected RobotStatus robotStatus;
	protected java.util.List<Map.Entry<String, java.util.List<Position>>> scans = new ArrayList<>();
	private java.util.List<Position> nullArrayList = new ArrayList<>();

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
			double scannedX = Double.NaN, scannedY = Double.NaN;
			try {
				java.util.List<Position> positions = scans.get(0).getValue();
				Position position = positions.get(positions.size() - 1);
				scannedX = position.getX();
				scannedY = position.getY();
			} catch (IndexOutOfBoundsException e) {
				// don't do anythink
				out.println(e);
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

	protected RobotStatus getRobotStatus() {
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
		try {
			java.util.List<Position> positions = scans.get(0).getValue();
			Position position = positions.get(positions.size() - 1);
			double scannedX = position.getX();
			double scannedY = position.getY();
			g.drawRect((int) Math.round(scannedX - 18), (int) Math.round(scannedY - 18), 36, 36);
		} catch (IndexOutOfBoundsException e) {
			// don't do anythink
		}
		//out.println("aimed.onPaint: " + (aimed ? "true" : "false"));
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

	public void onScannedRobot(ScannedRobotEvent event) {
		double targetAngle = Utils.normalAbsoluteAngle(getHeadingRadians() + event.getBearingRadians());
		double distance = event.getDistance();
		String robotName = event.getName();
		boolean inScans = false;
		Position position = new Position(
			getX() + Math.sin(targetAngle) * distance,
			getY() + Math.cos(targetAngle) * distance
		);
		java.util.List<Position> positionList = nullArrayList;
		for (Map.Entry<String, java.util.List<Position>> entry : scans) {
			if (entry.getKey().equals(robotName)) {
				inScans = true;
				positionList = entry.getValue();
				break;
			}
		}
		if (!inScans) {
			positionList = new ArrayList<>();
			Map.Entry<String, java.util.List<Position>> robotPositions = new AbstractMap.SimpleImmutableEntry<>(robotName, positionList);
			scans.add(robotPositions);
		}
		positionList.add(position);
	}

	public void onStatus(StatusEvent event) {
		robotStatus = event.getStatus();
	}
}
