/**
* TrendInPopularity.java - A subclass of Reports, backbone of task 3
* from. algorithm is execution optimized.
* @author Yuxi Sun
* @version 2.0
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
			 * return the difference of the end Rank with the start Rank
			 */
			return this.end.getRank() - this.start.getRank();
		}
		public boolean isNotValid(){
			return this.end.getYear() == this.start.getYear();
		}

		//mutators
		public void setStart(int year, int rank){start.update(year,rank);}
		public void setEnd(int year, int rank){
			if (this.isNotValid())
				this.end = new Entry(year,rank);
			else
				end.update(year,rank);
		}
		//Constructor
		public Trend(int year, int rank){
			this.start = new Entry(year,rank);
			//Java does not allow you to set an object to null
			this.end = new Entry(year,rank);

		}
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
		private Entry lowest;
		private Vector<Trend> fall;
		private Entry highest;

		//accessors
		public int getRise(){
			return rise.get(0).getChange();
		}
		public int getFall(){
			return fall.get(0).getChange();
		}
		public String getName(){return name;}
		//mutators
		public void addYear(int year,int rank){
			//update rising
			if(rank <lowest.getRank()){
				lowest.update(year,rank);
			}else if(rise.get(0).isNotValid()){
				//not a valid trend yet
				if (rise.get(0).getStart().getRank() < rank){
					//current entry has a higher rank then Trend.start and is an increase in rank for said name. Update Trend.end
					rise.get(0).setEnd(year,rank); // if end is invalid then at this point only 1 entry should be valid.
				}
			}else if((rank - lowest.getRank()) > rise.get(0).getChange()){
				//a larger rise found, update
				rise.clear();
				rise.add(new Trend(lowest, new Entry(year,rank)));
			}else if((rank - lowest.getRank()) == rise.get(0).getChange()){
				//equal tend change found
				rise.add(new Trend(lowest, new Entry(year,rank)));
			}
			//update falling
			if(rank >highest.getRank()){
				highest.update(year,rank);
			}else if(fall.get(0).isNotValid()){
				//not a valid trend yet
				if (fall.get(0).getStart().getRank() > rank){
					//current entry has a higher rank then Trend.start and is an increase in rank for said name. Update Trend.end
					fall.get(0).setEnd(year,rank); // if end is invalid then at this point only 1 entry should be valid.
				}
			}else if((rank - highest.getRank()) < fall.get(0).getChange()){
				//a larger rise found, update
				rise.clear();
				rise.add(new Trend(highest, new Entry(year,rank)));
			}else if((rank - highest.getRank()) == fall.get(0).getChange()){
				//equal tend change found
				rise.add(new Trend(highest, new Entry(year,rank)));
			}
		}
		//constructor
		public Name(String name, int year, int rank){
			rise = new Vector<Trend>();
			fall = new Vector<Trend>();
			rise.add(new Trend(year,rank));
			//NOT ENTERING HERE FOR SOME REASON
			fall.add(new Trend(year,rank));
			this.name = name;
			highest = new Entry(year,rank);
			lowest = new Entry(year,rank);
		}
	}
	private int startYear;
	private int endYear;
	Vector<Name> seenNames;
	Vector<Name> setOfLargestRise;
	Vector<Name> setOfLargestFall;
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
		seenNames = new Vector<Name>();
		setOfLargestRise = new Vector<Name>();
		setOfLargestFall = new Vector<Name>();
	}
	public void prepare(){
		/**
		 * Fetch all relavant data from database and process them to get a list of largest rise and fall for each name
		 */
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

				//iterate vector to see if entry exists
				Iterator value = seenNames.iterator();
				boolean found = false;
				while (value.hasNext() && !found){
					Name next = (Name)value.next();
					if (name.equals(next.getName())){
						next.addYear(year,rank);
						found = true;
					}
				}
				//if it doesn't then add a new name to the vector
				if(!found){
					seenNames.add(new Name(name,year,rank));
				}
				rank += 1;
			}
		}
	}
	public void preprocess(){
		/**
		 * filter the data generate from prepare to keep only the names with the largest rise or largest fall
		 */
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
			if (name.getRise() > setOfLargestRise.get(0).getRise()){
				setOfLargestRise.clear();
				setOfLargestRise.add(name);
			}
			else if (name.getRise() == setOfLargestRise.get(0).getRise()){
				setOfLargestRise.add(name);
			}
			//fall
			if (name.getFall() < setOfLargestFall.get(0).getFall()){
				setOfLargestFall.clear();
				setOfLargestFall.add(name);
			}
			else if (name.getFall() == setOfLargestFall.get(0).getFall()){
				setOfLargestFall.add(name);
			}
		}
	}
	public void generate(){
		/**
		 * Generates string from the values generated from preprocess.
		 */
		String oReport = "";
		Iterator rise = setOfLargestRise.iterator();
		//bug exists in this output 1941-1944 1941-1945
		while (rise.hasNext()){
			Name nextRise = (Name)rise.next();
			oReport += nextRise.getName() + " | Start Year: " + nextRise.rise.lastElement().start.getYear()+" Start Rank:" + nextRise.rise.lastElement().start.getRank()
				+ " | End Year: " + nextRise.rise.lastElement().end.getYear()+ " End Rank" + nextRise.rise.lastElement().end.getRank()
				+ " | Trend : " + nextRise.rise.lastElement().getChange() +"\n";
		}
		
		Iterator fall = setOfLargestFall.iterator();
		while (fall.hasNext()){
			Name nextFall = (Name)fall.next();
			oReport += nextFall.getName() + " | Start Year: " + nextFall.fall.lastElement().start.getYear()+" Start Rank:" + nextFall.fall.lastElement().start.getRank()
				+ " | End Year: " + nextFall.fall.lastElement().end.getYear()+ " End Rank" + nextFall.fall.lastElement().end.getRank()
				+ " | Trend : " + nextFall.fall.lastElement().getChange() +"\n";
		}
		super.setoReport(oReport);
	}
}