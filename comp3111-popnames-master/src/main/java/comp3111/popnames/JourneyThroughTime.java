/**
 * JourneyThroughTime
 * 
 * WOW factor of task 5.
 * Called by SoulmateName and is used to pull data from for the scenes in the journey through time. 
 * Employs a singleton design class for simplicity and consistency.
 * @author Ryder Khoi Daniel
 */

package comp3111.popnames;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.*;
import javafx.scene.image.Image;
/**
 * This class represents the singleton object in which all journeys through time will refer to.
 * The order in which objects interact with this object is as follows:
 * The SoulmateName_controller calls this object to set the values for the coming scenes.
 * Then, all the following scenes pull data and use functions in this class to operate.
 * @author Ryder Khoi Daniel
 * v1.0
 */
public class JourneyThroughTime extends Reports {
	/**
	 * JourneyThroughTime
	 * 
	 * WOW factor of task 5.
	 * Called by SoulmateName and is used to pull data from for the scenes in the journey through time. 
	 * Employs a singleton design class for simplicity and consistency.
	 * @author Ryder Khoi Daniel
	 */
	// Singleton Stuff
	private static final JourneyThroughTime SINGLE_INSTANCE = new JourneyThroughTime();
	/**
	 * Essentially Empty Constructor. Needs to call the super-classes constructor.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	private JourneyThroughTime() {
		super(null,null,null,null);
	}
	
	// Variables
	private static String inputName;
	private static String SoulmateName;
	private static String inputGender;
	private static String soulmateGender;
	private static int YOB;
	private static String country;
	private static String type;
	
	// Variables for the export function.
	
	private static String thisHTML;
	
	// Assume this is called first
	/**
	 * Called by SoulmateName_controller. This is the function that sets all the values to be used by later functions.
	 * @param inputName name of the user.
	 * @param soulmateName name of the potential soulmate.
	 * @param inputGender gender of the user.
	 * @param soulmateGender Gender of the soulmate.
	 * @param YOB The birth year of the user.
	 * @param country The country of the database.
	 * @param type Type of species.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static void setValues(String inputName, String soulmateName, String inputGender, String soulmateGender, int YOB, String country, String type) {
		JourneyThroughTime.inputName = inputName;
		JourneyThroughTime.SoulmateName = soulmateName;
		JourneyThroughTime.inputGender = inputGender;
		JourneyThroughTime.soulmateGender = soulmateGender;
		JourneyThroughTime.YOB = YOB;
		JourneyThroughTime.country = country;
		JourneyThroughTime.type = type;
		
	}
	
	// These methods here are guaranteed to be called after all of the values have been set.
	/**
	 * This function looks for and returns the year of first appearance of a given name and gender in the data set specified by country.
	 * 
	 * @param name The name of the person you are looking for.
	 * @param gender The gender of the person you are looking for.
	 * @return number representing the year in which the person you are looking for first appears. negative one is returned if the name is not found.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static int getFirstAppearance(String name, String gender) {
		int res = -1;
		int startYear = GlobalSettings.getLowerBound();
		int endYear = GlobalSettings.getUpperBound();
		String country = GlobalSettings.getCountry();
		for(int year = startYear; year <= endYear; ++year) {
			for(CSVRecord rec : AnalyzeNames.getFileParser(year, "human", country)){
				if(rec.get(1).equals(gender)) {
					if(rec.get(0).equals(name)) return year;
				}
			}
		}
		return res;
	}
	
	/**
	 * This function returns a list containing two facts about any year from 1880-2019.
	 * @param  year value in [1880, 2019]
	 * @return a list of facts in string format.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static List<String> getFacts(int year) {
		List<String> facts = new ArrayList<String>();		
		FileResource factsSource = new FileResource("JTTImagesAndFacts/facts_clean.csv");
		CSVParser factsAsCSV = factsSource.getCSVParser(false, "\t");
		for(CSVRecord rec : factsAsCSV) {
			if(Integer.parseInt(rec.get(0)) == year) {
				facts.add(rec.get(1));
				facts.add(rec.get(2));
				break;
			}
		}
		return facts;
	}
	
	/**
	 * This function returns an Image object of an image that has to do with a specified year.
	 * 
	 * @param year  in [1880, 2019].
	 * @return image object of image that has to do with year.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static Image getImage(int year) throws FileNotFoundException {
		if(year < GlobalSettings.getLowerBound() || year > GlobalSettings.getUpperBound()) return null;
		return new Image(String.format("/JTTImagesAndFacts/images/%d.jpg", year));
	}
	
	/**
	 * This function, given a name, gender and life expectancy returns a list of the population of that name on any given year between 1880 and 2019.
	 * The index 0 of the list returned refers to the population of name in the year 1880.
	 * 
	 * The way this function works is it first sums up the entire population and saves that as a list. For example, let P be the list of cumulative population, meaning that P[i] = SUM(P[0:i-1]).
	 * Let there also be another array keeping track of the frequency of that name on each year.
	 * Then starting from 1880 (0 index), we look at the year 1880 + life expectancy, and from that point onwards to the end, we subtract the population of the specified name from 1880. This process is repeated for the whole range of years.
	 * 
	 * @param name name of interest
	 * @param gender gender of name of interest
	 * @param lifeExpectancy Life expectancy of people with name of interest. 
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static List<Integer> getPopulationOf(String name, String gender, int lifeExpectancy){
		List<Integer> output = new ArrayList<Integer>();
		List<Integer> track = new ArrayList<Integer>();
		int startYear = GlobalSettings.getLowerBound();
		int endYear = GlobalSettings.getUpperBound();
		String country = GlobalSettings.getCountry();
		int sum = 0;
		for(int year = startYear; year <= endYear; ++year) {
			boolean found = false;
			for(CSVRecord rec : AnalyzeNames.getFileParser(year, "human", country)) {
				if(rec.get(1).equals(gender) && rec.get(0).equals(name)) {
					sum += Integer.parseInt(rec.get(2));
					track.add(Integer.parseInt(rec.get(2)));
					output.add(sum);
					found = true;
					break;
				}
			}
			if(found == false) {
				output.add(sum);
				track.add(0);
			}
		}
		for(int popYear = 0; popYear < track.size(); ++popYear ) {
			for(int year = popYear + lifeExpectancy; year < track.size(); ++year) {
				int newPop = output.get(year) - track.get(popYear);
				output.set(year, newPop);
			}
		}
		return output;
	}
	
	/* Getters */
	/**
	 * @return name of the user.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static String getUserName() { return JourneyThroughTime.inputName;}
	/**
	 * @return soulmates name.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static String getSoulmateName(){return JourneyThroughTime.SoulmateName;}
	/**
	 * @return gender of the user.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static String getUserGender() {return JourneyThroughTime.inputGender;}
	/**
	 * @return soulmates gender.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static String getSoulmateGender(){return JourneyThroughTime.soulmateGender;}
	/**
	 * @return year of birth of the user.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static int getYOB(){return JourneyThroughTime.YOB;}
	/**
	 * @return country of interest
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static String getCountry(){return JourneyThroughTime.country;}
	/**
	 * @return type. Almost guaranteed to be human.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static String getType(){return JourneyThroughTime.type;}
}
