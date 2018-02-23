package sharkodlak.robocode.misc;

import sharkodlak.geometry.*;

public class RobotStatus extends Position {
	private final Vector headingAndVelocity;
	private final double energy;
	private final long time;

	public RobotStatus(double x, double y, double heading, double velocity, double energy, long time) {
		super(x, y);
		headingAndVelocity = new Vector(heading, velocity);
		this.energy = energy;
		this.time = time;
	}

	public double getEnergy() {
		return energy;
	}

	public double getHeading() {
		return headingAndVelocity.getAngle();
	}

	public double getVelocity() {
		return headingAndVelocity.getMagnitude();
	}

	public long getTime() {
		return time;
	}
}
