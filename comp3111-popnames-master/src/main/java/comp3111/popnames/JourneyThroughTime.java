/**
 * JourneyThroughTime
 * 
 * WOW factor of task 5. Called by SoulmateName and is used to pull data from for the scenes in the jorney through time. Employs a singleton design class for simplicity and consistency.
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

public class JourneyThroughTime extends Reports {
	// Singleton Stuff
	private static final JourneyThroughTime SINGLE_INSTANCE = new JourneyThroughTime();
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
	
	public static int getFirstAppearance(String name, String gender) {
		int res = -1;
		int startYear = GlobalSettings.getLowerBound();
		int endYear = GlobalSettings.getUpperBound();
		String country = GlobalSettings.getCountry();
		for(int year = startYear; year <= endYear; ++year) {
			for(CSVRecord rec : AnalyzeNames.getFileParser(year, JourneyThroughTime.type, country)){
				if(rec.get(1).equals(gender)) {
					if(rec.get(0).equals(name)) return year;
				}
			}
		}
		return res;
	}
	
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
	
	public static Image getImage(int year) throws FileNotFoundException {
		return new Image(String.format("/JTTImagesAndFacts/images/%d.jpg", year));
	}
	
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
				output.add(0);
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
	public static String getUserName() { return JourneyThroughTime.inputName;}
	public static String getSoulmateName(){return JourneyThroughTime.SoulmateName;}
	public static String getUserGender() {return JourneyThroughTime.inputGender;}
	public static String getSoulmateGender(){return JourneyThroughTime.soulmateGender;}
	public static int getYOB(){return JourneyThroughTime.YOB;}
	public static String getCountry(){return JourneyThroughTime.country;}
	public static String getType(){return JourneyThroughTime.type;}
}
