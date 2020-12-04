/**
* TopNNames.java
* 
* Basis of Task 1.
* 
* 
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames;


import java.util.*;
import org.apache.commons.csv.CSVRecord;

import comp3111.export.ReportHistory;

public class TopNNames extends Reports {
	
	/* Private Variables */
	private HashMap<Integer, List<String>> collectionOfYears;
	private HashMap<Integer, List<Integer>> collectionOfFreqs;
	private int numOfNames;
	private int startYear;
	private int endYear;
	String expandedGender;
	
	/* Constructor */
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
					if(count == numOfNames) break;
					String name = rec.get(0);
					topNNames.add(name);
					topNFreqs.add(Integer.parseInt(rec.get(2)));
					++count;
				}
			}
			if(count < numOfNames) {
				for(int i = 0; i < numOfNames-count; ++i) {
					topNNames.add(" ");
					topNFreqs.add(-1);
				}
			}
			collectionOfYears.put(year, topNNames);
			collectionOfFreqs.put(year, topNFreqs);
		}
		
		updateReportLog();
	}
	
	/* Report Functions */
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
	
	public List<String> getListOfNamesFromYear(int year){
		if(year < startYear || year > endYear) return null;
		return collectionOfYears.get(year);
	}
	
	public List<Integer> getListOfFrequenciesFromYear(int year){
		if(year < startYear || year > endYear) return null;
		return collectionOfFreqs.get(year);
	}
	
	// getters
	public int getStartYear() {return this.startYear;}
	public int getEndYear() {return this.endYear;}
}
