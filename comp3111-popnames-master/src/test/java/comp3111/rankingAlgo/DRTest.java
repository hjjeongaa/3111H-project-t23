package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DRTest {

	//Testing Iterative settings
	@Test
	public void drIterativeConstructor() {
		DR algo = new DR(100);	
		assertTrue(algo.getRank()==1 && algo.getSize()==1);
	}
	@Test
	public void drIterativeaddEntrySameFreq() {
		DR algo = new DR(100);
		algo.addEntry(100);
		assertTrue(algo.getRank()==1 && algo.getSize()==2);
	}
	
	@Test
	public void drIterativeaddEntryIncrementTest() {
		DR algo = new DR(100);
		algo.addEntry(100);
		algo.addEntry(100);
		algo.addEntry(100);
		algo.addEntry(99);
		assertTrue(algo.getRank()==2 && algo.getSize()==5);
	}
	@Test
	public void drIterativeaddEntryDiffFreq() {
		DR algo = new DR(100);
		algo.addEntry(99);
		assertTrue(algo.getRank()==2 && algo.getSize()==2);
	}
	@Test
	public void drEntryValid() {
		DR algo = new DR(100);
		assertTrue(algo.addEntry(99));
	}
	@Test
	public void drEntryInvalid() {
		DR algo = new DR(100);
		assertFalse(algo.addEntry(101));
	}
	//Testing Non Iterative Settings
	
	@Test
	public void drGetMethod() {
		DR algo = new DR("Heather","F",2000,"usa","human","standard");
		assertTrue(algo.getMethod().equals("Dense Ranking"));
	}
	@Test
	public void drGetMethodAbbreviatedFoundNameTest() {
		DR algo = new DR("Heather","F",2000,"usa","human","standard");
		assertTrue(algo.getMethodAbbreviated().equals("dr"));
	}
	@Test
	public void drGetRankFoundNameTestValidFemale() {
		DR algo = new DR("Heather","F",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 125);
	}
	@Test
	public void drSameFreqSameRankTest() {
		DR algo = new DR("Heather","F",2000,"usa","human","standard");
		DR algo2 = new DR("Madelyn","F",2000,"usa","human","standard");
		assertTrue(algo.getRank() == algo2.getRank());
	}
	@Test
	public void drDiffFreqAfterSameRankTest() {
		DR algo = new DR("Madelyn","F",2000,"usa","human","standard");
		DR algo2 = new DR("Adriana","F",2000,"usa","human","standard");
		assertTrue(algo.getRank()+1 == algo2.getRank());
	}
	@Test
	public void drGetSizeFoundNametestMale() {
		DR algo = new DR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
	@Test
	public void drGetSizeFoundNametestFemale() {
		DR algo = new DR("Jabez","F",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 17652);
	}
}
