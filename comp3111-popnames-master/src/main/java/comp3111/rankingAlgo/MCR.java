package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.*;
import edu.duke.*;
/**
 * This class either calculates the rank of a name in a specified data set type/country/year (and gender) or works as a running rank tracker using Modified Competition Ranking System
 * NOTE THAT THIS FUNCTION DOES NOT SUPPORT ITERATORS
 * @author Yuxi Sun 
 * v 2.0
 */
public class MCR extends RankingAlgorithm {
	private int size;
	private int rank;
	private int sameRankCount;
	private int lastFreq;
	//keeps track of which type of ranking algorithm it is (discriminator), in this case it is by default false
	private boolean isIterative;
	//accessors
	/**
	 * Whether or not this Object instance is iterative or noniterative
	 * @return true if the function is iterative.
	 * @author Yuxi Sun
	 */
	public boolean isIterative(){return this.isIterative;};
	/**
	 * @return the full name of the Ranking Algorithm method
	 * @author Yuxi Sun
	 */
	public String getMethod(){
		return "Modified Competition Ranking";
	}
	/**
	 * @return the abbreviated name of the Ranking Algorithm method
	 * @author Yuxi Sun
	 */
	public String getMethodAbbreviated(){
		return "mcr";
	}
	/**
	 * @return gets the rank of the provided name in the specified data set using MCR.
	 * @author Yuxi Sun
	 */
	public int getRank(){
		return rank;
	}
	/**
	 * @return the total size of the data set of specification country, year and gender (specified in non-iterative Constructor)
	 * @author Yuxi Sun
	 */
	public int getSize(){
		return size;
	}
	/**
	 * This function is not supported for this class as look ahead cannot be done on the fly without look ahead
	 *  @return false by default is addEntry is not supported.
	 */
	public boolean addEntry(int freq) {
		//do nothing as look ahead is not support in the current format
		return false;
	}
	/**
	 * Non-iterative constructor for this object which is used strictly for getting the rank of a name in a specified data set and the size of the data set.
	 * No further mutations to this class will be allowed if this constructor is called.
	 * @param name the name you wish to rank for
	 * @param gender the gender of the name
	 * @param yob the year of the data set you wish to use
	 * @param country the of the data set you wish to use
	 * @param type the type of the data set you wish to use
	 * @param resolution the method you wish to use to replace a rank if name is not found in the specified data set. More can be found in the rankResolver class
	 * @author Yuxi Sun
	 */
	public MCR(String name, String gender, int yob, String country, String type, String resolution){
		this.isIterative = false;
		int rank = 1;
		int sameRankCount = 0; //stores the number of names seen with the same rank (frequency) as the current name (exclusive)
		//iterates through iYOB's data
		int lastFreq = 0;//stores the last seen frequency (used for seeing if the current name should have the same rank as the previous name).
		boolean found = false; 
		boolean assigned = false; // this.rank has been assigned yet
		boolean firstEntry = true;
		for(CSVRecord rec : AnalyzeNames.getFileParser(yob, type, country)){
			if (!rec.get(1).equals(gender)){
				continue; //if the name is not of specified gender then skip
			}
			//calculating the names rank in said year.
			if (firstEntry){
				//if this is the first entry (boundary case)
				lastFreq = Integer.parseInt(rec.get(2)); //setting lastFreq for else case
				firstEntry = false;
			}else{
				//not the first entry
				if (lastFreq == Integer.parseInt(rec.get(2)))
					++sameRankCount;
				else{
					//since we assume the file is grouped by gender and 
					//order in descending order on frequency, no extra check is required 
					rank = rank + sameRankCount + 1;
					sameRankCount = 0;//reset sameRankCount
					if(!assigned && found){
						this.rank = rank;
						assigned = true;
					}
				}
				//update lastFreq (iteration variable)
				lastFreq = Integer.parseInt(rec.get(2));
			}
			//now rank of current name has been computed, check if the iName is found
			if(rec.get(0).equals(name)){
				this.rank = rank;
				found = true;
			}
		}
		if(!assigned && found){ // for the case that the name is found with the same frequency as the last frequency
			this.rank = rank;
			assigned = true;
		}
		//setting size (number of names in yob)
		this.size = rank+sameRankCount;
		// if code gets to this point, then no name has been found
		if (!found)
			this.rank = new rankResolver("mcr", name, gender, yob, country, type, this.size, resolution).getRank();
	}
	
	/**
	 * This function returns a list of names which have a rank of the specified rank.
	 * @author Ryder Khoi Daniel
	 * v 1.0
	 */
	public List<String> getNameFromRank(int rank, String gender, int yob, String type, String country){
		int rankUnderInspection = 1;
		int prevFreq = -1;
		List<String> output = new ArrayList<String>();
		List<String> currentGroup = new ArrayList<String>();
		for(CSVRecord rec : AnalyzeNames.getFileParser(yob, type, country)){
			if (rec.get(1).equals(gender)) {
				if(rankUnderInspection > rank) break;
				if(Integer.parseInt(rec.get(2)) != prevFreq) {
					currentGroup = new ArrayList<String>();
					currentGroup.add(rec.get(0));
				}
				rankUnderInspection++;
				prevFreq = Integer.parseInt(rec.get(2));
			}
		}
		if(rankUnderInspection -1 == rank) output = currentGroup;
		return output;
	}
}
