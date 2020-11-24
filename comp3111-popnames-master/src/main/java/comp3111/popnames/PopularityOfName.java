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

public class PopularityOfName extends Reports {
	//The inputs provided to generate the report.
	private int startYear;
	private int endYear;
	
	private List<Triple<Integer,Integer,Double>> ranksInEachYear;
	/**
	 * Constructor for PopularityOfName
	 * Computes all the ranks of the given name for each year between startYear and endYear, as well as the total number of names in each year, and puts it into an array.
	 * @param startYear
	 * @param endYear
	 * @param country
	 * @param type dataset type
	 */
	public PopularityOfName(int startYear, int endYear, String name, String gender, String country, String type) {
		//Call Report constructor
		super(name, gender, country, type);

		this.startYear = startYear;
		this.endYear = endYear;
		//Create array object - this will hold the rank of the name every year.
		this.ranksInEachYear = new ArrayList<Triple<Integer,Integer,Double>>();
		//Iterate through all years within the start & end year.
		for (int thisYear = startYear; thisYear <= endYear; ++thisYear) {
			//To save computational power, we use this to stop comparing names once the name we are looking for is found.
			boolean nameFound = false;
			//This will store the rank at which the name was found for this year.
			int thisYearRank = 0;
			//Stores the total length of the year, used to calculate a percentage statistic.
			int thisYearRecordsLength = 0;
			//Counter for the current rank.
			int rankCounter = 0;
			for (CSVRecord rec : AnalyzeNames.getFileParser(thisYear, type, country)) {
				//Check gender matches.
				if (gender.equals(rec.get(1))) {
					++thisYearRecordsLength;
					++rankCounter;
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
			//Calculate percentile of the name's rank
			DecimalFormat df = new DecimalFormat("#.##");
			Double rank = (double)thisYearRank;
			int total = thisYearRecordsLength;
			Double thisRankPercentage = 100*(1-(rank/total));
			Triple<Integer,Integer,Double> rankAndYearSize = Triple.of(thisYearRank,thisYearRecordsLength,thisRankPercentage);
			this.ranksInEachYear.add(rankAndYearSize);
		}
	}
	/**
	 * Using the array computed when the constructor was called, a report is generated with each rank in each year.
	 * It creates a table with the year, the rank of the name in that year, and a percentile statistic of how popular that name was in that year, compared to the other names in the year.
	 * @return string containing the report table.
	 */
	//DEPRECATED
	public String getReport() { 
		/*//Basic information
		String outputReport = String.format("Popularity report for: %s (%s), from %d to %d.\n", this.name, this.gender, this.startYear, this.endYear);
		//Line 1: Print out each year
		outputReport += "Year:\t\t";
		for (int thisYear = this.startYear; thisYear <= this.endYear; ++thisYear) {
			outputReport += thisYear + "\t\t\t";
		}
		//Line 2: Print out the rank of the name at each year
		outputReport += "\nRank:\t";
		//Iterate through ranksInEachYear
		for (int i = 0; i <= this.endYear-this.startYear; ++i) {
			//left element is the rank of the name at this year.
			outputReport += this.ranksInEachYear.get(i).getLeft() + "\t\t\t";
		}
		//Line 3: Print out the percentile of the name's rank in the year
		outputReport += "\n%-ile:\t";
		
		for (int i = 0; i <= this.endYear-this.startYear; ++i) {
			//format to 1 decimal place
			DecimalFormat df = new DecimalFormat("#.#");
			//Get the rank of the name
			Double rank = (double)this.ranksInEachYear.get(i).getLeft();
			//Get the total number of names in that year
			int total = this.ranksInEachYear.get(i).getRight();
			//calculate percentage value
			Double thisRankPercentage = 100*(1-(rank/total));
			outputReport += df.format(thisRankPercentage) + "\t\t\t";
		}
		*/
		String outputReport = "";
		return outputReport;
	}
	
	public List<Triple<Integer,Integer,Double>> getPopularityList() {
		return this.ranksInEachYear;
	}
	
	public int getRankAt(int year) {
		if (year >= this.startYear && year <= this.endYear) {
			return this.ranksInEachYear.get(year-this.startYear).getLeft();
		}
		else return 0;
	}
}
