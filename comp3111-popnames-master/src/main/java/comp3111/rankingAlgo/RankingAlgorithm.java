package comp3111.rankingAlgo;

import org.apache.commons.csv.*;
import edu.duke.*;
import java.util.*;
import java.lang.Math;

/**
 * super class for all algorithms
 * @author Marty Sun
 *
 */
public abstract class RankingAlgorithm {

	public abstract int getRank();
	public abstract int getSize(); // getting size of population
	public abstract String getMethod();
	@Override
	/**
	 * outputs ranking method with the corresponding rank in string form
	 */
	public String toString() {
		return getMethod() + "\t| The rank is " + getRank();
	}
}

