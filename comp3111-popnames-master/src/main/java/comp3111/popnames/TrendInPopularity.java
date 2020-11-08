/**
* TrendInPopularity.java - A subclass of Reports, backbone of task 3
* from.
* @author Yuxi Sun
* @version 1.0
*/

package comp3111.popnames;

import org.apache.commons.csv.*;
import edu.duke.*;
import java.time.LocalDateTime;  
import java.util.*;

public class TrendInPopularity extends Reports{
	public class Entry{
		private int startYear;
		private int endYear;
		private int startRank;
		private int endRank;

		//accessors
		public int getStartYear()	{return this.startYear;};
		public int getEndYear()		{return this.endYear;};

		public int getChange(){
			/** return the difference of the end Rank with the start 	Rank*/
			return this.endRank - this.startRank;
		};
		public int getStartRank()	{return this.startRank;};
		public int getEndRank()		{return this.endRank;};

		//mutators
		public void setStartYear(int startYear)	{this.startYear = startYear;};
		public void setEndYear(int endYear)		{this.endYear = endYear;};
		public void setStartRank(int startRank)	{this.startRank = startRank;};
		public void setEndRank(int endRank)		{this.endRank = endRank;};
		public void update(int startYear, int endYear, int startRank,int endRank){
			this.startYear = startYear;
			this.endYear = endYear;
			this.startRank = startRank;
			this.endRank = endRank;
		};
		//constructor
		public Entry(int year,int rank){
			this.startYear = year;
			this.endYear = year;
			this.startRank = rank;
			this.endRank = rank;
		};
	}
	public class Name{
		//can optmize to be O(1) storage, do during stage 1 refactoring
		// name: [Entry]
		//variables
		private String name;

		private Entry largestRise;
		private int lowestRank;
		private int lowestYear;

		private Entry largestFall;
		private int highestRank;
		private int highestYear;

		private int startYear;//first appearence
		private int endYear;// used in TrendInPopularity.generate() for logistical reasons.
		//accessors
		public String getName()		{return this.name;};
		public int getLargestRise()	{return this.largestRise.getChange();};
		public int getLargestFall()	{return this.largestFall.getChange();};
		public int getStartYear()	{return this.startYear;};
		public int getEndYear()		{return this.endYear;};

		//constructor
		public Name(String name, int year, int rank){
			//initializing new dynamic array to store entries
			this.name = name;
			this.startYear = year;
			this.endYear = year;
			//need to change into vector for the case that multiple occurences appear. (show all occurences)
			this.largestRise = new Entry(year,rank);
			this.lowestRank = rank;
			this.lowestYear = year;
			//need to change into vector for the case that multiple occurences appear. (show all occurences)
			this.largestFall = new Entry(year,rank);
			this.highestRank = rank;
			this.highestYear = year;

		};

		public void addYear(int year, int rank){
			//update largestRise variables
			this.endYear = year;
			if (rank <= this.lowestRank){
				//set so that it records the most recent trends (remove = to set to get the largest duration)
				this.lowestRank = rank;
				this.lowestYear = year;
			}
			if (this.largestRise.getChange() < (rank - this.lowestRank)){
				/**Set so that it records the most recent trends (add = to set to get the largest duration)
				 * If there is a larger increase in rank than the previously recorded period, then we update the largestRise object.
				 */
				this.largestRise.update(this.lowestYear, year, this.lowestRank, rank);
			}

			//update largestFall variables
			if (rank>=this.lowestRank){
				//set so that it records the most recent trends (remove = to set to get the largest duration)
				this.highestRank = rank;
				this.highestYear = year;
			}
			if (this.largestFall.getChange() > (rank - this.highestRank)){
				/**Set so that it records the most recent trends (add = to set to get the largest duration)
				 * If there is a larger decrease in rank than the previously recorded period, then we update the largestFall object.
				 */
				this.largestFall.update(this.highestYear, year, this.highestRank, rank);
			}

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
		generate();
	}
	
	public TrendInPopularity(int startYear, int endYear, String gender, String country, String type){
		//Call parent constructor
		super(null, gender, country, type);
		this.startYear = startYear;
		this.endYear = endYear;
		generate();
	}

	//This generates the oReport string
	public void generate(){
		String oReport = "";
		Vector<Name> seenNames = new Vector<Name>();
		// int range = this.endYear-this.startYear+1;
		System.out.println(super.getgender());
		for(int year = this.startYear; year<=this.endYear;++year){
			//Iterate through all years in range inclusive
			int rank = 1;
			for(CSVRecord rec : AnalyzeNames.getFileParser(year, this.gettype(), this.getcountry())){
				//check if record is in array
				//if it isnt then create a new array entry
				String gender = rec.get(1);
				if (!gender.equals(super.getgender())){
					continue;
				}

				String name = rec.get(0);
				// int freq = Integer.parseInt(rec.get(2));
				//iterate vector to see if entry exists
				//optimization possible by ordering names using name as key and performing updating after a full pass of the dataset.
				Iterator value = seenNames.iterator();
				while (value.hasNext()){
					Name next = (Name)value.next();
					if (name.equals(next.getName())){
						next.addYear(year,rank);
						break;
					}
				}
				//if it doesn't then add a new name to the vector
				seenNames.add(new Name(name,year,rank));
				rank += 1;
			}
			Iterator value = seenNames.iterator();
			while (value.hasNext()){
				Name next = (Name)value.next();
				if (next.getEndYear() != year){
					//if name was not updated in this year, set rank to number of ranks+1
					next.addYear(year,rank);
				}
			}
		}
		Vector<Name> setOfLargestRise = new Vector<Name>();
		Vector<Name> setOfLargestFall = new Vector<Name>();

		//intialize the sets for the iterations
		Iterator value = seenNames.iterator();
		if(value.hasNext()){
			Name name = (Name)value.next();
			setOfLargestRise.add(name);
			setOfLargestFall.add(name);
		}
		//parse names to get largest change.
		while (value.hasNext()){
			Name name = (Name)value.next();
			//rise
			if (name.largestRise.getChange()>setOfLargestRise.get(0).largestRise.getChange()){
				setOfLargestRise.clear();
				setOfLargestRise.add(name);
			}
			else if (name.largestRise.getChange() == setOfLargestRise.get(0).largestRise.getChange()){
				setOfLargestRise.add(name);
			}

			//fall
			if (name.largestFall.getChange()<setOfLargestFall.get(0).largestFall.getChange()){
				setOfLargestFall.clear();
				setOfLargestFall.add(name);
			}
			else if (name.largestFall.getChange() == setOfLargestFall.get(0).largestFall.getChange()){
				setOfLargestFall.add(name);
			}
		}
		Iterator rise = setOfLargestRise.iterator();
		while (rise.hasNext()){
			Name nextRise = (Name)rise.next();
			oReport += nextRise.getName() + " | Start Year: " + nextRise.largestRise.getStartYear()+" Start Rank:" + nextRise.largestRise.getStartRank()
				+ " | End Year: " + nextRise.largestRise.getEndYear()+ " End Rank" + nextRise.largestRise.getEndRank()
				+ " | Trend : " + nextRise.largestRise.getChange() +"\n";

		}
		Iterator fall = setOfLargestFall.iterator();
		while (fall.hasNext()){
			Name nextFall = (Name)fall.next();

			oReport += nextFall.getName() + " | Start Year: " + nextFall.largestFall.getStartYear()+" Start Rank:" + nextFall.largestFall.getStartRank()
				+ " | End Year: " + nextFall.largestFall.getEndYear()+ " End Rank" + nextFall.largestFall.getEndRank()
				+ " | Trend : " + nextFall.largestFall.getChange() +"\n";
		}
		super.setoReport(oReport);
	}
}