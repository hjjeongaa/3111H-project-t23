/**
* TrendInPopularity.java - A subclass of Reports, backbone of task 3
* from. algorithm is execution optimized.
* @author Yuxi Sun
* @version 2.1
*/

package comp3111.popnames;

import org.apache.commons.csv.*;
import edu.duke.*;
import java.time.LocalDateTime;  
import java.util.*;

public class TrendInPopularity extends Reports{
	public class Entry{
		/**
		 * Consists of a year and it's corresponding rank
		 */
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

	public class Trend{
		/**
		 * Consists of 2 Entrys.
		 */
		private Entry start;
		private Entry end;
		//accessors
		public Entry getStart(){return this.start;}
		public Entry getEnd(){return this.end;}
		public int getChange(){
			/**
			 * return the difference of the start Rank with the end Rank so that the return value is how much the rank increased
			 * if negative, it mease the rank decreased
			 */
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

	public class Name{
		/**
		 * Consist of a name and its risingTrend and fallingTrend
		 * Sorting and management of Trends should be performed here.
		 */
			
		private String name;
		private Vector<Trend> rise;			
		/**
		 * rise contains a list of all Entries (year,rank) that has the same increase in ranks as the largest
		 * rise in ranks (assumed positive).
		 */
		private Entry lowest;				//stores the Entry of the lowest rank seen so far (highest value)

		private Vector<Trend> fall;	
		/**
		* fall contains a list of all Entries (year,rank) that has the same decrease in ranks as the largest
		* fall in ranks (assumed negative).
		*/
		private Entry highest;				//stores the Entry of the highest rank seen so far (lowest value)

		//accessors
		public boolean hasRise(){return rise.size()>0;};
		public boolean hasFall(){return fall.size()>0;};
		public int getRise(){
			if (hasRise())
				return rise.get(0).getChange();
			else
				return 0;//if returned value is negative, means no valid input found
		}
		public int getFall(){
			if (hasFall())
				return fall.get(0).getChange();
			else return 0;//if returned value is positive, means no valid input found
		}
		public String getName(){return this.name;}
		//mutators
		public void addYear(int year,int rank){
			/**
			*updates Trends of said person
			*/
			updateRise(year, rank);
			updateFall(year, rank);
		}
		private void updateRise(int year,int rank){
			/**
			*update rising
			*/
			if(rank >= lowest.getRank()){
				//a lower rank has been found (since a higher neumeric value of rank means a lower actual rank)
				lowest.update(year,rank);
			}else if(rise.size() == 0){
				//case of no found valid rising trend yet
				if (lowest.getRank() > rank){
					//current entry has a higher rank then the lowest rank seen so far and is an increase in rank for said name. Update Trend
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
		private void updateFall(int year,int rank){
			/**
			*update falling
			*/
			if(rank <= highest.getRank()){
				//a higher rank has been found (since a lower neumeric value of rank means a higher actual rank)
				highest.update(year,rank);
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
			rise = new Vector<Trend>();
			//Fall contains a list of all Entries (year,rank) that has the same decrease in ranks as the largest fall in ranks.
			fall = new Vector<Trend>();
			highest = new Entry(year,rank);
			lowest = new Entry(year,rank);
		}
	}
	private int startYear;
	private int endYear;
	//mutators
	//Note: data should be validated before being passed to a constructor/ mutator
	public void modify(int startYear, int endYear, String gender, String country, String type){
		super.modify(country, type, gender);
		this.startYear = startYear;
		this.endYear = endYear;
	}
	public TrendInPopularity(int startYear, int endYear, String gender, String country, String type){
		//Call parent constructor
		super(null, gender, country, type);
		this.startYear = startYear;
		this.endYear = endYear;
	}
	public void generate(){
		/**
		 * Fetch all relevant data from database and process them to get a list of largest rise and fall for each name
		 */
		HashMap<String,Name> seenNames = new HashMap<String,Name>();
		Vector<Name> setOfLargestRise = new Vector<Name>();
		Vector<Name> setOfLargestFall = new Vector<Name>();
		//collecting data from datasets and doing simple preprocessing to get the maxes rise/ fall of each Name
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
						//order in decending order on frequency, no extra check is required 
						rank = rank + sameRankCount + 1;
						sameRankCount = 0;//reset sameRankCount
					}
					//update lastFreq (interation variable)
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

		/**
		 * filter the data generate from prepare to keep only the names with the largest rise or largest fall
		 */
		//intialize the sets for the iterations
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
		/**
		 * Generates string from the values generated from preprocess.
		 */

		String oReport = "";
		Iterator rise = setOfLargestRise.iterator();
		while (rise.hasNext()){
			Name nextRise = (Name)rise.next();
			String temp = nextRise.getName();
			Trend mostRecent = nextRise.rise.lastElement();
			temp += " | Start Year: " + mostRecent.start.getYear();
			temp += " Start Rank:" + mostRecent.start.getRank();
			temp += " | End Year: " + mostRecent.end.getYear();
			temp += " End Rank: " + mostRecent.end.getRank();
			temp += " | Trend : " + mostRecent.getChange() +"\n";
			oReport += temp;
		}

		Iterator fall = setOfLargestFall.iterator();
		while (fall.hasNext()){
			Name nextFall = (Name)fall.next();
			String temp = nextFall.getName();
			if (nextFall.fall.size() == 0) continue;
			Trend mostRecent = nextFall.fall.lastElement();
			temp += " | Start Year: " + mostRecent.start.getYear();
			temp += " Start Rank:" + mostRecent.start.getRank();
			temp += " | End Year: " + mostRecent.end.getYear();
			temp += " End Rank: " + mostRecent.end.getRank();
			temp += " | Trend : " + mostRecent.getChange() +"\n";
			oReport += temp;
		}
		super.setoReport(oReport);
	}
}