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

public class PopularityOfName extends Reports {
	
	String name;
	int startYear;
	int endYear;
	String gender;
	
	private List<Pair<Integer,Integer>> mylist;
	
	public PopularityOfName(int startYear, int endYear, String name, String gender, String country, String type) {
		super(null, gender, country, type);
		
		this.name = name;
		this.startYear = startYear;
		this.endYear = endYear;
		this.gender = gender;
		this.mylist = new ArrayList<Pair<Integer,Integer>>();
		for (int thisYear = startYear; thisYear <= endYear; ++thisYear) {
			boolean nameFound = false;
			int rank = 0;
			int thisYearRecordsLength = 0;
			int currentRank = 0;
			for (CSVRecord rec : AnalyzeNames.getFileParser(thisYear, type, country)) {
				if (gender.equals(rec.get(1))) {
					++thisYearRecordsLength;
					++currentRank;
					if (!nameFound) {
						String thisRecName = rec.get(0);
						//int thisRecFrequency = Integer.parseInt(rec.get(2));
						
						if (name.equals(thisRecName)) {
							nameFound = true;
							rank = currentRank;//thisRecFrequency;
						}
					}
				}
			}
			Pair<Integer,Integer> rankAndYearSize = Pair.of(rank,thisYearRecordsLength);
			mylist.add(rankAndYearSize);
		}
	}
	
	public String getReport() { 
		String thisReport = String.format("Popularity report for: %s (%s), from %d to %d.\n", this.name, this.gender, this.startYear, this.endYear);
		thisReport += "Year:\t\t";
		for (int thisYear = this.startYear; thisYear <= this.endYear; ++thisYear) {
			thisReport += thisYear + "\t\t\t";
		}
		thisReport += "\nRank:\t";
		
		for (int i = 0; i <= this.endYear-this.startYear; ++i) {
			thisReport += this.mylist.get(i).getLeft() + "\t\t\t";
		}
		thisReport += "\n%-ile:\t";
		for (int i = 0; i <= this.endYear-this.startYear; ++i) {
			
			DecimalFormat df = new DecimalFormat("#.#");
			Double rank = (double)this.mylist.get(i).getLeft();
			int total = this.mylist.get(i).getRight();
			Double thisRankPercentage = 100*(1-(rank/total));
			thisReport += df.format(thisRankPercentage) + "\t\t\t";
		}
		return thisReport;
	}
}
