/**
* GlobalSettings.java
* 
* Singleton design Class to keep track of all settings that can be shared between pages. For example 
*
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames;

import javafx.util.Pair;

public class GlobalSettings {
	/* Global Settings */
	private static String country;
	private static int lowerBound;
	private static int upperBound;
	
	private static final GlobalSettings SINGLE_INSTANCE = new GlobalSettings();
	
	private GlobalSettings() {
		// Setting the defaults
		GlobalSettings.country = "usa";
		GlobalSettings.lowerBound = 1880;
		GlobalSettings.upperBound = 2019;
	}
	
	// Setter
	
	public static void setCountry(String countryName) {
		Pair<String, String> countryRange = DatasetHandler.getValidRange("human", countryName);
		GlobalSettings.country = countryName;
		if(countryRange == null) return;
		GlobalSettings.lowerBound = Integer.parseInt(countryRange.getKey());
		GlobalSettings.upperBound = Integer.parseInt(countryRange.getValue());
	}
	
	// Getters
	
	public static GlobalSettings getInstance() {
		return SINGLE_INSTANCE;
	}
	
	public static String getCountry() {
		return country;
	}
	
	public static int getLowerBound() {
		return lowerBound;
	}
	
	public static int getUpperBound() {
		return upperBound;
	}
}
