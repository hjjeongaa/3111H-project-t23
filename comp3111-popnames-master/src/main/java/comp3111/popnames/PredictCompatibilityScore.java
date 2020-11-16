package comp3111.popnames;
/**
* PredictCompatibilityScore.java - Code for Task 6
* Predicting Compatibility Score
* @author Yuxi Sun
* @version 1.0
*/
import org.apache.commons.csv.*;
import edu.duke.*;
import java.time.LocalDateTime;  
import java.util.*;
import java.lang.Math;

public class PredictCompatibilityScore{
	/**
	 * Code for predicting the compatibility of two names
	 */
	public class Person {
		private String name;
		private char   gender;
		private int    yob;
		private String country;
		private String type;

		private int    rank;
		private int    size;

		//getters
		public String getName()		{return name;};
		public char   getGender()	{return gender;};
		public int    getYob()		{return yob;};
		public String getCountry()	{return country;};
		public String getType()		{return type;};
		public int    getRank()		{return rank;};
		public int    getSize()		{return size;};
		
		//mutators
		private void intialize() {
			int rank = 1;
			int sameRankCount = 0; //stores the number of names seen with the same rank (frequency) as the current name (exclusive)
			//iterates through iYOB's data
			int lastFreq = 0;//stores the last seen frequency
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
		}
		//constructor
		public Person(String name, char gender, int yob, String country, String type){
			this.name = name;
			this.gender = gender;
			this.yob = yob;
			this.country = country;
			this.type = type;
			intialize();
		}
	}
	//variables
	private Person user;
	private Person mate;

	private float oScore;
	private String algo;
	private int size; //stores data from the Mate's year's table
	private int rankResolver(int length){
		return length + 1;//basic one returns the size of the last position of the 
	}

	/**
	 * Evaluation functions
	 * these functions should be called in the controller based on user selections to return scalar oScore.
	 */

	/**
	 * NTK 6
	 */
	public float nkt6() {
		// update algo name
		this.algo = "ntk6";
		// the closer the rank the better your score 
		this.oScore = (1-(float)Math.abs(user.rank-mate.rank)/user.rank);
		return oScore;
	}
	/**
	 * Self made function 1 (WOW FACTOR)
	 */
	public float pntk6() {
		/**
		 * takes ntk6 and adapts it to take population size of each year into account.
		 */
		this.algo = "pntk6";
		//normalizing ranks of each user
		float nUser = (float)user.rank/user.size;
		float nMate = (float)mate.rank/mate.size;
		this.oScore = (1-Math.abs(nUser-nMate)/nUser);
		return oScore;
	}

	/**
	 * Constructor
	 */
	public PredictCompatibilityScore(String iName, char iGender, int iYOB, String iNameMate, char iGenderMate, int iPreference, String country, String type, String algo) {
		this.user = new Person(iName, iGender, iYOB, country, type);
		this.mate = new Person(iNameMate, iGenderMate, iYOB+iPreference, country, type);
		this.algo = algo;
	}
}
