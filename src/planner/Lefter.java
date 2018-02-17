package sharkodlak.robocode.planner;

public class Lefter implements Planner {
	private Planner instance;

	public Lefter(Planner instance) {
		this.instance = instance;
	}

	public double getAhead() {
		return instance.getAhead();
	}

	public double getRight() {
		return -instance.getRight();
	}

	public Planner setRobotStatus(RobotStatus robotStatus) {
		instance.setRobotStatus(robotStatus);
		return this;
	}

	public Planner setDestination(double x, double y) {
		instance.setDestination(x, y);
		return this;
	}
}
