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
	
	//variables
	private Person user;
	private Person mate;

	private float oScore;
	private String algo;
	private int size; //stores data from the Mate's year's table


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
		this.oScore = (1-(float)Math.abs(user.getRank()-mate.getRank())/user.getRank());
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
		float nUser = (float)user.getRank()/user.getSize();
		float nMate = (float)mate.getRank()/mate.getSize();
		this.oScore = (1-Math.abs(nUser-nMate)/nUser);
		return oScore;
	}

	/**
	 * Constructor
	 */
	public PredictCompatibilityScore(String iName, String iGender, int iYOB, String iNameMate, String iGenderMate, int iPreference, String country, String type, String algo) {
		this.user = new Person(iName, iGender, iYOB, country, type);
		this.mate = new Person(iNameMate, iGenderMate, iYOB+iPreference, country, type);
		this.algo = algo;
	}
}
