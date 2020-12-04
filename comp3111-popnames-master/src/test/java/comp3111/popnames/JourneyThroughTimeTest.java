package comp3111.popnames;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JourneyThroughTimeTest {
	
	@Test
	public void testSetValues() {
		JourneyThroughTime.setValues("Apple", "Bottom", "Jeans", "Boots", 1960, "WithThe", "Fur");
		// There is no checking that goes on in the setting function, so all retrieved values from the getter should be as I set them.
		assertTrue(JourneyThroughTime.getUserName().equals("Apple"));
		assertTrue(JourneyThroughTime.getSoulmateName().equals("Bottom"));
		assertTrue(JourneyThroughTime.getUserGender().equals("Jeans"));
		assertTrue(JourneyThroughTime.getSoulmateGender().equals("Boots"));
		assertTrue(JourneyThroughTime.getYOB() == 1960);
		assertTrue(JourneyThroughTime.getCountry().equals("WithThe"));
		assertTrue(JourneyThroughTime.getType().equals("Fur"));
	}
	
	@Test
	public void testGetFirstAppearance() {
		//(String inputName, String soulmateName, String inputGender, String soulmateGender, int YOB, String country, String type)
		JourneyThroughTime.setValues("Apple", "Bottom", "Jeans", "Boots", 1960, "WithThe", "Fur");
	}
}
