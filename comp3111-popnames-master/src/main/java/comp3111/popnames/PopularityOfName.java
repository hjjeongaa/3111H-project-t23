/**
 * PopularityOfName
 * 
 * For task 2. Outputs a report on the popularity of a name and gender within a year range.
 * @author Hyun Joon Jeong
 */

package comp3111.popnames;

import java.text.DecimalFormat;
import java.util.*;
import org.apache.commons.csv.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import comp3111.export.ReportHistory;

public class PopularityOfName extends Reports {
	/**
	 * PopularityOfName
	 * 
	 * For task 2. Outputs a report on the popularity of a name and gender within a year range.
	 * @author Hyun Joon Jeong
	 */
	//The inputs provided to generate the report.
	private int startYear;
	private int endYear;
	
	//RanksInEachYear: A list of 4-tuples, each tuple containing a statistic of the name in a certain year.
	//The left item contains the user's rank in the year, middle item contains the name's frequency in the year, and right item contains a pair; inside of which contains the percentage of the name from all names in the year, and the total population of the year.
	private List<Triple<Integer,Integer,Pair<Integer,Double>>> ranksInEachYear;
	/**
	 * Constructor for PopularityOfName
	 * Computes all the ranks of the given name for each year between startYear and endYear, as well as the total number of names in each year, and puts it into an array.
	 * @param startYear the starting year where popularity should be analyzed
	 * @param endYear the ending year where popularity should be analyzed
	 * @param name name whose popularity the user wants to analyze
	 * @param gender M or F, depending on gender of the name.
	 * @param country data set's country of origin
	 * @param type data set's type
	 */
	public PopularityOfName(int startYear, int endYear, String name, String gender, String country, String type) {
		//Call Report constructor
		super(name, gender, country, type);
		super.setoReport(name+" ("+gender+"), "+startYear+"~"+endYear);
		super.setTask("Popularity of Name");
		//For HTML generation. It will be generated on the fly while we iterate through the CSV.
		String thisHtml = "<head> <style> table, th, td { border: 1px solid black; } table.center { margin-left: auto; margin-right: auto; } </style> </head>";
		thisHtml +=  String.format("<h3>Popularity of %s between %d and %d</h3>", name,startYear,endYear);
		thisHtml += "<table><tr><th>Year</th><th>Your name's rank</th><th>Frequency of name</th><th>Percentage</th></tr>";
		String tableRow = "<tr><td>%d</td><td>%d</td><td>%d</td><td>%s</td><tr>";
		
		this.startYear = startYear;
		this.endYear = endYear;
		//Initialize ranksInEachYear
		this.ranksInEachYear = new ArrayList<Triple<Integer,Integer,Pair<Integer,Double>>>();
		//Iterate through all years within the start & end year.
		for (int thisYear = startYear; thisYear <= endYear; ++thisYear) {
			//To improve speed, we stop comparing names once the name we are looking for is found.
			boolean nameFound = false;
			//This will store the rank at which the name was found for this year.
			int thisYearRank = 0;
			//Will store the total population of the year for this gender, used to calculate the percentage.
			int thisYearPopulation = 0;
			//Will store the frequency of the name.
			int thisYearFrequency = 0;
			//Counter for the current rank of the name being processed right now.
			int rankCounter = 0;
			//Go through all the names
			for (CSVRecord rec : AnalyzeNames.getFileParser(thisYear, type, country)) {
				//Check gender matches.
				if (gender.equals(rec.get(1))) {
					//If we find another name with the same gender, the rank increases. We also increase the total number of names found.
					thisYearPopulation += Integer.parseInt(rec.get(2));
					++rankCounter;
					//Compare names if the name hasn't been found yet
					if (!nameFound) {
						String thisRecName = rec.get(0);
						//Check name matches.
						if (name.equals(thisRecName)) {
							//Update the rank and frequency of the name we want to find.
							nameFound = true;
							thisYearRank = rankCounter;
							thisYearFrequency = Integer.parseInt(rec.get(2));
						}
					}
				}
			}
			//Calculate percentage
			Double thisNamePercentage = (double) ((100.0*thisYearFrequency)/thisYearPopulation);
			Triple<Integer,Integer,Pair<Integer,Double>> rankAndYearSize = Triple.of(thisYearRank,thisYearFrequency,Pair.of(thisYearPopulation,thisNamePercentage));
			this.ranksInEachYear.add(rankAndYearSize);
			//Html table should have the percentage truncated.
			DecimalFormat df = new DecimalFormat("#.##");
			thisHtml += String.format(tableRow, thisYear, thisYearRank, thisYearFrequency, df.format(thisNamePercentage)+"%");
		}

		thisHtml += "</table>";
		thisHtml = "<div>" + thisHtml + "</div>";
		super.setHTML(thisHtml);
		//Add to list of generated reports
		ReportHistory.addReportLog(this);
	}
	
	/**
	 * Returns the List object with the results. Used to fill the tableview in the output UI.
	 * @return the four tuple that stores all the results from generation
	 */
	public List<Triple<Integer,Integer,Pair<Integer,Double>>> getPopularityList() {
		return this.ranksInEachYear;
	}
}
