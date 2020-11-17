package comp3111.rankingAlgo;

import edu.duke.*;
import java.util.*;
/**
 * Class holds different methods of resolving unfound name in rank
 * @author Yuxi Sun
 *
 */
public class rankResolver {
	private int rank;
	public int getRank() {return this.rank;};
	public rankResolver(String resolution, int size) {
		if(resolution.equals("standard")) {
			this.rank = size+1;
		}
	}
}
