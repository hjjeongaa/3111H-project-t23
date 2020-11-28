package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RankResolverTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	//testing the accessors
	@Test
	public void getResolutionMethodsValid1() {
		rankResolver.getResolutionMethods();
		assertTrue(rankResolver.getResolutionMethods().contains("standard"));
	}
	@Test
	public void getResolutionMethodsValid2() {
		rankResolver.getResolutionMethods();
		assertTrue(rankResolver.getResolutionMethods().contains("ld"));
	}
	@Test
	public void getResolutionMethodsInvalid() {
		rankResolver.getResolutionMethods();
		assertFalse(rankResolver.getResolutionMethods().contains("GEORGIAFRAUD"));
	}
	//testing ld
	@Test
	public void getResolutionLD() {
		rankResolver resolution = new rankResolver("or","Lol","F",2000,"usa","human",0,"ld");//should resolve to Sol, position 3012
		assertTrue(resolution.getRank()==3012);
	}
}
