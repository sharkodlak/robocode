package sharkodlak.robocode.planner;

import java.lang.Math;
import robocode.*;
import sharkodlak.robocode.trigonometry.*;


public class FullSpeedAhead implements Planner {
	protected Vector planVector(double angle, double speed) {
		return new Vector(angle, speed);
	}
	
	public PositionedVector planPosition(PositionedVector positionedVector) {
		Vector currentVector = positionedVector.getVector();
		double speed = currentVector.getDistance();
		double plannedSpeed = Math.min(speed + Rules.ACCELERATION, Rules.MAX_VELOCITY);
		Vector plannedVector = planVector(currentVector.getAngle(), plannedSpeed);
		return positionedVector.getProjection(plannedVector);
	}
	
	public static class Right extends FullSpeedAhead {
		protected Vector planVector(double angle, double speed) {
			//System.out.println("angle: " + angle);
			return new Vector(angle + Rules.getTurnRateRadians(speed), speed);
		}
	}
}