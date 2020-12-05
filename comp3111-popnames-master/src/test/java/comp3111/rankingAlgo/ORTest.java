package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ORTest {


	//Testing Iterative settings
	@Test
	public void orIterativeConstructor() {
		OR algo = new OR(100);	
		assertTrue(algo.getRank()==1 && algo.getSize()==1);
	}
	@Test
	public void orIterativeaddEntrySameFreq() {
		OR algo = new OR(100);
		algo.addEntry(100);
		assertTrue(algo.getRank()==2 && algo.getSize()==2);
	}
	
	@Test
	public void orIterativeaddEntryIncrementTest() {
		OR algo = new OR(100);
		algo.addEntry(100);
		algo.addEntry(100);
		algo.addEntry(100);
		algo.addEntry(99);
		assertTrue(algo.getRank()==5 && algo.getSize()==5);
	}
	@Test
	public void orIterativeaddEntryDiffFreq() {
		OR algo = new OR(100);
		algo.addEntry(99);
		assertTrue(algo.getRank()==2 && algo.getSize()==2);
	}
	@Test
	public void orEntryValid() {
		OR algo = new OR(100);
		assertTrue(algo.addEntry(99));
	}
	@Test
	public void orEntryInvalid() {
		OR algo = new OR(100);
		assertFalse(algo.addEntry(101));
	}
	//Testing Non Iterative Settings
	
	@Test
	public void orGetMethod() {
		OR algo = new OR("Heather","F",2000,"usa","human","standard");
		assertTrue(algo.getMethod().equals("Ordinal Ranking"));
	}
	@Test
	public void orGetMethodAbbreviatedFoundNameTest() {
		OR algo = new OR("Heather","F",2000,"usa","human","standard");
		assertTrue(algo.getMethodAbbreviated().equals("or"));
	}
	@Test
	public void orGetRankFoundNameTestValidFemale() {
		OR algo = new OR("Heather","F",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 125);
	}
	@Test
	public void orSameFreqSameRankTest() {
		OR algo = new OR("Heather","F",2000,"usa","human","standard");
		OR algo2 = new OR("Madelyn","F",2000,"usa","human","standard");
		assertTrue(algo.getRank()+1 == algo2.getRank());
	}
	@Test
	public void orDiffFreqAfterSameRankTest() {
		OR algo = new OR("Madelyn","F",2000,"usa","human","standard");
		OR algo2 = new OR("Adriana","F",2000,"usa","human","standard");
		assertTrue(algo.getRank()+1 == algo2.getRank());
	}
	@Test
	public void orGetSizeFoundNametestMale() {
		OR algo = new OR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
	@Test
	public void orGetSizeFoundNametestFemale() {
		OR algo = new OR("Jabez","F",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 17652);
	}
	
	@Test
	public void testGetNameFromRank() {
		OR testHolder = new OR("Jabez","F",2000,"usa","human","standard");
		List<String> tester = testHolder.getNameFromRank(12, "F", 1956, "human", "usa");
		assertTrue(tester.size() == 1);
		assertTrue(tester.get(0).equals("Pamela"));
	}

}
