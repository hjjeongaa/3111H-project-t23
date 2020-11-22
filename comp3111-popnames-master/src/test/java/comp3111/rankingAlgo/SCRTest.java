package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SCRTest {
	@Test
	public void scrGetMethodFoundNametest() {
		SCR algo = new SCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getMethod().equals("Standard Competition Ranking"));
	}
	public void scrGetRankFoundNametest() {
		SCR algo = new SCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getRank() == 9102);
	}
	public void scrGetSizeFoundNametest() {
		SCR algo = new SCR("Jabez","M",2000,"usa","human","standard");
		assertTrue(algo.getSize() == 12111);
	}
}
