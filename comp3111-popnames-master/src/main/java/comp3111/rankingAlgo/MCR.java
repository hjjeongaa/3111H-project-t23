package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;
import org.apache.commons.csv.*;
import edu.duke.*;
/**
 * This class calculates the rank using Modified Competition Ranking system
 * @author Yuxi Sun 
 * v 1.0
 */
public class MCR extends RankingAlgorithm {
	private int size;
	private int rank;

	//accessors
	public String getMethod(){
		return "Modified Competition Ranking";
	}
	public int getRank(){
		return rank;
	}
	public int getSize(){
		return size;
	}
	public MCR(String name, String gender, int yob, String country, String type, String resolution){
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
					//order in decending order on frequency, no extra check is required 
					rank = rank + sameRankCount + 1;
					sameRankCount = 0;//reset sameRankCount
					if(!assigned && found){
						this.rank = rank;
						assigned = true;
					}
				}
				//update lastFreq (interation variable)
				lastFreq = Integer.parseInt(rec.get(2));
			}
			//now rank of curr name has been computed, check if the iName is found
			if(rec.get(0).equals(name)){
				this.rank = rank;
				found = true;
			}
		}
		if(!assigned && found){ // for the case that the name is found with the same freq as the last frequency
			this.rank = rank;
			assigned = true;
		}
		//setting size (number of names in yob)
		this.size = rank+sameRankCount - 1;
		// if code gets to this point, then no name has been found
		if (!found)
			this.rank = new rankResolver("mcr", name, gender, yob, country, type, this.size, resolution).getRank();
	}
}
