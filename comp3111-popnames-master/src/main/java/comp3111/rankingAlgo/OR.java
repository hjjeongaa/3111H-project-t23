package comp3111.rankingAlgo;

import comp3111.popnames.AnalyzeNames;
import org.apache.commons.csv.*;
import edu.duke.*;
/**
 * This class calculates the rank using ordinal ranking system
 * @author Yuxi Sun
 * v 2.0
 */
public class OR extends RankingAlgorithm {
	private int size;
	private int rank;

	//accessors
	public String getMethod(){
		return "Ordinal Ranking";
	}
	public int getRank(){
		return rank;
	}
	public int getSize(){
		return size;
	}
	public void addEntry(int freq) {
		++this.rank;
	}
	public OR(String name, String gender, int yob, String country, String type, String resolution){
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
	public OR(int freq) {
		this.rank = 1;
	}
}