package comp3111.popnames;
/**
* PredictCompatibilityScore.java - Code for Task 6
* Predicting Compatibility Score
* @author Yuxi Sun
* @version 1.1
*/
import comp3111.rankingAlgo.DLD;
import org.apache.commons.csv.*;
import edu.duke.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  
import java.util.*;
import java.lang.Math;
/**
 * Code for predicting the compatibility of two names
 */
public class PredictCompatibilityScore{
	public class algoScore{
		private String algo;
		private double score;
		public algoScore(String algo, double score){
			this.algo = algo;
			this.score = score;
		}
		public String getAlgo(){return this.algo;};
		public double getScore(){return this.score;};
	}
	//class formatter for formatting time, subclasses should use superclass variable to format to save space.
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	//records the time of creation or last modification
	private LocalDateTime time;  

	//variables
	private Person user;
	private Person mate;

	private Vector<algoScore> oScore;
	private String setting;
	private int size; //stores data from the Mate's year's table

	//accessors
	public LocalDateTime getTime(){return time;};
	public Vector<algoScore> getoScore(){return oScore;};
	public String getSetting(){return setting;};
	public Person getUser(){return user;};
	public Person getMate(){return mate;};
	public String generateHTML(){
		//TODO::complete
		return null;
	};
	/**
	 * Evaluation functions
	 * these functions should be called in the controller based on user selections to return scalar oScore.
	 */


	/**
	 * NTK 6
	 * @return oScore from nkt6 algorithm
	 */
	public double nkt6() {
		// the closer the rank the better your score 
		return (1-(double)Math.abs(user.getRank()-mate.getRank())/user.getRank());
	}

	/**
	 * parm: Population Adjusted Rank Matching
	 * Self made function 1 (WOW FACTOR), takes take population size of each year into account so that the most compatible pair of names
	 * are the names that have the same population adjusted rank in their respective years.
	 * @return oScore from parm algorithm
	 */
	public double parm() {
		//normalizing ranks of each user
		double nUser = (double)user.getRank()/user.getSize();
		double nMate = (double)mate.getRank()/mate.getSize();
		return (1-Math.abs(nUser-nMate));
	}
	/** 
	 * parsm: Population Adjusted Symmetric Rank Matching
	 * Self made function 2 (WOW FACTOR), takes parm and adapts it to take make the rank symmetric around the 50% to be the most compatible 
	 * e.g. if there are 100 names and iName is rank 70th, his most compatible name would be at rank 30th.
	 * The idea of this algorithm is that both names combined together would result in a neutral/ average level of uniqueness
	 * @return oScore from pasrm algorithm
	*/
	public double pasrm(){
		//normalizing ranks of each user 
		double nUser = (double)user.getRank()/user.getSize();
		double nMate = (double)mate.getRank()/mate.getSize();
		double optMate = 0.5-(nUser -0.5);
		return 1-  Math.abs(nMate-optMate);
	}
	/**
	 *  string similarity using a modified Damerau-Levenshtein distance
	 *  @return Damerau-Levenshtein distance/ (user name length + mate name length)
	 */
	public double dld() {
		return 1 - (double)DLD.calculate(user.getName(), mate.getName())/(user.getName().length()+mate.getName().length());
	}

	/**
	 * Constructor
	 * @param iName name of the person seeking advice
	 * @param iGender gender of the person seeking advice
	 * @param iYOB year of birth of the person seeking advice
	 * @param country particulars of the person seeking advice
	 * @param type particulars of the person seeking advice
	 * @param iNameMate the name of the person to be matched
	 * @param iGenderMate the gender of the person to be matched
	 * @param iPreference Specify the preference on having a younger (negative number) or older (positive number) soulmate
	 * @param countryMate particulars of the person to be matched
	 * @param typeMate particulars of the person to be matched
	 * @param setting specifies the algorithm(s) that should be shown through the console
	 */
	public PredictCompatibilityScore(String iName, String iGender, int iYOB, String country, String type , String iNameMate, String iGenderMate, int iPreference, String countryMate, String typeMate, String setting) {
		this.time = LocalDateTime.now();
		this.user = new Person(iName, iGender, iYOB, country, type);
		this.mate = new Person(iNameMate, iGenderMate, iYOB+iPreference, countryMate, typeMate);
		this.setting = setting;
		this.oScore = new Vector<algoScore>();
		//consider converting oScore to vector
		if (setting.equals("nkt6")||setting.equals("all")){
			this.setting = "nkt6";
			this.oScore.add(new algoScore("nkt6", nkt6()));
		}
		if (setting.equals("parm")||setting.equals("all")){
			//Population Adjusted Rank Matching
			this.setting = "parm";
			this.oScore.add(new algoScore("parm", parm()));
		}
		if (setting.equals("pasrm")||setting.equals("all")){
			this.setting = "pasrm";
			this.oScore.add(new algoScore("pasrm", pasrm()));
		}
		if (setting.equals("dld")||setting.equals("all")){
			//Damerau-Levenshtein distance
			this.setting = "dld";
			this.oScore.add(new algoScore("dld", dld()));
		}
		if (setting.equals("all")) {
			this.setting = "all";
		}
	}
}
