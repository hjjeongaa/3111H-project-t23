package comp3111.popnames;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class SoulmateNameTest {
	
	@Test
	public void testConstructor() {
		GlobalSettings.setCountry("usa");
		SoulmateName tester1 = new SoulmateName("John" , "M", 1880, "F", -1, "or", "usa", "human");
		SoulmateName tester2 = new SoulmateName("Apple" , "F", 1880, "F", 1, "or", "usa", "human");
		SoulmateName tester3 = new SoulmateName("Blegh" , "M", 1956, "F", 0, "or", "usa", "human");
		SoulmateName tester4 = new SoulmateName("Macy" , "F", 2019, "F", -1, "or", "usa", "human");
		SoulmateName tester5 = new SoulmateName("Emma" , "F", 2019, "F", 1, "or", "usa", "human");
		SoulmateName tester6 = new SoulmateName("John" , "M", 1880, "F", -1, "ld", "usa", "human");
		SoulmateName tester7 = new SoulmateName("Apple" , "F", 1880, "F", 1, "pyc", "usa", "human");
		SoulmateName tester8 = new SoulmateName("Blegh" , "M", 1956, "F", 0, "chance", "usa", "human");
		SoulmateName tester9 = new SoulmateName("Macy" , "F", 2003, "F", -1, "or", "usa", "human");
		SoulmateName tester10 = new SoulmateName("Emma" , "F", 1900, "F", 1, "or", "usa", "human");
	}
	
	private boolean in(String search, List<String> source) {
		for(String contender : source) {
			if(contender.equals(search)) return true;
		}
		return false;
	}
	
	@Test
	public void testGetFinalNames() {
		SoulmateName tester = new SoulmateName("Ryder" , "M", 2000, "F", 1, "or", "usa", "human");
		List<String> finals = tester.getFinalNames();
		assertTrue(in("Savana", finals));
		assertTrue(in("Rayden", finals));
		assertTrue(in("Emily", finals));
		assertTrue(in("Madison", finals));
		assertTrue(in("Hannah", finals));
	}
}
