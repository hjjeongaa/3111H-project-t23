package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.*;
import edu.duke.*;

/**
 * This class calculates the rank using Dense Ranking system
 * @author Yuxi Sun
 * v 2.0
 */
public class DR extends RankingAlgorithm {
	private int size;
	private int rank;
	private int lastFreq;
	private int unique; //stores the number of unique names seen so far
	//accessors
	public String getMethod(){
		return "Dense Ranking";
	}
	public int getRank(){
		return rank;
	}
	public int getSize(){
		return size;
	}
	public void addEntry(int freq) {
		if (lastFreq != freq)
			//since we assume the file is grouped by gender and 
			//order in descending order on frequency, no extra check is required 
			//we increase rank if we sea a decrease in frequency
			rank +=1;
		//update lastFreq (iteration variable)
		lastFreq = freq;
		++unique;
	}
	
	public DR(String name, String gender, int yob, String country, String type, String resolution){
		int rank = 1;
		int unique = 0; //stores the number of unique names seen so far
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
				if (lastFreq != Integer.parseInt(rec.get(2)))
					//since we assume the file is grouped by gender and 
					//order in descending order on frequency, no extra check is required 
					rank +=1;
				
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
	public DR(int freq) {
		this.rank = 1;
		this.unique = 0;
		this.lastFreq = freq;
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
		for(CSVRecord rec : AnalyzeNames.getFileParser(yob, type, country)){
			if (rec.get(1).equals(gender)) {
				if(Integer.parseInt(rec.get(2)) != prevFreq) rankUnderInspection++;
				if(rankUnderInspection == rank) output.add(rec.get(0));
				if(rankUnderInspection > rank) break;
				prevFreq = Integer.parseInt(rec.get(2));
			}
		}
		return output;
	}
}
