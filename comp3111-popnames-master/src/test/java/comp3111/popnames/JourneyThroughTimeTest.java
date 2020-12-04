package comp3111.popnames;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import javafx.scene.image.Image;

public class JourneyThroughTimeTest {
	
	@Test
	public void testSetValues() {
		GlobalSettings.setCountry("usa");
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
		GlobalSettings.setCountry("usa");
		JourneyThroughTime.setValues("Justin", "Katharine", "M", "F", 1956, "usa", "human");
		assertTrue(JourneyThroughTime.getFirstAppearance("Justin", "M") == 1880);
		assertTrue(JourneyThroughTime.getFirstAppearance("dQw4w9WgXcQ", "F") == -1);
	}
	
	@Test
	public void testGetFacts() {
		GlobalSettings.setCountry("usa");
		
		JourneyThroughTime.setValues("Justin", "Katharine", "M", "F", 1956, "usa", "human");
		List<String> facts1 = JourneyThroughTime.getFacts(1945);
		List<String> facts2 = JourneyThroughTime.getFacts(2000);
		List<String> facts3 = JourneyThroughTime.getFacts(1880);
		List<String> facts4 = JourneyThroughTime.getFacts(2019);
		List<String> facts5 = JourneyThroughTime.getFacts(1776);
		List<String> facts6 = JourneyThroughTime.getFacts(2042);
		
		// Check sizes
		assertTrue(facts1.size() == 2);
		assertTrue(facts2.size() == 2);
		assertTrue(facts3.size() == 2);
		assertTrue(facts4.size() == 2);
		assertTrue(facts5.size() == 0);
		assertTrue(facts6.size() == 0);
		
		
		// Check Facts
		assertTrue(facts2.get(0).equals("A worldwide online business giant, Baidu was founded in Beijing, China"));
		assertTrue(facts2.get(1).equals("The Dow Jones Industrial Average closes at 11,722.98 (at the peak of the Dot-com bubble)"));
		assertTrue(facts3.get(0).equals("The journal Science is first published in the United States with financial backing from Thomas Edison"));
		assertTrue(facts3.get(1).equals("Cologne Cathedral is completed after construction began in 1248 632 years earlier"));
		assertTrue(facts4.get(0).equals("An unmanned demonstration flight of the new crew capable version of the SpaceX Dragon spacecraft, intended to carry American astronauts into space, achieves successful autonomous docking with the International Space Station"));
		assertTrue(facts4.get(1).equals("Scientists from the Event Horizon Telescope project announce the first ever image of a black hole, located in the centre of the M87 galaxy"));
	}
	
	@Test
	public void testGetPopulationOf() {
		GlobalSettings.setCountry("usa");
		
		List<Integer> tester1 = JourneyThroughTime.getPopulationOf("John", "M", 70);
		assertTrue(tester1.get(0) == 9655);
		assertTrue(tester1.get(1) == 8769 + 9655);
		
		List<Integer> tester2 = JourneyThroughTime.getPopulationOf("AnotherBogusNameLikeSoap", "M", 70);
		assertTrue(tester2.get(0) == 0);
		assertTrue(tester2.get(1) == 0);
	}
	
	@Test
	public void testGetImage() throws FileNotFoundException {
		GlobalSettings.setCountry("usa");
		
		Image tester1 = JourneyThroughTime.getImage(1900);
		assertTrue((int)(tester1.getHeight()) == 627);
		
		Image tester2 = JourneyThroughTime.getImage(1776);
		assertTrue(tester2 == null);
	}
}
