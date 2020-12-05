/**
* TopNNames.java
* 
* Basis of Task 1.
* This class operates by mapping every year in range to a list of strings, and mapping every year in range to a list of frequencies (integers).
* After the constructor has set up this data structure, then it is very easy and fast to find information about the year of interest.
* 
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames;


import java.util.*;
import org.apache.commons.csv.CSVRecord;

import comp3111.export.ReportHistory;

/**
 * This class is used for task 1.
 * It contains to hashmaps which allow for rapid access to data after the constructor sets it up.
 * @author Ryder Khoi Daniel
 * v1.0
 */
public class TopNNames extends Reports {
	/**
	* TopNNames.java
	* 
	* Basis of Task 1.
	* This class operates by mapping every year in range to a list of strings, and mapping every year in range to a list of frequencies (integers).
	* After the constructor has set up this data structure, then it is very easy and fast to find information about the year of interest.
	* 
	* @author Ryder Khoi Daniel
	* @version 1.0
	*/
	
	/* Private Variables */
	private HashMap<Integer, List<String>> collectionOfYears;
	private HashMap<Integer, List<Integer>> collectionOfFreqs;
	private int numOfNames;
	private int startYear;
	private int endYear;
	String expandedGender;
	
	/* Constructor */
	/**
	 * This constructors main role is to set up the hashmaps so data can be pulled from them rapidly.
	 * It also sends information to the report logger so that information found here may be exported too.
	 * 
	 * @param startYear from which year the user would like to start seeing the top N names.
	 * @param endYear the last year the user would like to see the top N names for.
	 * @param gender the gender the user is interested in.
	 * @param numNames the number of names per year the user would like to see.
	 * @param country the country of interest.
	 * @param type the type of interest - almost guaranteed to be human.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public TopNNames(int startYear, int endYear, String gender, int numNames, String country, String type){
		//	Call Report constructor.
		super(null, gender, country, type);
		collectionOfYears = new HashMap<Integer, List<String>>();
		collectionOfFreqs = new HashMap<Integer, List<Integer>>();
		this.numOfNames = Math.max(0,numNames);
		this.startYear = Math.max(startYear, GlobalSettings.getLowerBound());
		this.endYear = Math.min(endYear, GlobalSettings.getUpperBound());
		
		/* Stuff for exporting */
		expandedGender = "Male";
		if(gender.equals("F")) expandedGender = "Female";
		super.setoReport("Top "+ expandedGender +" Names From "+startYear+" to "+endYear);
		super.setTask(String.format("Top %d Names in %s", numOfNames, country));
		
		/*
		 * 		Generate the collection of years.
		 */
		
		// For every year in the range
		for(int year = startYear; year <= endYear; ++year) {
			int count = 0;
			List<String> topNNames = new ArrayList<String>();
			List<Integer> topNFreqs = new ArrayList<Integer>();
			for(CSVRecord rec : AnalyzeNames.getFileParser(year, type, country)) {
				// We are only interested in Names who belong to the gender of interest.
				if(rec.get(1).equals(gender)) {
					if(count == numOfNames) break; // only return the top numOfNames names.
					String name = rec.get(0);
					topNNames.add(name);
					topNFreqs.add(Integer.parseInt(rec.get(2)));
					++count;
				}
			}
			// if there is any discrepancy between the the amount of names found, and the number of names requested, fill the rest with blanks and -1.
			if(count < numOfNames) {
				for(int i = 0; i < numOfNames-count; ++i) {
					topNNames.add(" ");
					topNFreqs.add(-1);
				}
			}
			collectionOfYears.put(year, topNNames);
			collectionOfFreqs.put(year, topNFreqs);
		}
		
		// Update the report log so that the formatted info can be exported.
		updateReportLog();
	}
	
	/* Report Functions */
	/**
	 * Utility function to send information for export.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	private void updateReportLog() {
		String thisHtml = String.format("<head> <style> table, th, td { border: 1px solid black; } table.center { margin-left: auto; margin-right: auto; } </style> </head> <h3>Top %d %s Names From %d to %d</h3>", numOfNames, expandedGender, startYear, endYear); 
		String tableRow = "<tr><td>%d</td><td>%s</td><td>%d</td><tr>";
		for(int year = startYear; year <= endYear; ++year) {
			thisHtml += "<h4>" + String.format("%d", year) + "</h4>";
			thisHtml += "<table><tr><th>Rank</th><th>Name</th><th>Frequency</th></tr>";
			for(int rank = 0; rank < this.numOfNames; ++rank) {
				thisHtml += String.format(tableRow,rank+1, collectionOfYears.get(year).get(rank), collectionOfFreqs.get(year).get(rank));
			}
			thisHtml += "</table>";
		}
		thisHtml = "<div>" + thisHtml + "</div>";
		super.setHTML(thisHtml);
		ReportHistory.addReportLog(this);
	}
	
	/* Interface Functions */
	/**
	 * This function returns a list of the top numOfNames names from the year of choice.
	 * @param year year of interest. 
	 * @return Either returns a list of strings if the year is valid, and null otherwise.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public List<String> getListOfNamesFromYear(int year){
		if(year < startYear || year > endYear) return null;
		return collectionOfYears.get(year);
	}
	/**
	 * This function returns a list of the top numOfNames frequencies from the year of choice.
	 * @param year year of interest.
	 * @return Either returns a list of integers, or null if the year is invalid.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public List<Integer> getListOfFrequenciesFromYear(int year){
		if(year < startYear || year > endYear) return null;
		return collectionOfFreqs.get(year);
	}
	
	// getters
	/**
	 * @return returns the start year the user specified
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public int getStartYear() {return this.startYear;}
	/**
	 * @return returns the end year the user specified.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public int getEndYear() {return this.endYear;}
}
