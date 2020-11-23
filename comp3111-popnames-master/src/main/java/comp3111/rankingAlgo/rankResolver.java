package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;
import comp3111.popnames.Person;
import org.apache.commons.csv.*;
import edu.duke.*;
import java.util.*;
/**
 * Class holds different methods of resolving not found name in rank
 * 
 * @author Yuxi Sun
 * v 1.0
 */
public class rankResolver {
	private int rank;
	/**
	 * 
	 * @return a Vector<String> of the resolution options
	 */
	public static Vector<String> getResolutionMethods() {
		Vector<String> methods = new Vector<String>();
		methods.add("standard");
		methods.add("dld");
		return methods;
	}
	public int getRank() {return this.rank;};
	/**
	 * 
	 * @param rankingMethod
	 * @param name name of person that cannot be found in data set
	 * @param gender gender affiliation of name
	 * @param yob year of birth of name
	 * @param country country of name
	 * @param type type of name (human or pet)
	 * @param size number of unique names in current data set.
	 * @param resolution means of resolution ["standard" giving the name size of data set + 1, "dld": uses class DLD to find a ranked name with the least difference with current name and adopts said names rank]
	 */
	public rankResolver(String rankingMethod, String name, String gender, int yob, String country, String type, int size, String resolution) {
		if(resolution.equals("standard")) { // using standard rank resolution
			this.rank = size+1;
		}else 

		if (resolution.equals("dld")){ //Using DLD for rank resolution
			/**
			 * We iterate through the entire data set and match the given name with the most similar name found according to the DLD function and take that names rank.
			 * This method is good for resolving variant spellings of names that may not appear on in the records and especially applies to the Nordish data sets where special characters
			 * exist within their names.
			 */
			//iterates through iYOB's data
			int threshold = name.length()/5;//threshold is currently set to be at least 20% similar to the original name
			int minDiff = Integer.MAX_VALUE;// initialized to max value as it is not expect that another name will require that many changes.
			Vector<String> similarNames = new Vector<String>();//stores list of minDiff names or similar names
			for(CSVRecord rec : AnalyzeNames.getFileParser(yob, type, country)){
				if (!rec.get(1).equals(gender)){
					continue; //if the name is not of specified gender then skip
				}
				String currName = rec.get(0);
				int currDiff = DLD.calculate(name,currName); // using DLD (Damerau-Levenshtein distance) to evaluate the how different the names are
				if (currDiff == minDiff){
					//name of same DLD difference as similarNames found so far e.g. jon has the same difference with jun and jan
					similarNames.add(currName);
				}else if(currDiff < minDiff){
					//new more similar name found
					similarNames.clear();
					similarNames.add(currName);
					minDiff = currDiff;
				}
			}
			if(similarNames.size() <= 0 || minDiff >= threshold){
				//if none found or the minDiff is larger then the acceptable threshold limits (coded into the system) then default to standard ranking
				this.rank = size+1;
			}else{
				//The name that is going to be selected (if there are multiple names found with the same minDiff) is either the middle 
				//or middle left ranking number in terms ranking in similarNames ordered from highest rank to lowest ranking.
				String selectedName = similarNames.get((int)(similarNames.size()/2));
				//getting the rank of the name that is going to be adopted.
				this.rank =  RankingAlgorithmFactory.getRankAlgorithm(rankingMethod, selectedName, gender, yob, country, type, resolution).getRank();// this call should not result in another call of this function (rankResolveR) method since name should be found.
			}
		}
	}
}
