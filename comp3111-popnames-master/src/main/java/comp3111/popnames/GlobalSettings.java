/**
* GlobalSettings.java
* 
* Singleton design Class to keep track of all settings that can be shared between pages.
* In the context of this program, only the country, start year, and end year need keeping track of.
* This class uses a singleton design pattern to ensure that it is the same object/data all pages are referencing.
*
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames;

import javafx.util.Pair;
/**
 * Used for getting the country that all pages should be pulling their data set from, and that countries respective upper and lower bounds for retrieving years.
 * This class can also be used to set the country, in which case, the upper and lower bounds will be automatically updated. 
 * @author Ryder Khoi Daniel
 * v1.0
 */
public class GlobalSettings {
	/**
	* GlobalSettings.java
	* 
	* Singleton design Class to keep track of all settings that can be shared between pages.
	* In the context of this program, only the country, start year, and end year need keeping track of.
	* This class uses a singleton design pattern to ensure that it is the same object/data all pages are referencing.
	*
	* @author Ryder Khoi Daniel
	* @version 1.0
	*/
	/* Global Settings */
	private static String country;
	private static int lowerBound;
	private static int upperBound;
	
	private static final GlobalSettings SINGLE_INSTANCE = new GlobalSettings();
	/**
	 * The constructor of this class is private as this class uses a singleton design pattern.
	 * Moreover, the default data set it the US. This constructor is called when the object is referenced for the first time from anywhere in the program.
	 * 
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	private GlobalSettings() {
		// Setting the defaults
		GlobalSettings.country = "usa";
		GlobalSettings.lowerBound = 1880;
		GlobalSettings.upperBound = 2019;
	}
	
	// Setter
	/**
	 * This function is used to set the country to your liking. Intended to only be called from the settings page.
	 * After this function is called, the upper and lower bound are automatically updated. This function operates under the assumption that the country parameter represents a valid data set.
	 * @param countryName {usa, scotland, norway, NorthernIreland, France, EnglandAndWales}
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static void setCountry(String countryName) {
		Pair<String, String> countryRange = DatasetHandler.getValidRange("human", countryName);
		GlobalSettings.country = countryName;
		if(countryRange == null) return;
		GlobalSettings.lowerBound = Integer.parseInt(countryRange.getKey());
		GlobalSettings.upperBound = Integer.parseInt(countryRange.getValue());
	}
	
	// Getters
	/**
	 * returns the object itself. Not entirely useful in the context of this program.
	 * @return The GlobalSettings object
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static GlobalSettings getInstance() {
		return SINGLE_INSTANCE;
	}
	
	/**
	 * @return String of the country variable.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static String getCountry() {
		return country;
	}
	
	/**
	 * @return Integer The smallest year in which a csv file for country can be opened.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static int getLowerBound() {
		return lowerBound;
	}
	
	/**
	 * @return Integer The largest year in which a csv file for country can be opened.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public static int getUpperBound() {
		return upperBound;
	}
}
