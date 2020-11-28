package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.*;
import edu.duke.*;
/**
 * This class either calculates the rank of a name in a specified data set type/country/year (and gender) or works as a running rank tracker using Standard Competition Ranking system
 * @author Yuxi Sun
 * v 2.0
 */
public class SCR extends RankingAlgorithm {
	private int size;
	private int rank;
	private int sameRankCount;
	private int lastFreq;
	//keeps track of which type of ranking algorithm it is (discriminator)
	private boolean isIterative;	

	//accessors
	/**
	 * Checks whether or not this Object instance is iterative or non-iterative
	 * @return true if the function is iterative.
	 * @author Yuxi Sun
	 */
	public boolean isIterative(){return this.isIterative;};
	/**
	 * @return the full name of the Ranking Algorithm method
	 * @author Yuxi Sun
	 */
	public String getMethod(){
		return "Standard Competition Ranking";
	}
	/**
	 * @return the abbreviated name of the Ranking Algorithm method
	 * @author Yuxi Sun
	 */
	public String getMethodAbbreviated(){
		return "scr";
	}
	/**
	 * For the iterative case it is used to get the rank of the last entered frequency.
	 * For the non-iterative case it is used to get the rank of the provided name in the specified data set using SCR.
	 * @author Yuxi Sun
	 */
	public int getRank(){
		return rank;
	}
	/**
	 * For iterative case returns the number of frequencies seen so far.
	 * For the non-iterative case returns the total size of the data set of specification country, year and gender (specified in non-iterative Constructor)
	 * @author Yuxi Sun
	 */
	public int getSize(){
		return size;
	}
	
	//Mutator: Iterator functions
	/**
	 * This function should be purely used for Iterative methods, this is used to update the state of the ranking and should be used with getRank().
	 * NOTHING will occur if this function is called on an iterative method to insure the integrity of the data.
	 * @param freq the frequency of the current entry to be entered 
	 * @return true if the addEntry succeeded
	 * @author Yuxi Sun
	 */
	public boolean addEntry(int freq) {
		if (!isIterative||freq>this.lastFreq) {
			return false; //invalid call, freq must be less then or equal to lastFreq and instance must be non-terative
		}
		if (this.lastFreq == freq)
			++this.sameRankCount;
		else{
			//since we assume the file is grouped by gender and 
			//order in descending order on frequency, no extra check is required 
			this.rank = this.rank + this.sameRankCount + 1;
			this.sameRankCount = 0;//reset sameRankCount
		}
		this.size+=1;
		return true;
	}

	//Constructors
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
	public SCR(String name, String gender, int yob, String country, String type, String resolution){
		this.isIterative = false;
		int rank = 1;
		int sameRankCount = 0; //stores the number of names seen with the same rank (frequency) as the current name (exclusive)
		//iterates through iYOB's data
		int lastFreq = 0;//stores the last seen frequency (used for seeing if the current name should have the same rank as the previous name).
		boolean found = false; 
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
		//setting size (number of names in yob)
		this.size = rank+sameRankCount;
		// if code gets to this point, then no name has been found
		if (!found)
			this.rank = new rankResolver("scr", name, gender, yob, country, type, this.size, resolution).getRank();
	}
	/**
	 * Iterative constructor, calling getRank() immediately after this constructor to get the rank of the first frequency entered.
	 * The iterative version of this object (identifiable by isIterative()) can be used in a for-loop to get the rank of the current name (after you enter the frequency).
	 * @param freq the frequency of the first name in the data set
	 * @author Yuxi Sun
	 */
	public SCR(int freq) {
		isIterative = true;
		this.lastFreq = freq;
		this.rank = 1;
		this.size = 1;
		this.sameRankCount = 0;
	}
	
	/**
	 * This function returns a list of names which have a rank of the specified rank.
	 * @author Ryder Khoi Daniel
	 * v 1.0
	 */
	public List<String> getNameFromRank(int rank, String gender, int yob, String type, String country){
		int rankUnderInspection = 1;
		int numNamesAbove = 1;
		int prevFreq = -1;
		List<String> output = new ArrayList<String>();
		for(CSVRecord rec : AnalyzeNames.getFileParser(yob, type, country)){
			if (rec.get(1).equals(gender)) {
				if(Integer.parseInt(rec.get(2)) != prevFreq) rankUnderInspection = numNamesAbove;
				if(rankUnderInspection == rank) output.add(rec.get(0));
				if(rankUnderInspection > rank) break;
				numNamesAbove++;
				prevFreq = Integer.parseInt(rec.get(2));
			}
		}
		return output;
	}
	
}
