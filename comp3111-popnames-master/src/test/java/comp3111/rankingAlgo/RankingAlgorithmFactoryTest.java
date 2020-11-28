package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RankingAlgorithmFactoryTest {
	Vector<String> iterative; 
	Vector<String> nonIterative;
	@Before
	public void setUp() throws Exception {
		iterative = RankingAlgorithmFactory.getIterativeMethods();
		nonIterative = RankingAlgorithmFactory.getNonIterativeMethods();
	}
	@Test
	public void checkGetIterative() {
		iterative = RankingAlgorithmFactory.getIterativeMethods();
		assertFalse(iterative==null);
	}
	@Test
	public void checkGetNonIterative() {
		nonIterative = RankingAlgorithmFactory.getNonIterativeMethods();
		assertFalse(nonIterative==null);
	}
	@After
	public void tearDown() throws Exception {
		
	}
	//test Iterative set
	@Test
	public void checkSRCinIterative() {
		assertTrue(iterative.contains("scr"));
	}	
	@Test
	public void checkORinIterative() {
		assertTrue(iterative.contains("or"));		
	}
	@Test
	public void checkDRinIterative() {
		assertTrue(iterative.contains("dr"));
	}
	@Test
	public void checkFalseinIterative() {
		assertFalse(iterative.contains("StopTheCount"));
	}
	//test Non Iterative set
	@Test
	public void checkSRCinNonIterative() {
		assertTrue(nonIterative.contains("scr"));		
	}	
	@Test
	public void checkMRCinNonIterative() {
		assertTrue(nonIterative.contains("mcr"));		
	}	
	@Test
	public void checkORinNonIterative() {
		assertTrue(nonIterative.contains("or"));
	}
	@Test
	public void checkDRinNonIterative() {
		assertTrue(nonIterative.contains("dr"));
	}
	@Test
	public void checkFalseinNoIterative() {
		assertFalse(nonIterative.contains("StopTheCount"));
	}
	
	//constructor testing
	@Test
	public void getRankingAlgorithmNonIterativeFail() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("StopTheCount", "Donald" ,"M",1946,"usa","human","standard");
		assertTrue(algo == null);
	}
	@Test
	public void getRankingAlgorithmIterativeFail() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("StopTheCount",  10);
		assertTrue(algo == null);
	}
	
	//getIterativeMethods
	@Test
	public void getRankingAlgorithmIterativeSCR() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("scr",  10);
		assertTrue(algo.getMethodAbbreviated() == "scr");
	}
	@Test
	public void getRankingAlgorithmIterativeDR() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("dr",  10);
		assertTrue(algo.getMethodAbbreviated() == "dr");
	}
	@Test
	public void getRankingAlgorithmIterativeOR() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("or",  10);
		assertTrue(algo.getMethodAbbreviated() == "or");
	}
	
}
