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
	
	public abstract void addEntry(int freq);//User for getting Ranks in Iterators, should be used with getRank()
	
	public abstract List<String> getNameFromRank(int rank, String gender, int yob, String type, String country); // for getting the name of a person given the rank.
	
	public abstract String getMethod();
	@Override
	/**
	 * outputs ranking method with the corresponding rank in string form
	 */
	public String toString() {
		return getMethod() + "\t| The rank is " + getRank();
	}
}

