package sharkodlak.robocode.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sharkodlak.robocode.tests.trigonometry.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	PositionTest.class,
	VectorTest.class,
	PositionedVectorTest.class
})
public class AllTests {
	// this class is empty
}
