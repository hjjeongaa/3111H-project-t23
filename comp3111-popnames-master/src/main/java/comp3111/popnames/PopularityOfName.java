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
import org.apache.commons.lang3.tuple.Triple;
import comp3111.export.ReportHistory;

public class PopularityOfName extends Reports {
	//The inputs provided to generate the report.
	private int startYear;
	private int endYear;
	
	//RanksInEachYear: A list of 3-tuples, each tuple containing the statistics of the name in a certain year.
	//The left item contains the user's rank in the year, middle item contains the total number of unique names in the year, and right item contains the percentile of the name's rank compared against all names in the year.
	private List<Triple<Integer,Integer,Double>> ranksInEachYear;
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
		String thisHtml = "<table><tr><th>Year</th><th>Your name's rank</th><th>Total # of names this year</th><th>Percentile</th></tr>";
		String tableRow = "<tr><td>%d</td><td>%d</td><td>%d</td><td>%s</td><tr>";
		
		this.startYear = startYear;
		this.endYear = endYear;
		//Initialize ranksInEachYear
		this.ranksInEachYear = new ArrayList<Triple<Integer,Integer,Double>>();
		//Iterate through all years within the start & end year.
		for (int thisYear = startYear; thisYear <= endYear; ++thisYear) {
			//To improve speed, we stop comparing names once the name we are looking for is found.
			boolean nameFound = false;
			//This will store the rank at which the name was found for this year.
			int thisYearRank = 0;
			//Will store the total length of the year, used to calculate the percentile.
			int thisYearRecordsLength = 0;
			//Counter for the current rank of the name being processed right now.
			int rankCounter = 0;
			//Go through all the names
			for (CSVRecord rec : AnalyzeNames.getFileParser(thisYear, type, country)) {
				//Check gender matches.
				if (gender.equals(rec.get(1))) {
					//If we find another name with the same gender, the rank increases. We also increase the total number of names found.
					++thisYearRecordsLength;
					++rankCounter;
					//Compare names if the name hasn't been found yet
					if (!nameFound) {
						String thisRecName = rec.get(0);
						//Check name matches.
						if (name.equals(thisRecName)) {
							nameFound = true;
							thisYearRank = rankCounter;
						}
					}
				}
			}
			//Calculate percentile of the name's rank (1- (name Rank / total number of ranks) * 100)
			Double rank = (double)thisYearRank;
			int total = thisYearRecordsLength;
			Double thisRankPercentage = 100*(1-((rank-1)/total));
			Triple<Integer,Integer,Double> rankAndYearSize = Triple.of(thisYearRank,thisYearRecordsLength,thisRankPercentage);
			this.ranksInEachYear.add(rankAndYearSize);
			//Html table should have the percentage truncated.
			DecimalFormat df = new DecimalFormat("#.##");
			thisHtml += String.format(tableRow, thisYear, thisYearRank, thisYearRecordsLength, df.format(thisRankPercentage)+"%");
		}

		thisHtml += "</table>";
		thisHtml = "<div>" + thisHtml + "</div>";
		super.setHTML(thisHtml);
		//Add to list of generated reports
		ReportHistory.addReportLog(this);
	}
	
	/**
	 * Returns the List object with the results. Used to fill the tableview in the output UI.
	 * @return the list of triples that stores all the results from generation
	 */
	public List<Triple<Integer,Integer,Double>> getPopularityList() {
		return this.ranksInEachYear;
	}
}
