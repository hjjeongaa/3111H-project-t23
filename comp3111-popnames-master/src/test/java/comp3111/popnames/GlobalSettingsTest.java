package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;

public class GlobalSettingsTest {
	
	@Test
	public void testSetToUSAValues() {
		// Test if the singletons values are all updated after calling the setValue to usa function.
		GlobalSettings.setCountry("usa");
		String defaultCountry = GlobalSettings.getCountry();
		int defaultStartYear = GlobalSettings.getLowerBound();
		int defaultEndYear = GlobalSettings.getUpperBound();
		assertTrue(defaultCountry.equals("usa"));
		assertEquals(defaultStartYear, 1880);
		assertEquals(defaultEndYear, 2019);
	}
	
	@Test
	public void testSetToScotlandValues() {
		// Test if the singletons values are all updated after calling the setValue to scotland function.
		GlobalSettings.setCountry("scotland");
		String defaultCountry = GlobalSettings.getCountry();
		int defaultStartYear = GlobalSettings.getLowerBound();
		int defaultEndYear = GlobalSettings.getUpperBound();
		assertTrue(defaultCountry.equals("scotland"));
		assertEquals(defaultStartYear, 1974);
		assertEquals(defaultEndYear, 2018);
	}
	
	@Test
	public void testSetToNorwayValues() {
		// Test if the singletons values are all updated after calling the setValue to norway function.
		GlobalSettings.setCountry("norway");
		String defaultCountry = GlobalSettings.getCountry();
		int defaultStartYear = GlobalSettings.getLowerBound();
		int defaultEndYear = GlobalSettings.getUpperBound();
		assertTrue(defaultCountry.equals("norway"));
		assertEquals(defaultStartYear, 1945);
		assertEquals(defaultEndYear, 2019);
	}
	
	@Test
	public void testSetToNorthernIrelandValues() {
		// Test if the singletons values are all updated after calling the setValue to northernireland function.
		GlobalSettings.setCountry("NorthernIreland");
		String defaultCountry = GlobalSettings.getCountry();
		int defaultStartYear = GlobalSettings.getLowerBound();
		int defaultEndYear = GlobalSettings.getUpperBound();
		assertTrue(defaultCountry.equals("NorthernIreland"));
		assertEquals(defaultStartYear, 1997);
		assertEquals(defaultEndYear, 2018);
	}
	
	@Test
	public void testSetToFranceValues() {
		// Test if the singletons values are all updated after calling the setValue to france function.
		GlobalSettings.setCountry("France");
		String defaultCountry = GlobalSettings.getCountry();
		int defaultStartYear = GlobalSettings.getLowerBound();
		int defaultEndYear = GlobalSettings.getUpperBound();
		assertTrue(defaultCountry.equals("France"));
		assertEquals(defaultStartYear, 1900);
		assertEquals(defaultEndYear, 2019);
	}
	
	@Test
	public void testSetToEnglandAndWalesValues() {
		// Test if the singletons values are all updated after calling the setValue to englandandwales function.
		GlobalSettings.setCountry("EnglandAndWales");
		String defaultCountry = GlobalSettings.getCountry();
		int defaultStartYear = GlobalSettings.getLowerBound();
		int defaultEndYear = GlobalSettings.getUpperBound();
		assertTrue(defaultCountry.equals("EnglandAndWales"));
		assertEquals(defaultStartYear, 1996);
		assertEquals(defaultEndYear, 2016);
	}
	
	@Test
	public void testSetToBogusValues() {
		// Test if the singletons values are all updated after calling the setValue to some bogus String function.
		// What is expected to happen is the country is updated, but the old bounds remain the same.
		GlobalSettings.setCountry("usa");
		String initalCountry = GlobalSettings.getCountry();
		int initialStartYear = GlobalSettings.getLowerBound();
		int initialEndYear = GlobalSettings.getUpperBound();
		GlobalSettings.setCountry("BogusVille");
		String updatedCountry = GlobalSettings.getCountry();
		int updatedStartYear = GlobalSettings.getLowerBound();
		int updatedEndYear = GlobalSettings.getUpperBound();
		
		assertTrue(updatedCountry.equals("BogusVille"));
		assertEquals(initialStartYear, updatedStartYear);
		assertEquals(initialEndYear, updatedEndYear);
	}
}
