package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;
import java.text.DecimalFormat;
import java.util.List;
import org.apache.commons.lang3.tuple.Triple;

public class PopularityOfNameTest {
	
    @Test 
    public void genericMaleTest() {
    	PopularityOfName a = new PopularityOfName(2014,2016,"Jack","M","usa","human");
    	List<Triple<Integer,Integer,Double>> list = a.getPopularityList();
    	DecimalFormat df = new DecimalFormat("#.##");
		assertTrue(list.get(0).getLeft() == 41
				&& list.get(0).getMiddle() == 13977
				&& df.format(list.get(0).getRight()).equals(df.format(99.71))
				
				&& list.get(2).getLeft() == 38
				&& list.get(2).getMiddle() == 14181
				&& df.format(list.get(2).getRight()).equals(df.format(99.74)));
    }
    
    @Test 
    public void genericFemaleTest() {
    	PopularityOfName a = new PopularityOfName(1880,1900,"Samantha","F","usa","human");
    	List<Triple<Integer,Integer,Double>> list = a.getPopularityList();
    	DecimalFormat df = new DecimalFormat("#.##");
		assertTrue(list.get(0).getLeft() == 365
				&& list.get(0).getMiddle() == 942
				&& df.format(list.get(0).getRight()).equals(df.format(61.36))
				
				&& list.get(20).getLeft() == 701
				&& list.get(20).getMiddle() == 2225
				&& df.format(list.get(20).getRight()).equals(df.format(68.54)));
    }
    
    @Test 
    public void edgeTest() {
    	PopularityOfName a = new PopularityOfName(2019,2019,"Alice","F","usa","human");
    	List<Triple<Integer,Integer,Double>> list = a.getPopularityList();
    	DecimalFormat df = new DecimalFormat("#.##");
		assertTrue(list.get(0).getLeft() == 73
				&& list.get(0).getMiddle() == 17905
				&& df.format(list.get(0).getRight()).equals(df.format(99.6)));
    }
    
    @Test 
    public void notFoundTest() {
    	PopularityOfName a = new PopularityOfName(2000,2019,"asdfghjkl","M","usa","human");
    	List<Triple<Integer,Integer,Double>> list = a.getPopularityList();
    	DecimalFormat df = new DecimalFormat("#.##");
		assertTrue(list.get(0).getLeft() == 0
				&& list.get(0).getMiddle() == 12111);
    }
}
