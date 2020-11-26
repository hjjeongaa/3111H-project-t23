/**
* TrendInPopularity.java - A subclass of Reports, backbone of task 3
* from. algorithm is execution optimized.
* @author Yuxi Sun
* @version 2.2
*/

package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.*;
import java.util.*;
/**
 * Code for Task 3, 
 * Finding the (names) with largest increase in popularity and 
 * the (names) with largest decrease in popularity over a 
 * specified period of time for the USA data set.
 * @author Yuxi Sun
 *
 */
public class TrendInPopularity extends Reports{
	/**
	 * Entry is a private data structure used to make the year rank paired code more intuitive, it consists of a year and a rank.
	 * @author Yuxi Sun
	 */
	private class Entry{

		private int year;
		private int rank;
		//accessors
		/**
		 * @return year stored in said data structure
		 * @author Yuxi Sun
		 */
		public int getYear(){return this.year;}
		/**
		 * @return rank stored in said data structure
		 * @author Yuxi Sun
		 */
		public int getRank(){return this.rank;}

		//mutators not needed
		/**
		 * used to update datastructure
		 * @param year year to be updated
		 * @param rank rank to be updated
		 * @author Yuxi Sun
		 */
		public void update(int year,int rank){
			this.year = year;
			this.rank = rank;
		}
		//constructor
		/**
		 * Entry Constructor 
		 * @param year year of said datastructure
		 * @param rank rank of said datastructure
		 */
		public Entry(int year, int rank){
			this.year = year;
			this.rank = rank;
		}
	}
	/**
	 * Trend is a private data structure that consists of 2 Entrys and is used in the TrendInPopularity class as a
	 * running record of the largest increase/ decrease in popularity of a name.
	 * Each name has two Vectors of Trends: a rise Vector Trend and a fall Vector trend.
	 * @author Yuxi Sun
	 */
	private class Trend{
		private Entry start;
		private Entry end;
		//accessors
		/**
		 * @return start Entry of Trend
		 * @author Yuxi Sun
		 */
		public Entry getStart(){return this.start;}
		/**
		 * @return end Entry of Trend
		 * @author Yuxi Sun
		 */
		public Entry getEnd(){return this.end;}
		/**
		 * @return the difference of the start Rank with the end Rank so that the return value is how much the rank increased if positive
		 * or how much the rank decreased if negative since a larger rank value means a lower rank.
		 * @author Yuxi Sun
		 */
		public int getChange(){

			return this.start.getRank() - this.end.getRank();
		}

		//mutators
		/**
		 * Changes the start Entry of trend
		 * @param year new start year
		 * @param rank new start rank
		 * @author Yuxi Sun
		 */
		public void setStart(int year, int rank){start.update(year,rank);}
		/**
		 * Changes the end Entry of trend
		 * @param year new end year
		 * @param rank new end rank
		 * @author Yuxi Sun
		 */
		public void setEnd(int year, int rank){end.update(year,rank);}
		//Constructors
		/**
		 * 
		 * @param start of Entry type specifying the start rank and year
		 * @param end of Entry type specifying the end rank and year
		 * @author Yuxi Sun
		 */
		public Trend(Entry start, Entry end){
			this.start = start;
			this.end = end;
		}
	}
	/**
	 * Name is a data structure that uses Trend to keep track of the running largest increase and largest decrease in popularity of a specific name.
	 * Consist of a name and its risingTrend and fallingTrend
	 * Sorting and management of Trends (including updating and verification) should be performed here.
	 * @author Yuxi Sun
	 */
	private class Name{
		private String name;
		//variables used for finding names largest rise in ranks
		/**
		 * rise contains a non-trivial list of all Trends that has the same increase in ranks as the largest.
		 * Non-Trivial refers a non-zero increase in rank
		 * rise in ranks (assumed positive).
		 * @author Yuxi Sun
		 */
		private Vector<Trend> rise;			
		private Entry lowest;				//stores the Entry of the lowest rank seen so far (highest value)
		private int lastYear; 				// used for maintaining update integrity

		//variables used for finding names largest fall in ranks
		/**
		 * fall contains a non-trivial list of all Entries (year,rank) that has the same decrease in ranks as the largest
		 * Non-Trivial refers a non-zero decrease in rank
		 * fall in ranks (assumed negative).
		 * @author Yuxi Sun
		 */
		private Vector<Trend> fall;	
		private Entry highest;				//stores the Entry of the highest rank seen so far (lowest value)

		//accessors
		/**
		 * Checks if Rise is not empty
		 * @return true if rise is not empty
		 * @author Yuxi Sun
		 */
		public boolean hasRise(){return rise.size()>0;};	
		/**
		 * Checks if Fall is not empty
		 * @return true if fall is not empty
		 * @author Yuxi Sun
		 */
		public boolean hasFall(){return fall.size()>0;};
		/**
		 * @return If there is a rising trend found, gets the value of the current largest rise in popularity, if a rising trend hasn't been so far, 0 is returned.
		 * @author Yuxi Sun
		 */
		public int getRise(){
			if (hasRise())
				return rise.get(0).getChange();
			else
				return 0;//if returned value is negative or 0, means no valid input found
		}
		/**
		 * @return if there is a falling trend found, gets the value of the current largest fall in popularity, if a fall trend hasn't been so far, 0 is returned.
		 * @author Yuxi Sun
		 */
		public int getFall(){
			if (hasFall())
				return fall.get(0).getChange();
			else return 0;//if returned value is positive or 0, means no valid input found
		}
		/**
		 * @return name a String represeting the name the current instance is tracking.
		 * @author Yuxi Sun
		 */
		public String getName(){return this.name;}
		
		//mutators
		/**
		 * Calls the updateRise and updateFall functions to update the relevant variable.
		 * @param year the year the name was found in and should be strictly higher then the years seen previously.
		 * @param rank the rank of name in the corresponding year.
		 * @author Yuxi Sun
		 */
		
		public void addYear(int year,int rank){
			if (lastYear>=year){ // if year occurred before the last seen year then don't do anything, this is a faulty entry!
				return;
			}
			updateRise(year, rank);
			updateFall(year, rank);
			this.lastYear = year; // update last year
		}
		
		/**
		 * Updates the Rising variables to only keep data for calculating the largest increase in rank of the potential for a largest increase in rank.
		 * @param year the year the name was found in and should be strictly higher then the years seen previously.
		 * @param rank the rank of name in the corresponding year.
		 * @author Yuxi Sun
		 */
		
		private void updateRise(int year,int rank){
			if(rank >= lowest.getRank()){
				//a lower rank has been found (since a higher numeric value of rank means a lower actual rank)
				//it is set to update lowest even if the rank is the same as doing so will mean if a large increase in rank is found, the period of rise would
				//be smaller then otherwise.
				lowest = new Entry(year,rank);
			}else if(rise.size() == 0){
				//case of no found valid rising trend yet/ so far
				if (rank < lowest.getRank()){
					//current entry has a non trivially higher rank then the lowest rank seen so far and is thus an increase in rank for said name. Update/ Initialize rise Trend
					rise.add(new Trend(lowest,new Entry(year,rank)));
				}
			}else if((lowest.getRank() - rank) > getRise()){
				//a larger rise found, clear current list and update accordingly.
				rise.clear();
				rise.add(new Trend(lowest, new Entry(year,rank)));
			}else if((lowest.getRank() - rank) ==  getRise()){
				//period where trend is the same as the current highest increase trend found.
				rise.add(new Trend(lowest, new Entry(year,rank)));
			}
			//otherwise don't do anything
		}
		
		/**
		 * Updates the Falling variables to only keep data for calculating the largest decrease in rank of the potential for a largest decrease in rank.
		 * @param year the year the name was found in and should be strictly higher then the years seen previously.
		 * @param rank the rank of name in the corresponding year.
		 * @author Yuxi Sun
		 */
		private void updateFall(int year,int rank){
			if(rank <= highest.getRank()){
				//a higher rank has been found (since a lower numeric value of rank means a higher actual rank)
				highest = new Entry(year,rank);
			}else if(fall.size() == 0){
				//case of no found valid falling trend yet
				if (lowest.getRank() < rank){
					//current entry has a lower rank then the highest rank seen so far and is a decrease in rank for said name. Update Trend
					fall.add(new Trend(highest,new Entry(year,rank)));
				}

			}
			if((highest.getRank() - rank) < getFall()){
				//a larger fall found, clear current list and update accordingly.
				fall.clear();
				fall.add(new Trend(highest, new Entry(year,rank)));
			}else if((highest.getRank() - rank) ==  getFall()){
				//period where trend is the same as the current highest decrease trend found.
				fall.add(new Trend(highest, new Entry(year,rank)));
			}
			//otherwise don't do anything
		}
		//constructor
		/**
		 * Constructor of Name
		 * @param name name being tracked
		 * @param year first year of appearance 
		 * @param rank rank in first year of appearance
		 * @author Yuxi Sun
		 */
		public Name(String name, int year, int rank){
			this.name = name;
			//initializing empty Vectors
			rise = new Vector<Trend>();
			fall = new Vector<Trend>();
			highest = new Entry(year,rank);
			lowest = new Entry(year,rank);
			lastYear = year;
		}
	}
	private int startYear;
	private int endYear;
	Vector<Name> setOfLargestRise;
	Vector<Name> setOfLargestFall;
	//Note: data should be validated before being passed to a constructor/ mutator
	
	/**
	 * mutators: Note that these will not be used in the current format of T3 and export
	 * 
	 * @author Yuxi Sun
	 */

	/**
	 * 
	 * @param startYear new start year of period to be parsed (inclusive)
	 * @param endYear new end year of period to be parsed (inclusive)
	 * @param gender new gender of specefied data set 
	 * @param country new country of specefied data set 
	 * @param type new type of specefied data set [human or pet] 
	 * @author Yuxi Sun
	 */
	public void modify(int startYear, int endYear, String gender, String country, String type){
		super.modify(country, type, gender);
		this.startYear = startYear;
		this.endYear = endYear;
		generate();
	}
	/**
	 *  Constructor
	 * @param startYear start year of period to be parsed (inclusive)
	 * @param endYear end year of period to be parsed (inclusive)
	 * @param gender gender of specefied data set 
	 * @param country country of specefied data set 
	 * @param type type of specefied data set [human or pet] 
	 * @author Yuxi Sun
	 */
	public TrendInPopularity(int startYear, int endYear, String gender, String country, String type){
		//Call parent constructor
		super(null, gender, country, type);
		this.startYear = startYear;
		this.endYear = endYear;
		generate();
	}
	/**
	 * Fetch all relevant data from database and process them to get a list of largest rise and fall for each name.
	 * If names no not appear in a year of a dataset after its discovery, the year for said name will be ignored e.g.
	 * 	if David appeared in 2000 but not in 2001 for said dataset, David's Name Object will not be updated.
	 * Note that the years are ranked using Standard Competition Ranking.
	 * The output is stored in the parent Superclass ReportLog variable oReport and oHTML for the controller to fetch and output. 
	 * @author Yuxi Sun
	 */
	public void generate(){		
		HashMap<String,Name> seenNames = new HashMap<String,Name>();
		//collecting data from data sets and doing simple preprocessing to get the max rise/ fall of each Name
		super.setTask("Task 3");
		//Iterate through all years in range inclusive
		for(int year = this.startYear; year<=this.endYear;++year){
			//set up variables required for standard competition ranking
			int rank = 1;
			int sameRankCount = 0; //stores the number of names seen with the same rank (frequency) as the current name (exclusive)
			int lastFreq = 0;//stores the last seen frequency
			boolean firstEntry = true;
			//iterates through year's data
			for(CSVRecord rec : AnalyzeNames.getFileParser(year, this.gettype(), this.getcountry())){
				String gender = rec.get(1);
				if (!gender.equals(super.getgender())){
					continue; //if the name is not of specified gender then skip
				}
				//calculating the names rank in said year.
				if (firstEntry){
					//if this is the first entry (boundary case)
					lastFreq = Integer.parseInt(rec.get(2)); //setting last Freq for else case
					firstEntry = false;
				}else{
					//not the first entry
					if (lastFreq == Integer.parseInt(rec.get(2)))
						++sameRankCount;
					else{
						//since we assume the file is grouped by gender and 
						//order in descending order on frequency, no extra check is required 
						rank = rank + sameRankCount + 1;
						sameRankCount = 0;//reset sameRankCount
					}
					//update lastFreq (interaction variable)
					lastFreq = Integer.parseInt(rec.get(2));
				}
				//Getting names with specified gender
				String name = rec.get(0);

				//check if name is in seenNames
				if (seenNames.containsKey(name)){
					//if it is then update the mapped Name object
					seenNames.get(name).addYear(year,rank);
				}else{
					//if it isn't then create a new entry 
					seenNames.put(name,new Name(name,year,rank));
				}
			}
		}
		filter(seenNames);
	}
	/**		
	 * Filters the data generate from prepare to keep only the names with the largest rise or largest fall
	 * @param seenNames
	 * @author Yuxi Sun
	 */
	private void filter(HashMap<String,Name> seenNames) {
		/**
		 */
		setOfLargestRise  = new Vector<Name>();
		setOfLargestFall  = new Vector<Name>();
		//initialize the sets for the iterations
		Iterator nameiter = seenNames.entrySet().iterator();
		if(nameiter.hasNext()){
			Map.Entry namePair = (Map.Entry)nameiter.next();
			setOfLargestRise.add((Name)namePair.getValue());
			setOfLargestFall.add((Name)namePair.getValue());
		}
		//parse names to get largest change.
		while (nameiter.hasNext()){
			Map.Entry namePair = (Map.Entry)nameiter.next();
			Name name = (Name)namePair.getValue();
			//rise
			if(name.getRise()>0){//if non-trival rise
				if (name.getRise() > setOfLargestRise.get(0).getRise()){
					//a larger rise is found, reset list
					setOfLargestRise.clear();
					setOfLargestRise.add(name);
				}
				else if (name.getRise() == setOfLargestRise.get(0).getRise()){
					//a name with the same largest rise is found
					setOfLargestRise.add(name);
				}
			}
			//fall
			if(name.getFall()<0){//if non-trival fall
				if (name.getFall() < setOfLargestFall.get(0).getFall()){
					//a larger fall is found, reset list
					setOfLargestFall.clear();
					setOfLargestFall.add(name);
				}
				else if (name.getFall() == setOfLargestFall.get(0).getFall()){
					//a name with the same largest fall is found
					setOfLargestFall.add(name);
				}
			}
		}
		//write outputs to superclass
		writeoReport();
		writeoHTML();
	}
	/**
	 * Writes the summary of said report to Reportlog Superclass. The content will be a String containting the html code representing both the inputs and outputs of said query instance.
	 * It will show all Names with the largest increase in popularity or largest fall in popularity.
	 * Note that if a name has multiple Trends with the largest increase in popularity or fall in popularity, only the most recent trend will be listed
	 * @author Yuxi Sun
	 */
	private void writeoHTML() {
		//generate table html
		String table = "";
		String row = "<tr>\n\t<td>%s</td>\n\t<td>%s</td>\n\t<td>%s</td>\n\t<td>%s</td>\n\t<td>%s</td>\n\t<td>%s</td>\n</tr>\n";
		//writing rising values to Super class oReport variable.
		Iterator<Name> rise = setOfLargestRise.iterator();
		while (rise.hasNext()){
			Name nextRise = (Name)rise.next();
		 	Trend mostRecent = nextRise.rise.lastElement(); // only add the most recent trend
		 	table += String.format(row, nextRise.getName(), mostRecent.start.getYear(),mostRecent.start.getRank(),mostRecent.end.getYear(),mostRecent.end.getRank(),mostRecent.getChange());
		 }

		 //writing falling values to Super class oReport variable.
		 Iterator<Name> fall = setOfLargestFall.iterator();
		 while (fall.hasNext()){
		 	Name nextFall = (Name)fall.next();
		 	Trend mostRecent = nextFall.fall.lastElement(); // only add the most recent trend
		 	table += String.format(row, nextFall.getName(), mostRecent.start.getYear(),mostRecent.start.getRank(),mostRecent.end.getYear(),mostRecent.end.getRank(),mostRecent.getChange());
		 }
		 
		//grabbing template
    	FileResource fr = new FileResource("export/t3htmlTemplate.txt");
    	String template = fr.asString();
    	//substituting values into the template
    	String oHTML = String.format(template, super.gettype(), super.getcountry(), super.getgender(), Integer.toString(this.startYear), Integer.toString(this.endYear),table);
		super.setHTML(oHTML);
	}
	/**
	 * This function parses through the filtered content and writes it to the super classes output string oReport. 
	 * It will show all Names with the largest increase in popularity or largest fall in popularity.
	 * Note that if a name has multiple Trends with the largest increase in popularity or fall in popularity, only the most recent trend will be listed
	 * @author Yuxi Sun
	 */
	private void writeoReport() {
    	FileResource fr = new FileResource("export/t3oReportTemplate.txt");
    	String template = fr.asString();
		String oReport = String.format(template, super.gettype(),super.getcountry(),super.getgender(), this.startYear, this.endYear);
		super.setoReport(oReport);
	}
	/**
	 * 
	 * @return HashMap of Column (String) to Vector of (entries String) for the Console to fetch the results of the output/
	 * It will show all Names with the largest increase in popularity or largest fall in popularity.
	 * Note that if a name has multiple Trends with the largest increase in popularity or fall in popularity, only the most recent trend will be listed
	 * @author Yuxi Sun
	 */
	public HashMap<String,Vector<String>> getResults(){
		//setting up output data structure
		HashMap<String,Vector<String>> output = new HashMap<String,Vector<String>>();
		// name
		output.put("name", new Vector<String>());
		// lowest_rank
		output.put("startRank", new Vector<String>());
		// lowest_year
		output.put("startYear", new Vector<String>());
		// highest_rank
		output.put("endRank", new Vector<String>());
		// highest_year
		output.put("endYear", new Vector<String>());
		// trend
		output.put("trend", new Vector<String>());

		//writing rising values to Super class oReport variable.
		Iterator rise = setOfLargestRise.iterator();
		while (rise.hasNext()){
			//putting variables into output structure
			Name nextRise = (Name)rise.next();
			output.get("name").add(nextRise.getName());
			Trend mostRecent = nextRise.rise.lastElement();  // only add the most recent trend
			output.get("startRank").add(String.valueOf(mostRecent.start.getRank()));
			output.get("startYear").add(String.valueOf(mostRecent.start.getYear()));
			output.get("endRank").add(String.valueOf(mostRecent.end.getRank()));
			output.get("endYear").add(String.valueOf(mostRecent.end.getYear()));
			output.get("trend").add(String.valueOf(mostRecent.getChange()));
		}
		//writing falling values to Super class oReport variable.
		Iterator fall = setOfLargestFall.iterator();
		while (fall.hasNext()){
			Name nextFall = (Name)fall.next();
			output.get("name").add(nextFall.getName());
			Trend mostRecent = nextFall.fall.lastElement(); // only add the most recent trend
			output.get("startRank").add(String.valueOf(mostRecent.start.getRank()));
			output.get("startYear").add(String.valueOf(mostRecent.start.getYear()));
			output.get("endRank").add(String.valueOf(mostRecent.end.getRank()));
			output.get("endYear").add(String.valueOf(mostRecent.end.getYear()));
			output.get("trend").add(String.valueOf(mostRecent.getChange()));
		}
		return output;
	}
}