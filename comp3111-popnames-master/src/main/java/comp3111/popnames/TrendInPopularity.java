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
 *  the (names) with largest decrease in popularity over a 
 *  specified period of time for the USA data set.
 * @author Yuxi Sun
 *
 */
public class TrendInPopularity extends Reports{
	/**
	 * Entry is a private data structure used to make the year rank paired code more intuitive. 
	 */
	private class Entry{

		private int year;
		private int rank;
		//accessors
		public int getYear(){return this.year;}
		public int getRank(){return this.rank;}
		//mutators not needed
		public void update(int year,int rank){
			this.year = year;
			this.rank = rank;
		}
		//constructor
		public Entry(int year, int rank){
			this.year = year;
			this.rank = rank;
		}
	}
	/**
	 * Trend is a private data structure that consists of 2 Entrys and is used in the TrendInPopularity class as a
	 * running record of the largest increase/ decrease in popularity of a name (such that each name has two Trends).
	 */
	private class Trend{
		private Entry start;
		private Entry end;
		//accessors
		public Entry getStart(){return this.start;}
		public Entry getEnd(){return this.end;}
		/**
		 * return the difference of the start Rank with the end Rank so that the return value is how much the rank increased
		 * if positive, and vice versa since a larger rank value means a lower rank.
		 */
		public int getChange(){

			return this.start.getRank() - this.end.getRank();
		}

		//mutators
		public void setStart(int year, int rank){start.update(year,rank);}
		public void setEnd(int year, int rank){end.update(year,rank);}
		//Constructors
		public Trend(Entry start, Entry end){
			this.start = start;
			this.end = end;
		}
	}
	/**
	 * Name is a data structure that uses Trend to keep track of the running largest increase and largest decrease in popularity of a specific name.
	 * Consist of a name and its risingTrend and fallingTrend
	 * Sorting and management of Trends (including updating and verification) should be performed here.
	 */
	private class Name{
		private String name;
		/**
		 * rise contains a non-trivial list of all Entries (year,rank) that has the same increase in ranks as the largest
		 * rise in ranks (assumed positive).
		 */
		private Vector<Trend> rise;			
		private Entry lowest;				//stores the Entry of the lowest rank seen so far (highest value)
		private int lastYear; 				// used for maintaining update integrity
		/**
		* fall contains a non-trivial list of all Entries (year,rank) that has the same decrease in ranks as the largest
		* fall in ranks (assumed negative).
		*/
		private Vector<Trend> fall;	
		private Entry highest;				//stores the Entry of the highest rank seen so far (lowest value)

		//accessors
		public boolean hasRise(){return rise.size()>0;};	//checks if Rise is not empty
		public boolean hasFall(){return fall.size()>0;};	//checks if Fall is not empty
		/**
		 * @return if there is a rising trend found, gets the value of the current largest rise in popularity
		 * @return if a rising trend hasn't been so far, 0 is returned.
		 */
		public int getRise(){
			if (hasRise())
				return rise.get(0).getChange();
			else
				return 0;//if returned value is negative or 0, means no valid input found
		}
		/**
		 * @return if there is a falling trend found, gets the value of the current largest fall in popularity
		 * @return if a fall trend hasn't been so far, 0 is returned.
		 */
		public int getFall(){
			if (hasFall())
				return fall.get(0).getChange();
			else return 0;//if returned value is positive or 0, means no valid input found
		}
		/**
		 * @return name of current data instance
		 */
		public String getName(){return this.name;}
		
		//mutators
		/**
		 * Calls the updateRise and updateFall functions to update the relevant variable.
		 * @param year the year the name was found in and should be strictly higher then the years seen previously.
		 * @param rank the rank of name in the corresponding year.
		 * 
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
		 *  Updates the Rising variables
		 * @param year the year the name was found in and should be strictly higher then the years seen previously.
		 * @param rank the rank of name in the corresponding year.
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
		 *  Updates the Falling variables
		 * @param year the year the name was found in and should be strictly higher then the years seen previously.
		 * @param rank the rank of name in the corresponding year.
		 */
		private void updateFall(int year,int rank){
			/**
			*update falling
			*/
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
	
	//mutators
	public void modify(int startYear, int endYear, String gender, String country, String type){
		super.modify(country, type, gender);
		this.startYear = startYear;
		this.endYear = endYear;
		generate();
	}
	/**
	 *  Constructor
	 * @param startYear start year of period (inclusive)
	 * @param endYear end year of period (inclusive)
	 * @param gender gender of question
	 * @param country country of data set
	 * @param type type of data set (human/pet)
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
	 * The output is stored in the parent Superclass variable oReport for the controller to fetch and output. 
	 */
	public void generate(){		
		HashMap<String,Name> seenNames = new HashMap<String,Name>();
		//collecting data from datasets and doing simple preprocessing to get the max rise/ fall of each Name
		this.super.setTask("Task 3");
		for(int year = this.startYear; year<=this.endYear;++year){
			//Iterate through all years in range inclusive
			int rank = 1;
			int sameRankCount = 0; //stores the number of names seen with the same rank (frequency) as the current name (exclusive)
			//iterates through year's data
			int lastFreq = 0;//stores the last seen frequency
			boolean firstEntry = true;
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
	private void filter(HashMap<String,Name> seenNames) {
		/**
		 * filter the data generate from prepare to keep only the names with the largest rise or largest fall
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
			if(name.getRise()>0){
				if (name.getRise() > setOfLargestRise.get(0).getRise()){
					setOfLargestRise.clear();
					setOfLargestRise.add(name);
				}
				else if (name.getRise() == setOfLargestRise.get(0).getRise()){
					setOfLargestRise.add(name);
				}
			}
			//fall
			if(name.getFall()<0){
				if (name.getFall() < setOfLargestFall.get(0).getFall()){
					setOfLargestFall.clear();
					setOfLargestFall.add(name);
				}
				else if (name.getFall() == setOfLargestFall.get(0).getFall()){
					setOfLargestFall.add(name);
				}
			}
		}
		write();
	}
	/**
	 * This function parses through the filtered content and writes it to the super classes output format . 
	 */
	private void write() {
		super.setoReport(this.name +" " +this.gender + " " + this.);

		// String oReport = "";
		// //writing rising values to Super class oReport variable.
		// oReport+="Rising\n";
		// Iterator rise = setOfLargestRise.iterator();
		// while (rise.hasNext()){
		// 	Name nextRise = (Name)rise.next();
		// 	String temp = nextRise.getName();
		// 	Trend mostRecent = nextRise.rise.lastElement();
		// 	temp += " | Start Year: " + mostRecent.start.getYear();
		// 	temp += " Start Rank:" + mostRecent.start.getRank();
		// 	temp += " | End Year: " + mostRecent.end.getYear();
		// 	temp += " End Rank: " + mostRecent.end.getRank();
		// 	temp += " | Trend : " + mostRecent.getChange() +"\n";
		// 	oReport += temp;
		// }

		// //writing falling values to Super class oReport variable.
		// oReport+="Falling\n";
		// Iterator fall = setOfLargestFall.iterator();
		// while (fall.hasNext()){
		// 	Name nextFall = (Name)fall.next();
		// 	String temp = nextFall.getName();
		// 	if (nextFall.fall.size() == 0) continue;
		// 	Trend mostRecent = nextFall.fall.lastElement();
		// 	temp += " | Start Year: " + mostRecent.start.getYear();
		// 	temp += " Start Rank:" + mostRecent.start.getRank();
		// 	temp += " | End Year: " + mostRecent.end.getYear();
		// 	temp += " End Rank: " + mostRecent.end.getRank();
		// 	temp += " | Trend : " + mostRecent.getChange() +"\n";
		// 	oReport += temp;
		// }
		super.setoReport(oReport);
	}

	public HashMap<String,Vector<String>> getResults(){
		//setting up output datastructure
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
			Trend mostRecent = nextRise.rise.lastElement(); // getting the most recent trend
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
			if (nextFall.fall.size() == 0) continue;
			Trend mostRecent = nextFall.fall.lastElement();
			//changing the order of outputting to suit the Task3 description 
			output.get("startRank").add(String.valueOf(mostRecent.start.getRank()));
			output.get("startYear").add(String.valueOf(mostRecent.start.getYear()));
			output.get("endRank").add(String.valueOf(mostRecent.end.getRank()));
			output.get("endYear").add(String.valueOf(mostRecent.end.getYear()));
			output.get("trend").add(String.valueOf(mostRecent.getChange()));
		}
		return output;
	}
}