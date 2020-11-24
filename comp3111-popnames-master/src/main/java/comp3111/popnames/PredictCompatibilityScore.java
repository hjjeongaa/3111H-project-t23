package comp3111.popnames;
/**
* PredictCompatibilityScore.java - Code for Task 6
* Predicting Compatibility Score
* @author Yuxi Sun
* @version 1.1
*/
import comp3111.rankingAlgo.LD;
import org.apache.commons.csv.*;
import edu.duke.*;
import java.time.LocalDateTime;  
import java.util.*;
import java.lang.Math;
/**
 * Code for predicting the compatibility of two names
 */
public class PredictCompatibilityScore extends ReportLog{
	//records the time of creation or last modification
	private LocalDateTime time;  

	//variables
	private Person user;
	private Person mate;

	private HashMap<String, Double> oScore;

	//accessors
	public LocalDateTime getTime(){return time;};
	public HashMap<String, Double>  getoScore(){return oScore;};
	public Person getUser(){return user;};
	public Person getMate(){return mate;};
	public String getHTML(){
		//TODO::complete
		String html = "<div>";
		//insert content here
		html += "</div>";
		return null;
	};
	//accessors for Report History
	public String getTask(){
		return "Task 6";
	}
	public String getoReport(){
		String oReport = user.getName() + " " + mate.getName();
		return oReport;
	}
	/**
	 * Evaluation functions
	 * these functions should be called in the controller based on user selections to return scalar oScore.
	 */

	/**
	 * parm: Population Adjusted Rank Matching
	 * Self made function 1 (WOW FACTOR), takes take population size of each year into account so that the most compatible pair of names
	 * are the names that have the same population adjusted rank in their respective years.
	 * @return oScore from parm algorithm
	 */
	public double parm() {
		//normalizing ranks of each user
		double nUser = (double)user.getRank()/(user.getSize()+1);
		double nMate = (double)mate.getRank()/(mate.getSize()+1);
		return Math.max(0,(1-Math.abs(nUser-nMate)));
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
		double nUser = (double)user.getRank()/(user.getSize()+1);
		double nMate = (double)mate.getRank()/(mate.getSize()+1);
		double optMate = 0.5-(nUser -0.5);
		return Math.max(0,1-  Math.abs(nMate-optMate));
	}
	/**
	 *  string similarity using a modified Levenshtein distance. The values are squared to introduce nonlinearity.
	 *  @return (Levenshtein distance)^2/ (Max(user name length, mate name length)^2
	 */
	public double ld() {
		int ldScore = LD.calculate(user.getName(), mate.getName());
		return Math.max(0,1 - (double)Math.pow(ldScore,2)/Math.pow(Math.max(user.getName().length(),mate.getName().length()),2));
	}
	
	public double composite(){
		//should only be called after the other functions
		double composite = 0;
		composite += this.oScore.get("parm");
		composite += this.oScore.get("pasrm");
		composite += this.oScore.get("ld");
		return composite/3;
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
	 * @param iPreference Specify the preference on having a younger (negative number) or older (positive number) soul mate
	 * @param countryMate particulars of the person to be matched
	 * @param typeMate particulars of the person to be matched
	 * @param setting specifies the algorithm(s) that should be shown through the console
	 */
	public PredictCompatibilityScore(String iName, String iGender, int iYOB, String country, String type , String iNameMate, String iGenderMate, int iPreference, String countryMate, String typeMate, String rankingAlgo, String resolver) {
		this.time = LocalDateTime.now();
		// both names could not exist in the data set hence a special rankingAlgo and resolver has been used.
		this.user = new Person(iName, iGender, iYOB, country, type, rankingAlgo, resolver);
		this.mate = new Person(iNameMate, iGenderMate, iYOB+iPreference, countryMate, typeMate, rankingAlgo, resolver);
		this.oScore = new HashMap<String, Double>();
		//Note that preference will have no effect on the oScores if the name is not found within the dataset
		//Population Adjusted Rank Matching
		this.oScore.put("parm", parm());
		//Population Adjusted Symmetric Rank Matching
		this.oScore.put("pasrm", pasrm());
		//Levenshtein distance
		this.oScore.put("ld", ld());
		//composite
		this.oScore.put("composite", composite());

	}
}
