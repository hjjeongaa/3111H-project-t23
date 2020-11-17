package comp3111.popnames;

import org.apache.commons.csv.*;
import edu.duke.*;
import java.util.*;
/**
 * Person class was created to assist the coding process for Task 4-5-6
 * @author Yuxi Sun
 * v1.0
 */
public class Person {
	private String name;
	private String gender;
	private int    yob;
	private String country;
	private String type;
	
	private int    rank; //rank of name (of said gender) in said dataset.
	private int    size; //stores the number of unique names in the yob of said dataset

	//getters
	public String getName()		{return name;};
	public String getGender()	{return gender;};
	public int    getYob()		{return yob;};
	public String getCountry()	{return country;};
	public String getType()		{return type;};
	public int    getRank()		{return rank;};
	public int    getSize()		{return size;};

	//mutators
	/**
	 * This function was written for the purpose of task 6 to fetch the rank and size variables.
	 * currently uses standard competition ranking
	 * @author Yuxi Sun
	 * 
	 */
	private void intialize() {
		int rank = 1;
		int sameRankCount = 0; //stores the number of names seen with the same rank (frequency) as the current name (exclusive)
		//iterates through iYOB's data
		int lastFreq = 0;//stores the last seen frequency (used for seeing if the current name should have the same rank as the previous name).
		boolean found = false; 
		boolean firstEntry = true;
		for(CSVRecord rec : AnalyzeNames.getFileParser(this.yob, this.type, this.country)){
			if (!rec.get(1).equals(this.gender)){
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
				}
				//update lastFreq (interation variable)
				lastFreq = Integer.parseInt(rec.get(2));
			}
			//now rank of curr name has been computed, check if the iName is found
			if(rec.get(0).equals(name)){
				this.rank = rank;
				return;
			}
		}
		//setting size (number of names in yob)
		this.size = rank+sameRankCount;
		// if code gets to this point, then no name has been found
		this.rank = rankResolver(this.size);
	}
	
	/**
	 * Used for rank resolution for unfound names
	 * @param size The number of unique names in the yob dataset
	 * @return
	 */
	private int rankResolver(int size){
		return size + 1;//basic one returns the size of the last position of the 
	}
	
	//constructor
	/**
	 * 
	 * @param name		the name of the person
	 * @param gender	the gender of the person
	 * @param yob		the year of birth of the person
	 * @param country	the country in which the information was scraped from
	 * @param type		the type of database in which the information was scraped from (human/pet)
	 */
	public Person(String name, String gender, int yob, String country, String type){
		this.name = name;
		this.gender = gender;
		this.yob = yob;
		this.country = country;
		this.type = type;
		intialize();
	}
	
	/**
	 * Overloaded constructor to allow for more optimal parsing.
	 * @param name		the name of the person
	 * @param gender	the gender of the person
	 * @param yob		the year of birth of the person
	 * @param country	the country in which the information was scraped from
	 * @param type		the type of database in which the information was scraped from (human/pet)
	 * @param rank		the rank of the person
	 */
	public Person(String name, String gender, int yob, String country, String type, int rank){
		this.name = name;
		this.gender = gender;
		this.yob = yob;
		this.country = country;
		this.type = type;
		this.rank = rank;
	}
}