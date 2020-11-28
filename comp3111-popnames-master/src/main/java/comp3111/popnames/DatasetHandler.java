package comp3111.popnames;

import java.util.Vector;

import edu.duke.FileResource;
import javafx.util.Pair;

/**
 * Used for handling valid data set access handlers and is helpful for controllers.
 * @author Yuxi Sun
 * v1.0
 */
public abstract class DatasetHandler {
	/**
	 * This function is used to get the valid types in the current data set
	 * @return a Vector of Strings of the support types in the current data set using the data set/metadata.txt
	 */
	public static Vector<String> getTypes(){
		Vector<String> types = new Vector<String>();
    	FileResource fr = new FileResource("dataset/metadata.txt");
    	for(String line: fr.lines()) {
    		types.add(line);
    	}
    	return types;
	}
	/**
	 * This function is used to get the valid countries given a type using the dataset/(type)/metadata.txt
	 * @param type [human, pet]
	 * @return Vector of Strings of the supported countries in the existing data set
	 */
	public static Vector<String> getCountries(String type){
		type = type.strip(); //cleaning input
		Vector<String> types = new Vector<String>();
    	FileResource fr = new FileResource(String.format("dataset/%s/metadata.txt", type));
    	for(String line: fr.lines()) {
    		types.add(line);
    	}
    	return types;
	}

	/**
	 * Used to get the valid start and end year ranges inclusive of a type/country
	 * @param type the type of data set being used
	 * @param country the country of data set being 
	 * @return A Pair of type String,String with key start year and value end year or null if the type and country are invalid.
	 */
	public static Pair<String,String> getValidRange(String type, String country){
		type = type.strip(); //cleaning input
		country = country.strip(); //cleaning input
		//validation
		if (getTypes().contains(type) && getCountries(type).contains(country)){
	    	FileResource fr = new FileResource(String.format("dataset/%s/%s/metadata.txt", type,country));
	    	Vector<String> metadata = new Vector<String>(); // used to store the first line of the metadata file (that holds the start and end year separated by a space
	    	for(String line: fr.lines()) {
	    		metadata.add(line);
	    	}
			return new Pair<String,String>(metadata.get(0),metadata.get(1));
		}
		return null;//input error
	}
}
