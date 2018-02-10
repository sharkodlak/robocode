package sharkodlak.robocode.misc;

public class RobotStatus extends Position {
	private final double energy, heading, velocity;
	private final long time;

	public RobotStatus(double x, double y, double heading, double velocity, double energy, long time) {
		super(x, y);
		this.heading = heading;
		this.velocity = velocity;
		this.energy = energy;
		this.time = time;
	}

	public double getEnergy() {
		return energy;
	}

	public double getHeading() {
		return heading;
	}

	public double getVelocity() {
		return velocity;
	}

	public long getTime() {
		return time;
	}
}
