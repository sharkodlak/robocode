package sharkodlak.robocode.misc;

import java.util.*;

public class RobotsPositions {
	private List<Map.Entry<String, List<Position>>> robotsPositions = new ArrayList<>();

	public RobotsPositions add(String robotName, double x, double y) {
		List<Position> positions;
		try {
			positions = getRobotPositions(robotName);
		} catch (NoSuchElementException e) {
			positions = new ArrayList<>();
			robotsPositions.add(new AbstractMap.SimpleImmutableEntry<>(robotName, positions));
		}
		positions.add(new Position(x, y));
		return this;
	}

	public Map.Entry<String, List<Position>> get(int index) {
		return robotsPositions.get(index);
	}

	public List<Position> getRobotPositions(String robotName) {
		for (Map.Entry<String, List<Position>> robotPositions : robotsPositions) {
			if (robotPositions.getKey().equals(robotName)) {
				return robotPositions.getValue();
			}
		}
		throw new NoSuchElementException(String.format("Robot '%s' isn't in list yet.", robotName));
	}
}
