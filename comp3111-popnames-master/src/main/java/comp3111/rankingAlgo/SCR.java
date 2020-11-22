package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;
import org.apache.commons.csv.*;
import edu.duke.*;
/**
 * This class calculates the rank using Standard Competition Ranking system
 * @author Yuxi Sun
 * v 2.0
 */
public class SCR extends RankingAlgorithm {
	private int size;
	private int rank;
	private int sameRankCount;
	private int lastFreq;
	//accessors
	public String getMethod(){
		return "Standard Competition Ranking";
	}
	public int getRank(){
		return rank;
	}
	public int getSize(){
		return size;
	}
	
	//Mutator: Iterator functions
	public void addEntry(int freq) {
		if (this.lastFreq == freq)
			++this.sameRankCount;
		else{
			//since we assume the file is grouped by gender and 
			//order in descending order on frequency, no extra check is required 
			this.rank = this.rank + this.sameRankCount + 1;
			this.sameRankCount = 0;//reset sameRankCount
		}
	}
	//Constructors
	public SCR(String name, String gender, int yob, String country, String type, String resolution){
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
		this.size = rank+sameRankCount - 1;
		// if code gets to this point, then no name has been found
		if (!found)
			this.rank = new rankResolver("scr", name, gender, yob, country, type, this.size, resolution).getRank();
	}
	
	public SCR(int freq) {
		this.lastFreq = freq;
		this.rank = 1;
		this.sameRankCount = 0;
	}
	
}
