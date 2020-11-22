package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RankingAlgorithmTest {

	//src
	@Test
	public void scrCreationtest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("scr","Jabez","M",2000,"usa","human","standard");
		System.out.println(algo);
		assertTrue(algo.getMethod().equals("Standard Competition Ranking"));
	}
	public void scrGetRanktest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("scr","Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 9102);
	}
	public void scrGetSizetest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("scr","Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
	
	//dr
	@Test
	public void drCreationtest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("dr","Jabez","M",2000,"usa","human","standard");
		System.out.println(algo);
		assertTrue(algo.getMethod().equals("Dense Ranking"));
	}
	@Test
	public void drGetRanktest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("dr","Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 822);
	}
	public void drGetSizetest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("dr","Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
	
	//mcr
	@Test
	public void mcrCreationtest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("mcr","Jabez","M",2000,"usa","human","standard");
		System.out.println(algo);
		assertTrue(algo.getMethod().equals("Modified Competition Ranking"));
	}
	@Test
	public void mcrGetRanktest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("mcr","Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 10341);
	}
	public void mcrGetSizetest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("mcr","Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
	//or
	@Test
	public void orCreationtest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("or","Jabez","M",2000,"usa","human","standard");
		System.out.println(algo);
		assertTrue(algo.getMethod().equals("Ordinal Ranking"));
	}
	@Test
	public void orGetRanktest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("or","Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 9554);
	}
	@Test
	public void orGetSizetest() {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm("or","Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
}
