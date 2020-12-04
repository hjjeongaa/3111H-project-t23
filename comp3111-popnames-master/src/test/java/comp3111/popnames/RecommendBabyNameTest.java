package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import org.apache.commons.lang3.tuple.Triple;

public class RecommendBabyNameTest {
	
    @Test 
    public void genericMaleTest() {
    	//TODO:PYTHONIZE!
    	RecommendBabyName a = new RecommendBabyName("Cameron", "Suzanne", 1964, 1965, 2019, "M", "usa", "human");
    	List<Triple<String,Integer,Integer>> list = a.getBabyNamesList();
		assertTrue(list.get(0).getLeft().equals("Cane")
				&& (list.get(0).getRight()*5+list.get(0).getMiddle()) == 4);
    }
    
    @Test 
    public void genericFemaleTest() {
    	//TODO:PYTHONIZE!
    	RecommendBabyName a = new RecommendBabyName("Carl", "Alice", 1996, 1980, 2011, "F", "usa", "human");
    	List<Triple<String,Integer,Integer>> list = a.getBabyNamesList();
		assertTrue(list.get(0).getLeft().equals("Claire")
				&& (list.get(0).getRight()*5+list.get(0).getMiddle()) == 3);
    }
    
    @Test 
    public void longNameTest() {
    	//TODO:PYTHONIZE!
    	RecommendBabyName a = new RecommendBabyName("Bartholomew", "Emmanuella", 1950, 1951, 2019, "M", "usa", "human");
    	List<Triple<String,Integer,Integer>> list = a.getBabyNamesList();
		assertTrue(list.get(0).getLeft().equals("Marcello")
				&& (list.get(0).getRight()*5+list.get(0).getMiddle()) == 11);
    }
    
    @Test 
    public void littleNamesTest() {
    	//TODO:PYTHONIZE!
    	RecommendBabyName a = new RecommendBabyName("Abcdefghijkl", "Mnopqrstuvwx", 2001, 2000, 2019, "F", "usa", "human");
    	List<Triple<String,Integer,Integer>> list = a.getBabyNamesList();
		assertTrue(list.get(0).getLeft().equals("Abigail")
				&& (list.get(0).getRight()*5+list.get(0).getMiddle()) == 32);
    }
    
    @Test 
    public void extremeLittleNamesTest() {
    	//TODO:PYTHONIZE!
    	RecommendBabyName a = new RecommendBabyName("Abcdefghijklm", "Nopqrstuvwxyz", 2001, 2000, 2019, "M", "usa", "human");
    	List<Triple<String,Integer,Integer>> list = a.getBabyNamesList();
		assertTrue(list.get(0).getLeft().equals("Abdirahim")
				&& (list.get(0).getRight()*5+list.get(0).getMiddle()) == 32);
    }
    
    @Test 
    public void noNamesTest() {
    	//TODO:PYTHONIZE!
    	RecommendBabyName a = new RecommendBabyName("Abcdefghijklmnopqrstuvwxyz", "Zyxwvutsrqponmlkjihgfedcba", 2001, 2000, 2019, "M", "usa", "human");
    	List<Triple<String,Integer,Integer>> list = a.getBabyNamesList();
		assertTrue(list.isEmpty());
    }
}
