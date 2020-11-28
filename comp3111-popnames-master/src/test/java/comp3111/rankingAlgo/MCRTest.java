package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MCRTest {
	//Testing Non Iterative Settings
	//getSize tests
	@Test
	public void mcrGetMethod() {
		MCR algo = new MCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getMethod().equals("Modified Competition Ranking"));
	}
	@Test
	public void mcrGetMethodAbbreviatedFoundNameTest() {
		MCR algo = new MCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getMethodAbbreviated().equals("mcr"));
	}
	@Test
	public void mcrGetMethodSameFreqTest() {
		MCR algo = new MCR("Athena","F",2000,"usa","human","standard");
		MCR algo2 = new MCR("Esperanza","F",2000,"usa","human","standard");
		MCR algo3 = new MCR("Regina","F",2000,"usa","human","standard");
		assertTrue(algo.getRank() == algo2.getRank()&& algo.getRank() == algo3.getRank());
	}
	@Test
	public void mcrGetRankFoundNametestValidFemale() {
		MCR algo = new MCR("Athena","F",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 531);
	}
	
	@Test
	public void mcrGetSizeFoundNametestMale() {
		MCR algo = new MCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
	@Test
	public void mcrGetSizeFoundNametestFemale() {
		MCR algo = new MCR("Jabez","F",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 17652);
	}
	@Test
	public void mcrIterativeDoesNothing() {
		MCR algo = new MCR("Jabez","F",2000,"usa","human","standard");
		assertFalse(algo.isIterative());
	}
	@Test
	public void mcrAddEntryFalseTest() {
		MCR algo = new MCR("Jabez","F",2000,"usa","human","standard");
		assertFalse(algo.addEntry(-1));
	}
}
