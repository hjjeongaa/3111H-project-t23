/**
* TopNNames.java
* 
* Basis of Task 1.
* This class is used to do any form of reporting on a collection of years. This is done by collecting all the CSVs and mapping each name to a value. If a name is seen more than once, then the value in the map is updated. The keys of the map are stored in a separate List object and then sorted.
* 
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames;


import java.util.*;
import org.apache.commons.csv.CSVRecord;

public class TopNNames extends Reports {
	
	/* Private Variables */
	private HashMap<String, Integer> collectionOfYears;
	private List<String> sortedNames;
	private int collectionSize;
	
	
	/* Constructor */
	public TopNNames(int startYear, int endYear, String gender, String country, String type){
		//	Call Report constructor.
		super(null, gender, country, type);
		collectionOfYears = new HashMap<String,Integer>();
		
		/*
		 * 		Generate the collection of years.
		 */
		
		// For every year in the range
		for(int year = startYear; year <= endYear; ++year) {
			for(CSVRecord rec : AnalyzeNames.getFileParser(year)) {
				// We are only interested in Names who belong to the gender of interest.
				if(rec.get(1).equals(gender)) {
					String name = rec.get(0);
					int frequency = Integer.parseInt(rec.get(2));
					if(collectionOfYears.containsKey(name)) {
						// The name exists in the map, then we just need to update the value.
						int updatedValue = collectionOfYears.get(name) + frequency;
						collectionOfYears.put(name, updatedValue);
					} else {
						// The name does not exist in the map, thus, we need to put it in.
						collectionOfYears.put(name, frequency);
					}
				}
			}
		}
		
		/*
		 * 		Generate the list of the top N names.
		 * 		
		 * 	Inspiration and code taken from:
		 * 	https://stackoverflow.com/questions/18971849/best-way-to-get-top-n-keyssorted-by-values-in-a-hashmap
		 */
		
		// Convert the collection of names into a list.
		sortedNames = new ArrayList<String>(collectionOfYears.keySet());
		// Sort those names.
		sortedNames.sort(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
			     return Integer.compare(collectionOfYears.get(s2), collectionOfYears.get(s1));
			 }
		});
		
		collectionSize = sortedNames.size();
	}
	
	/* Interface Functions */
	
	/**
	 * Given the list of all names and frequencies in a year span, this function takes an index of that list and returns
	 * the name at that index.
	 * @param index	The index of the name which will be retrieved.
	 * @return	The name at that index.
	 */
	public String getNameFromIndex(int index) {
		if(index < collectionSize){
			return sortedNames.get(index);
		} else {
			return "-1";
		}
				
	}
	
	/**
	 * Given the list of all names and frequencies in a year span, this function takes an index of that list and returns the frequency at that index.
	 * @param index	The index of the frequency which will be retrieved.
	 * @return	The frequency at that index.
	 */
	public int getFrequencyFromIndex(int index) {
		if(index < collectionSize){
			return collectionOfYears.get(sortedNames.get(index));
		} else {
			return -1;
		}
		
	}
	
	
	/**
	 * Given the list of all names and frequencies in a year span, this function takes a name and returns the index of that name. If it does not exist, it returns -1.
	 * @param name	The index of this name will be retrieved.
	 * @return	The index of the given name. If the name does not exist in the list, -1 will be returned.
	 */
	public int getNameIndex(String name) {
		return sortedNames.indexOf(name);
	}
}
