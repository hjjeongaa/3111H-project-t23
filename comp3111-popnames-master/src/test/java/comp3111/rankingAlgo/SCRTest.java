package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SCRTest {

	//Testing Iterative settings
	@Test
	public void scrIterativeConstructor() {
		SCR algo = new SCR(100);	
		assertTrue(algo.getRank()==1 && algo.getSize()==1);
	}
	@Test
	public void scrIterativeaddEntrySameFreq() {
		SCR algo = new SCR(100);
		algo.addEntry(100);
		assertTrue(algo.getRank()==1 && algo.getSize()==2);
	}
	
	@Test
	public void scrIterativeaddEntryIncrementTest() {
		SCR algo = new SCR(100);
		algo.addEntry(100);
		algo.addEntry(100);
		algo.addEntry(100);
		algo.addEntry(99);
		assertTrue(algo.getRank()==5 && algo.getSize()==5);
	}
	@Test
	public void scrIterativeaddEntryDiffFreq() {
		SCR algo = new SCR(100);
		algo.addEntry(99);
		assertTrue(algo.getRank()==2 && algo.getSize()==2);
	}
	@Test
	public void scrEntryValid() {
		SCR algo = new SCR(100);
		assertTrue(algo.addEntry(99));
	}
	@Test
	public void scrEntryInvalid() {
		SCR algo = new SCR(100);
		assertFalse(algo.addEntry(101));
	}
	//Testing Non Iterative Settings
	
	@Test
	public void scrGetMethod() {
		SCR algo = new SCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getMethod().equals("Standard Competition Ranking"));
	}
	@Test
	public void scrGetMethodAbbreviatedFoundNameTest() {
		SCR algo = new SCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getMethodAbbreviated().equals("scr"));
	}
	@Test
	public void scrGetRankFoundNameTestValidMale() {
		SCR algo = new SCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 9102);
	}
	
	@Test
	public void scrGetSizeFoundNametestMale() {
		SCR algo = new SCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
	@Test
	public void scrGetSizeFoundNametestFemale() {
		SCR algo = new SCR("Jabez","F",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 17652);
	}
}
