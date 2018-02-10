package sharkodlak.robocode.misc;

import java.util.*;

public class RobotsStatuses implements Iterable<Map.Entry<String, List<RobotStatus>>> {
	private List<String> robotNames;
	private List<List<RobotStatus>> robotsStatuses;
	private java.io.PrintStream out;

	public RobotsStatuses(int numberOfOtherRobots, java.io.PrintStream out) {
		robotNames = new ArrayList<>(numberOfOtherRobots);
		robotsStatuses = new ArrayList<>(numberOfOtherRobots);
		this.out = out;
	}

	public RobotStatus getStatus(String robotName) {
		List<RobotStatus> robotStatuses = getStatuses(robotName);
		return robotStatuses.get(robotStatuses.size() - 1);
	}

	public List<RobotStatus> getStatuses(String robotName) {
		int robotIndex = robotNames.indexOf(robotName);
		return robotsStatuses.get(robotIndex);
	}

	public Iterator<Map.Entry<String, List<RobotStatus>>> iterator() {
		return new RobotsStatusesIterator(robotNames);
	}

	private class RobotsStatusesIterator implements Iterator<Map.Entry<String, List<RobotStatus>>> {
		Iterator<String> robotNamesIterator;

		public RobotsStatusesIterator(List<String> robotNames) {
			robotNamesIterator = robotNames.iterator();
		}

		public boolean hasNext() {
			return robotNamesIterator.hasNext();
		}

		public Map.Entry<String, List<RobotStatus>> next() {
			String robotName = robotNamesIterator.next();
			List<RobotStatus> robotStatuses = RobotsStatuses.this.getStatuses(robotName);
			Map.Entry<String, List<RobotStatus>> entry = new AbstractMap.SimpleImmutableEntry<>(robotName, robotStatuses);
			return entry;
		}
	}

	public void setDeath(String robotName, long time) {
		setStatus(robotName, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, time);
	}

	public void setStatus(String robotName, double x, double y, double heading, double velocity, double energy, long time) {
		int robotIndex = maintainRobotIndex(robotName);
		RobotStatus robotStatus = new RobotStatus(x, y, heading, velocity, energy, time);
		robotsStatuses.get(robotIndex)
			.add(robotStatus);
	}

	private synchronized int maintainRobotIndex(String robotName) {
		int robotIndex = robotNames.indexOf(robotName);
		if (robotIndex == -1) {
			robotNames.add(robotName);
			robotIndex = robotNames.indexOf(robotName);
			robotsStatuses.add(robotIndex, new ArrayList<RobotStatus>());
		}
		return robotIndex;
	}
}
