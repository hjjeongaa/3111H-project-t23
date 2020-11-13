/**
* TrendInPopularity2.java
* The potential basis of Task 3.
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames;

import java.util.*;

import org.apache.commons.csv.CSVRecord;

public class TrendInPopularity2 extends Reports{
	/* Utility Classes */
	// Used to keep track of the rank of every person encountered in the range of years.
	private class Person {
		/* Private Variables */
		int entryYear;
		
		/* Both of these are positive integers */
		int bestGain;
		int worstGain;
		
		int bestGainStart;
		int bestGainEnd;
		int worstGainStart;
		int worstGainEnd;
		private List<Integer> rankStartingFromEntry;
		
		public Person(int entry, int rank) {
			entryYear = entry;
			rankStartingFromEntry = new ArrayList<Integer>();
			rankStartingFromEntry.add(rank);
			
			bestGainStart = -1;
			bestGainEnd = -1;
			worstGainStart = -1;
			worstGainEnd = -1;
		}
		
		public void addRanking(int rank) {
			rankStartingFromEntry.add(rank);
		}
		
		/* For these functions to return the an accurate answer, rankStartingFromEntry should be complete */
		public int getLargestDecrease() {
			// make sure that this persons rank increase is actually solvable.
			if(rankStartingFromEntry.size() < 2) return -1;
			int mui = 0; // Maximum gain under inspection.
			int msf = 0; // Maximum gain encountered so far.
			int sy = 0;	// Temporary start year.
			int ey = 0; // Temporary end year.
			int probe = 0;
			
			for(int i = 1; i < rankStartingFromEntry.size(); ++i) {
				mui = Math.max(0, mui + rankStartingFromEntry.get(i) - rankStartingFromEntry.get(i-1));
				msf = Math.max(msf, mui);
				if( mui == 0 )
					probe = i;
				if( msf == mui ) {
					sy = probe;
					ey = i;
				}
			}
			
			if (msf != 0) {
				bestGainStart = sy + entryYear;
				bestGainEnd = ey + entryYear;
				bestGain = msf;
				return msf;
			}
			// bestGain* stay as -1 if there is purely a decrease in rank.
			return -1;
		}
		
		public int getLargestIncrease() {
			// make sure that this persons rank increase is actually solvable.
			if(rankStartingFromEntry.size() < 2) return -1;
			int mui = 0; // Maximum loss under inspection.
			int msf = 0; // Maximum loss encountered so far.
			int sy = 0;	// Temporary start year.
			int ey = 0; // Temporary end year.
			int probe = 0;
			
			for(int i = 1; i < rankStartingFromEntry.size(); ++i) {
				mui = Math.min(0, mui + rankStartingFromEntry.get(i) - rankStartingFromEntry.get(i-1));
				msf = Math.min(msf, mui);
				if( mui == 0 )
					probe = i;
				if( msf == mui ) {
					sy = probe;
					ey = i;
				}
			}
			
			if (msf != 0) {
				worstGainStart = sy + entryYear;
				worstGainEnd = ey + entryYear;
				worstGain = -1*msf;
				return msf;
			}
			// worstGain* stay as -1 if there is purely an increase in rank.
			return -1;
		}
		
		/* Access functions */
		public int getRankInYear(int year) {
			return rankStartingFromEntry.get(year-entryYear);
		}
		
		public int getBestGainStart() { return bestGainStart;}
		public int getBestGainEnd() { return bestGainEnd;}
		
		public int getWorstGainStart() { return worstGainStart;}
		public int getWorstGainEnd() { return worstGainEnd;}
		
		public int getBestGain() { return bestGain;}
		public int getWorstGain() { return worstGain;}
		
	}
	
	/* Private Variables */
	private HashMap<String, Person> peopleEncountered;
	private List<String> topRisesNames;
	private List<String> topLossesNames;
	
	public TrendInPopularity2(int startYear, int endYear, String gender, String country, String type) {
		//		Call Report constructor.
		super(null, gender, country, type);
		peopleEncountered = new HashMap<String,Person>();
		
		// Populate HashMap
		for(int year = startYear; year <= endYear; ++year) {
			int rnk = 0;
			for(CSVRecord rec : AnalyzeNames.getFileParser(year)) {
				// We are only interested in Names who belong to the gender of interest.
				if(rec.get(1).equals(gender)) {
					String name = rec.get(0);
					rnk++; // increase the rank
					if(peopleEncountered.containsKey(name)) {
						// The name exists in the map, then we just need to update the Person
						peopleEncountered.get(name).addRanking(rnk);
					} else {
						// The name does not exist in the map, thus, we need to put it in.
						Person newGuy = new Person(year, rnk);
						peopleEncountered.put(name, newGuy);
					}
				}
			}
		}
		
		// At this point, the whole HashMap is populated. now to sort everybody by their ranking increase.
		// Firstly calculate everyones gains and losses
		topRisesNames = new ArrayList<String>(peopleEncountered.keySet());
		topLossesNames = new ArrayList<String>(peopleEncountered.keySet());
		
		for(String nm : topRisesNames) {
			peopleEncountered.get(nm).getLargestIncrease();
			peopleEncountered.get(nm).getLargestDecrease();
		}
		
		// now that all access functions can work quickly now, we can now sort.
		topRisesNames.sort(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
			     return Integer.compare(peopleEncountered.get(s2).getBestGain(), 
			    		 peopleEncountered.get(s1).getBestGain());
			 }
		});
		
		topLossesNames.sort(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
			     return Integer.compare(peopleEncountered.get(s2).getWorstGain(), 
			    		 peopleEncountered.get(s1).getWorstGain());
			 }
		});
	}
	
	/* Access Functions */
	
	public int getRankInYear(String name, int year) {
		return peopleEncountered.get(name).getRankInYear(year);
	}
	
	public int getBestGainStartYear(String name) {
		return peopleEncountered.get(name).getBestGainStart();
	}
	public int getBestGainEndYear(String name) {
		return peopleEncountered.get(name).getBestGainEnd();
	}
	
	public int getWorstGainStartYear(String name) {
		return peopleEncountered.get(name).getWorstGainStart();
	}
	public int getWorstGainEndYear(String name) {
		return peopleEncountered.get(name).getWorstGainEnd();
	}
	
	public int getBestGain(String name) {
		return peopleEncountered.get(name).getBestGain();
	}
	public int getWorstGain(String name) {
		return peopleEncountered.get(name).getWorstGain();
	}
	
	/* For these functions, the input is a rank, meaning that rank cannot be less than 1. */
	public String getNthHighestIncrease(int rank) {
		return topRisesNames.get(rank-1);
	}
	public String getNthHighestDecrease(int rank) {
		return topLossesNames.get(rank-1);
	}

}
