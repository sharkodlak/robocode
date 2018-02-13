package sharkodlak.robocode.gunner;

import robocode.*;
import robocode.util.*;

public class Aim extends Base {
	public double getRight(double robotRightTurn) {
		double nextGunHeading = Utils.normalAbsoluteAngle(robotStatus.getGunHeadingRadians() + robotRightTurn);
		double gunBearing = Utils.normalRelativeAngle(getTargetAngle() - nextGunHeading);
		aimed = Math.abs(gunBearing) < robocode.Rules.GUN_TURN_RATE_RADIANS;
		return gunBearing;
	}

	public static class Probability extends Aim {
		double chanceToHit;

		public double getBulletPower() {
			return robocode.Rules.MAX_BULLET_POWER * chanceToHit;
		}

		public Probability setChanceToHit(double chanceToHit) {
			if (chanceToHit < 0 || chanceToHit > 1) {
				throw new IllegalArgumentException(String.format("Probability out of range. Excepted <0, 1> got %.2f", chanceToHit));
			}
			this.chanceToHit = chanceToHit;
			return this;
		}
	}

	public static class Proximity extends Probability {
		double maxFiringDistance;
		double minFullPowerDistance = sharkodlak.robocode.misc.Rules.ROBOT_WIDTH / 2 / robocode.Rules.MAX_VELOCITY * MIN_BULLET_SPEED;

		public Proximity(double maxFiringDistance, double minFullPowerDistance) {
			this(maxFiringDistance);
			this.minFullPowerDistance = minFullPowerDistance;
		}

		public Proximity(double maxFiringDistance) {
			this.maxFiringDistance = maxFiringDistance;
		}

		public double getBulletPower() {
			double targetDistance = getTargetDistance();
			double variableDistance = maxFiringDistance - minFullPowerDistance;
			double chanceToHit = 1 - (targetDistance - minFullPowerDistance) / variableDistance;
			super.setChanceToHit(sharkodlak.robocode.misc.Rules.normalizeProbability(chanceToHit));
			return super.getBulletPower();
		}

		private double getTargetDistance() {
			return Math.hypot(targetPositionX - robotStatus.getX(), targetPositionY - robotStatus.getY());
		}
	}
}
