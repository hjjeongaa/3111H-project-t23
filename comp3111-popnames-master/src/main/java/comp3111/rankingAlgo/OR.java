package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;
import org.apache.commons.csv.*;
import edu.duke.*;
/**
 * This class either calculates the rank of a name in a specified data set type/country/year (and gender) or works as a running rank tracker using Ordinal Ranking System
 * @author Yuxi Sun
 * v 2.0
 */
public class OR extends RankingAlgorithm {
	private int size;
	private int rank;
	//keeps track of which type of ranking algorithm it is (discriminator)
	private boolean isIterative;
	private int lastFreq;
	//accessors
	/**
	 * Checks whethe or not this Object instance is iterative or noniterative
	 * @return true if the function is iterative.
	 * @author Yuxi Sun
	 */
	public boolean isIterative(){return this.isIterative;};
	/**
	 * @return the full name of the Ranking Algorithm method
	 * @author Yuxi Sun
	 */
	public String getMethod(){
		return "Ordinal Ranking";
	}
	public String getMethodAbbreviated(){
		return "or";
	}
	/**
	 * @return the abbreviated name of the Ranking Algorithm method
	 * @author Yuxi Sun
	 */
	public int getRank(){
		return rank;
	}
	/**
	 * For the iterative case it is used to get the rank of the last entered frequency.
	 * For the non-iterative case it is used to get the rank of the provided name in the specified data set using SCR.
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
		++this.rank;
		++this.size;
		//update relevant variable
		this.lastFreq = freq;
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
	public OR(String name, String gender, int yob, String country, String type, String resolution){
		isIterative = false;
		int rank = 1;
		//iterates through iYOB's data
		boolean found = false; 
		for(CSVRecord rec : AnalyzeNames.getFileParser(yob, type, country)){
			if (!rec.get(1).equals(gender)){
				continue; //if the name is not of specified gender then skip
			}
			//now rank of current name has been computed, check if the iName is found
			if(rec.get(0).equals(name)){
				this.rank = rank;
				found = true;
			}
			++rank;
		}
		//setting size (number of names in yob)
		this.size = rank - 1;
		// if code gets to this point, then no name has been found
		if (!found)
			this.rank = new rankResolver("or", name, gender, yob, country, type, this.size, resolution).getRank();
	}
	/**
	 * Iterative constructor, calling getRank() immediately after this constructor to get the rank of the first frequency entered.
	 * The iterative version of this object (identifiable by isIterative()) can be used in a for-loop to get the rank of the current name (after you enter the frequency).
	 * @param freq the frequency of the first name in the data set
	 * @author Yuxi Sun
	 */
	public OR(int freq) {
		isIterative = true;
		this.rank = 1;
		this.size = 1;
		this.lastFreq = freq;
	}
}