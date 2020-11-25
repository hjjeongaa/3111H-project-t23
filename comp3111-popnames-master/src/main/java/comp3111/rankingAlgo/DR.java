package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;
import org.apache.commons.csv.*;
import edu.duke.*;

/**
 * This class either calculates the rank of a name in a specified data set type/country/year (and gender) or works as a running rank tracker using Dense Ranking system
 * @author Yuxi Sun
 * v 2.0
 */
public class DR extends RankingAlgorithm {
	private int size;
	private int rank;
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
		return "Dense Ranking";
	}
	/**
	 * @return the abbreviated name of the Ranking Algorithm method
	 * @author Yuxi Sun
	 */
	public String getMethodAbbreviated(){
		return "dr";
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
		if (this.lastFreq != freq)
			//since we assume the file is grouped by gender and 
			//order in descending order on frequency, no extra check is required 
			//we increase rank if we sea a decrease in frequency
			rank +=1;
		//update lastFreq (iteration variable)
		this.lastFreq = freq;
		++size;
		return true;
	}
	//Mutator: Iterator functions
	/**
	 * This function should be purely used for Iterative methods, this is used to update the state of the ranking and should be used with getRank().
	 * NOTHING will occur if this function is called on an iterative method to insure the integrity of the data.
	 * @param freq the frequency of the current entry to be entered 
	 * @return true if the addEntry succeeded
	 * @author Yuxi Sun
	 */
	public DR(String name, String gender, int yob, String country, String type, String resolution){
		this.isIterative = false;
		int rank = 1;
		int unique = 0; //stores the number of unique names seen so far
		//iterates through iYOB's data
		int lastFreq = Integer.MAX_VALUE;//stores the last seen frequency (used for seeing if the current name should have the same rank as the previous name).
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
				if (lastFreq != Integer.parseInt(rec.get(2))) {
					//since we assume the file is grouped by gender and 
					//order in descending order on frequency, no extra check is required 
					rank +=1;
				}
				//update lastFreq (iteration variable)
				lastFreq = Integer.parseInt(rec.get(2));
			}
			//now rank of current name has been computed, check if the iName is found
			if(rec.get(0).equals(name)){
				this.rank = rank;
				found = true;
			}
			++unique;
		}
		//setting size (number of names in yob)
		this.size = unique;
		// if code gets to this point, then no name has been found
		if (!found)
			this.rank = new rankResolver("dr", name, gender, yob, country, type, this.size, resolution).getRank();
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
	public DR(int freq) {
		this.isIterative = true;
		this.rank = 1;
		this.size = 1;
		this.lastFreq = freq;
	}
}
