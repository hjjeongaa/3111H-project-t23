package comp3111.rankingAlgo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import comp3111.rankingAlgo.LD;
/**
 * Outputs and new test cases can be verified/ generated using http://www.let.rug.nl/~kleiweg/lev/ with settings 
 * indel = 1, substitution = 1, swap = 1
 * @author Yuxi Sun
 *
 */
public class LDTest {

	@Test
	public void dldTest1Valid() {
		assertTrue(LD.calculate("Jon", "Joon")==1);
	}

	@Test
	public void dldTest2Valid() {
		assertTrue(LD.calculate("Joon", "Joon")==0);
	}
	@Test
	public void dldTest3Valid() {
		assertTrue(LD.calculate("joon", "Joon")==2);
	}
	@Test
	public void dldTest4Valid() {
		assertTrue(LD.calculate("Amy", "Joon")==4);
	}
	@Test
	public void dldTest5Valid() {
		assertTrue(LD.calculate("Leeman", "Joon")==5);
	}
	@Test
	public void dldTestNonAlphaValid() {
		// Non alphabetical names should also be accepted as we do not know what language the user might be speaking or which symbols the users might use.
		assertTrue(LD.calculate("1234", "Joon")==4);
	}
}