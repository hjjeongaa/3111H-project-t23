package comp3111.popnames;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TopNNames_Test {

	@Test
	public void testConstructor() {
		// TopNNames(int startYear, int endYear, String gender, int numNames, String country, String type)
		TopNNames tester1 = new TopNNames(1880, 2019, "M", 500, "usa", "human");
		TopNNames tester2 = new TopNNames(1956, 1980, "F", 5000, "usa", "human");
	}
	
	@Test
	public void testGetListOfNamesFromYear() {
		TopNNames tester = new TopNNames(1976,2000, "M", 50, "usa", "human");
		List<String> nameSet1 = tester.getListOfNamesFromYear(1976);
		List<String> nameSet2 = tester.getListOfNamesFromYear(1975);
		List<String> nameSet3 = tester.getListOfNamesFromYear(2000);
		List<String> nameSet4 = tester.getListOfNamesFromYear(2001);
		
		assertTrue(nameSet1.get(0).equals("Michael"));
		assertTrue(nameSet1.get(1).equals("Jason"));
		assertTrue(nameSet2 == null);
		assertTrue(nameSet3.get(0).equals("Jacob"));
		assertTrue(nameSet3.get(1).equals("Michael"));
		assertTrue(nameSet4 == null);
	}
	
	@Test
	public void testGetListOfFrequenciesFromYear() {
		TopNNames tester = new TopNNames(1976,2000, "M", 50, "usa", "human");
		List<Integer> nameSet1 = tester.getListOfFrequenciesFromYear(1976);
		List<Integer> nameSet2 = tester.getListOfFrequenciesFromYear(1975);
		List<Integer> nameSet3 = tester.getListOfFrequenciesFromYear(2000);
		List<Integer> nameSet4 = tester.getListOfFrequenciesFromYear(2001);
		
		assertTrue(nameSet1.get(0) == 66971);
		assertTrue(nameSet1.get(1) == 52686);
		assertTrue(nameSet2 == null);
		assertTrue(nameSet3.get(0) == 34465);
		assertTrue(nameSet3.get(1) == 32025);
		assertTrue(nameSet4 == null);
	}
	
	@Test
	public void testGetters() {
		TopNNames tester = new TopNNames(1911,1967, "M", 50, "usa", "human");
		int sY = tester.getStartYear();
		int eY = tester.getEndYear();
		assertTrue(sY == 1911);
		assertTrue(eY == 1967);
	}
	
}
