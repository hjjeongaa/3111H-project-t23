/**
* TopNNames.java
* 
* Basis of Task 1.
* 
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames;


import java.util.*;
import org.apache.commons.csv.CSVRecord;

public class TopNNames extends Reports {
	
	/* Private Variables */
	private int numOfNames;
	private HashMap<String, Integer> collectionOfYears;
	private List<String> topNName;
	
	
	/* Constructor */
	public TopNNames(int startYear, int endYear, int N, String gender, String country, String type){
		//	Call Report constructor.
		super(null, gender, country, type);
		this.numOfNames = N;
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
		topNName = new ArrayList<String>(collectionOfYears.keySet());
		// Sort those names.
		topNName.sort(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
			     return Integer.compare(collectionOfYears.get(s2), collectionOfYears.get(s1));
			 }
		});
		// Make topNName a subset of that sorted list.
		topNName = topNName.subList(0,numOfNames);
	}
	
	/* Interface Function */
	public String getNameFromIndex(int index) {
		return topNName.get(index);
	}
	
	public int getFrequenctFromIndex(int index) {
		return collectionOfYears.get(topNName.get(index));
	}
}
