package sharkodlak.tests.trigonometry;

import org.junit.*;
import static org.junit.Assert.*;


public class PositionTest {
	@Test
	public void addTest() {
		Position base = new Position(0, 0);
		Position add = new Position(7, 9);
		Position sum = base.add(add);
		assertNotSame(add, sum);
		assertEquals(7, sum.getX());
		assertEquals(9, sum.getY());

		Position substract = new Position(-3, -11);
		sum = base.add(substract);
		assertEquals(-3, sum.getX());
		assertEquals(-11, sum.getX());
	}

	@Test
	public void getAngleTest() {
		Position start = new Position(0, 0);
		Position end = new Position(3, 3);
		Double angle = start.getAngle(end);
		assertEquals(angle, 0.25 * Math.PI);

		Position substract = new Position(-3, -11);
		sum = base.add(substract);
		assertEquals(-3, sum.getX());
		assertEquals(-11, sum.getX());
	}
}
