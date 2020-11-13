package comp3111.popnames;

import org.apache.commons.csv.*;
import edu.duke.*;

public class AnalyzeNames {
	
    public static CSVParser getFileParser(int year) {
        FileResource fr = new FileResource(String.format("dataset/human/usa/yob%s.csv", year));
        return fr.getCSVParser(false);
    }

    public static CSVParser getFileParser(int year, String type, String country) {
        FileResource fr = new FileResource(String.format("dataset/%s/%s/yob%s.csv", type, country, year));
        return fr.getCSVParser(false);
    }

    public static String getSummary(int year) {
        String oReport = "";
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        int uniqueBoys = 0;
        int uniqueGirls = 0;

        oReport = String.format("Summary (Year of %d):\n", year);
        for (CSVRecord rec: getFileParser(year)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            totalNames += 1;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                uniqueBoys++;
            } else {
                totalGirls += numBorn;
                uniqueGirls++;
            }
        }

        oReport += String.format("Total Births = %,d\n", totalBirths);
        oReport += String.format("***Baby Girls = %,d\n", totalGirls);
        oReport += String.format("***Baby Boys = %,d\n", totalBoys);

        oReport += String.format("Total Number of Unique Names = %,d\n", totalNames);
        oReport += String.format("***Unique Names (baby girls) = %,d\n", uniqueGirls);
        oReport += String.format("***Unique Names (baby boys) = %,d\n", uniqueBoys);

        return oReport;
    }

    /*
     *		Returns rank based on name. 
     */
    public static int getRank(int year, String name, String gender) {
        boolean found = false;
        int oRank = 0;
        int rank = 1;
        for (CSVRecord rec: getFileParser(year)) {
            // Increment rank if gender matches param
            if (rec.get(1).equals(gender)) {
                // Return rank if name matches param
                if (rec.get(0).equals(name)) {
                    found = true;
                    oRank = rank;
                    break;
                }
                rank++;
            }
        }
        if (found)
            return oRank;
        else
            return -1;
    }

    /*
     * 		Returns the name based on rank and gender.
     */
    public static String getName(int year, int rank, String gender) {
        boolean found = false;
        String oName = "";
        int currentRank = 0;

        // For every name entry in the CSV file
        for (CSVRecord rec: getFileParser(year)) {
            // Get its rank if gender matches param
            if (rec.get(1).equals(gender)) {
                // Get the name whose rank matches param
                currentRank++;
                if (currentRank == rank) {
                    found = true;
                    oName = rec.get(0);
                    break;
                }
            }
        }
        if (found)
            return oName;
        else
            return "information on the name at the specified rank is not available";
    }
    
    /*	
     * 		Author: Ryder Khoi Daniel
     * 
     * 		Description:
     *			This function returns the frequency of a name in a given year, based on rank or name.
     *		Year and Gender will always be the first two parameters, followed by an int if you 
     *		want the frequency based on rank, or followed by a String if you want the frequency
     *		based on name.
     *		
     *		Parameters:		( Mandatory ones )
     *			year		-		An integer representing the year of interest.
     *			gender		-		'M' or 'F'
     *
     *		Return value:
     *			Returns the frequency of the item in a given year. If the item does not exit in
     *		that year (name doesn't exist, or rank is larger than the entire set), then the function
     *		will return -1.
     */
    public static int getFrequency(int year, String gender, int rank) {
    	// Return frequency based on rank.
    	int currentRank = 0;
    	int frequency = -1;
    	for(CSVRecord rec: getFileParser(year)) {
    		if (rec.get(1).equals(gender)) {
                currentRank++;
                if (currentRank == rank) 
                	frequency = Integer.parseInt(rec.get(2));
            }
    	}
    	return frequency;
    }
    
    public static int getFrequency(int year, String gender, String name) {
    	// Return frequency based on rank.
    	int frequency = -1;
    	for(CSVRecord rec: getFileParser(year)) {
    		if (rec.get(1).equals(gender) && rec.get(0).equals(name)) 
                	frequency = Integer.parseInt(rec.get(2));
    	}
    	return frequency;
    }
    

}