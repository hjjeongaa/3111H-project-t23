package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;
import java.text.DecimalFormat;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class PopularityOfNameTest {
	
    @Test 
    public void genericMaleTest() {
    	PopularityOfName a = new PopularityOfName(2014,2016,"Jack","M","usa","human");
    	List<Triple<Integer,Integer,Pair<Integer,Double>>> list = a.getPopularityList();
    	DecimalFormat df = new DecimalFormat("#.##");
		assertTrue(list.get(0).getLeft() == 41
				&& list.get(0).getMiddle() == 8685
				&& df.format(list.get(0).getRight().getRight()).equals(df.format(0.45677))
				
				&& list.get(1).getLeft() == 40
				&& list.get(1).getMiddle() == 8505
				&& df.format(list.get(1).getRight().getRight()).equals(df.format(0.44457))
				
				&& list.get(2).getLeft() == 38
				&& list.get(2).getMiddle() == 8418
				&& df.format(list.get(2).getRight().getRight()).equals(df.format(0.44458)));
    }
    
    @Test 
    public void genericFemaleTest() {
    	PopularityOfName a = new PopularityOfName(1880,1900,"Samantha","F","usa","human");
    	List<Triple<Integer,Integer,Pair<Integer,Double>>> list = a.getPopularityList();
    	DecimalFormat df = new DecimalFormat("#.##");
		assertTrue(list.get(0).getLeft() == 365
				&& list.get(0).getMiddle() == 21
				&& df.format(list.get(0).getRight().getRight()).equals(df.format(0.023))
				
				&& list.get(20).getLeft() == 701
				&& list.get(20).getMiddle() == 27
				&& df.format(list.get(20).getRight().getRight()).equals(df.format(0.009)));
    }
    
    @Test 
    public void edgeTest() {
    	PopularityOfName a = new PopularityOfName(2019,2019,"Alice","F","usa","human");
    	List<Triple<Integer,Integer,Pair<Integer,Double>>> list = a.getPopularityList();
    	DecimalFormat df = new DecimalFormat("#.##");
		assertTrue(list.get(0).getLeft() == 73
				&& list.get(0).getMiddle() == 3527
				&& df.format(list.get(0).getRight().getRight()).equals(df.format(0.21)));
    }
    
    @Test 
    public void notFoundTest() {
    	PopularityOfName a = new PopularityOfName(2000,2019,"asdfghjkl","M","usa","human");
    	List<Triple<Integer,Integer,Pair<Integer,Double>>> list = a.getPopularityList();
    	DecimalFormat df = new DecimalFormat("#.##");
		assertTrue(list.get(0).getLeft() == 0
				&& list.get(0).getMiddle() == 0);
    }
}
