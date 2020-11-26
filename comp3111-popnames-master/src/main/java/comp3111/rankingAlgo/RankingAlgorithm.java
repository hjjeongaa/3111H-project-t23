package comp3111.rankingAlgo;

import org.apache.commons.csv.*;
import edu.duke.*;
import java.util.*;
import java.lang.Math;

/**
 * super class for all algorithms
 * @author Yuxi Sun
 * v 1.1
 */
public abstract class RankingAlgorithm {

	public abstract int getRank(); //getting rank of current Entry
	public abstract int getSize(); // getting size of population
	public abstract boolean isIterative();//checks which type of object said instance is.
	public abstract boolean addEntry(int freq);//User for getting Ranks in Iterators, should be used with getRank()
	public abstract String getMethodAbbreviated();//get the abbreviated name of the method
	public abstract String getMethod();//get the full name of the method
	@Override
	/**
	 * outputs ranking method with the corresponding rank in string form
	 */
	public String toString() {
		return getMethod() + "\t| The rank is " + getRank();
	}
}

