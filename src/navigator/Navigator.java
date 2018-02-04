package sharkodlak.robocode.navigator;

import java.util.concurrent.*;
import robocode.*;
import sharkodlak.util.tree.*;
import sharkodlak.robocode.planner.*;
import sharkodlak.robocode.trigonometry.*;


public class Navigator {
	private double battleFieldHeight;
	private double battleFieldWidth;
	private TreeNode<PositionedVector> path;
	private TreeNode<PositionedVector> pathNextStep;
	private java.util.List<Planner> planners = new CopyOnWriteArrayList<Planner>();
	private RobotStatus robotStatus;
	private int turns = 10;


	public Navigator addPlanner(Planner planner) {
		planners.add(planner);
		return this;
	}
	
	public double getAhead() {
		double distance = getPathNextStep().getData().getVector().getDistance();
		double sign = 1;
		
		if (distance < 0) {
			sign = -1;
			distance *= sign;
		}
		
		return getAheadBrakingDistance(distance, sign);
	}
	
	private double getAheadBrakingDistance(double distance, double sign) {
		double brakingDistance = 0;
		
		while (distance > 0) {
			brakingDistance += distance;
			distance -= Rules.DECELERATION;
		}
		
		return brakingDistance * sign;
	}
	
	private TreeNode<PositionedVector> getPathNextStep() {
		if (pathNextStep == null) {
			pathNextStep = getPathNextStepEvaluation();
		}
		
		return pathNextStep;
	}
	
	private TreeNode<PositionedVector> getPathNextStepEvaluation() {
		return path.getChildAt(0);
	}
	
	public double getRight() {
		double right = getPathNextStep().getData().getVector().getAngle() - robotStatus.getHeadingRadians();
		return right;
	}
	
	private boolean isDifferentPath(PositionedVector positionedVector) {
		boolean pathIsModified = !path.getData().isSame(positionedVector);
		
		if (pathIsModified) {
			System.out.println("Position modification!");
		}
		
		return pathIsModified;
	}
	
	public Navigator plan() {
		for (Planner planner : planners) {
			PositionedVector positionedVector = path.getData();
			TreeNode<PositionedVector> pathTreeNode = path;
			System.out.println(positionedVector);
			
			for (int turn = 0; turn < turns; ++turn) {
				positionedVector = planner.planPosition(positionedVector);
				TreeNode<PositionedVector> treeNode = new MutableTreeNode<PositionedVector>();
				treeNode.setData(positionedVector);
				pathTreeNode.addChild(treeNode);
				pathTreeNode = treeNode;
				System.out.println(positionedVector);
			}
		}
		
		return this;
	}
	
	public Navigator setBattleFieldDimensions(double battleFieldWidth, double battleFieldHeight) {
		this.battleFieldWidth = battleFieldWidth;
		this.battleFieldHeight = battleFieldHeight;
		return this;
	}
	
	public Navigator setRobotStatus(RobotStatus robotStatus) {
		this.robotStatus = robotStatus;
		return setPositionWithVector(
			robotStatus.getX(),
			robotStatus.getY(),
			robotStatus.getHeadingRadians(),
			robotStatus.getVelocity()
		);
	}
	
	public Navigator setPositionWithVector(double x, double y, double heading, double velocity) {
		PositionedVector positionedVector = new PositionedVector(
			robotStatus.getX(),
			robotStatus.getY(),
			robotStatus.getHeadingRadians(),
			robotStatus.getVelocity()
		);
		return setPositionWithVector(positionedVector);
	}
	
	public Navigator setPositionWithVector(PositionedVector positionedVector) {
		if (pathNextStep != null) {
			path = pathNextStep;
			path.removeParent();
			pathNextStep = null;
		}
		
		if (path == null || isDifferentPath(positionedVector)) {
			path = new MutableTreeNode<PositionedVector>();
			path.setData(positionedVector);
		}
		
		return this;
	}
	
	public Navigator setTurns(int turns) {
		this.turns = turns;
		return this;
	}
}
