/**
 * SoulmateName
 * 
 * Task 5. Given your name, year of birth, gender of interest, and age preference, can predict your soulmate's name using various algorithms.
 * Then from that point,this class is also the bridge into the journey through time with a selected name.
 * 
 * The way this class works is by creating a hashmap mapping a string to a list of strings. Where the key value of the string is the name of the method of obtaining a list of soulmate names.
 * 
 * @author Ryder Khoi Daniel
 */

package comp3111.popnames;

import java.time.LocalDateTime;
import java.util.*;

import org.apache.commons.csv.CSVRecord;

import comp3111.export.ReportHistory;
import comp3111.rankingAlgo.LD;
import comp3111.rankingAlgo.RankingAlgorithmFactory;

public class SoulmateName extends Reports {
	/**
	 * SoulmateName
	 * 
	 * Task 5. Given your name, year of birth, gender of interest, and age preference, can predict your soulmate's name using various algorithms.
	 * Then from that point,this class is also the bridge into the journey through time with a selected name.
	 * 
	 * The way this class works is by creating a hashmap mapping a string to a list of strings. Where the key value of the string is the name of the method of obtaining a list of soulmate names.
	 * 
	 * @author Ryder Khoi Daniel
	 */
	private LocalDateTime time;
	private String inputName;
	private String inputGender;
	private int inputYOB;
	private String matesGender;
	private int agePreference; // -1 -> younger; 0 -> same age; 1 -> older
	
	private String algorithm; // for choosing which ranking algorithm to be used in the nkt5 algorithm.
	
	private Set<String> finalNames;
	private HashMap<String, List<String>> soulmateNames; // maps an algorithm to a list of names.
	/*
	 * 		'nkt5' 	-> 	output of nkt5 algorithm.
	 * 		'ld'	->	output of smallest ld distance. (besides the input name)
	 * 		'pyc'	->	output of 'Probably' your classmate algorithm.
	 * 		'chance'->	output of 'chance encounter' algorithm.
	 * 
	 * */
	
	/**
	 * Utility boolean function to determine if source is inside table.
	 * @param String source, the string in question
	 * @param Table a list of strings. To be checked whether source is in it.
	 * @return True if source is in table, false otherwise.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	private boolean in(String source, List<String> table) {
		for(String comp : table) if(source.equals(comp)) return true;
		return false;
	}
	
	/**
	 * Constructor for SoulmateName.
	 * This constructor sets up the feature of exporting to HTML, and does all calculation necessary so that afterwards, elements may call functions of this class to quickly retrieve data.
	 * @param String name - the users name.
	 * @param String myGender - the users gender in the form of "M" or "F"
	 * @param Integer YOB - the year of birth of the user.
	 * @param String m_gender - your soulmate's gender in the form "M" or "F"
	 * @param Intgeger preference - your age preference of your mate. Utilized in calculation by doing year - preference. This means that a preference of -1 implies you prefer a younger mate and a preference of 1 implied an older mate.
	 * @param String algo - which ranking system to use for the NK-T5 algorithm. {or,dr,mcr,scr}
	 * @param String country - which country the analysis is happening in.
	 * @param String type - most guaranteed to be human.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public SoulmateName(String name, String myGender, int YOB, String m_gender, int preference, String algo, String country, String type) {
		super(name, myGender, country, type);
		super.setoReport("Potential Soulmates of " + name);
		super.setTask("Soulmates of " + name);
		
		this.inputName = name;
		this.inputGender = myGender;
		this.inputYOB = YOB;
		this.matesGender = m_gender;
		this.agePreference = preference;
		this.time = LocalDateTime.now();
		
		List<String> availableAlgos = List.of("or", "dr", "mcr", "scr");
		if(!in(algo, availableAlgos)) algo = "or";
		this.algorithm = algo;
		
		this.soulmateNames = new HashMap<String,List<String>>();
		this.finalNames = new HashSet<String>();
		
		
		// Systematically go through algorithms to populate the list soulmateNames.
		// Things that will be used across algorithms:
		// Check for boundary cases where your age is already the max year and you prefer someone younger, or you are born in the minimum year and prefer someone older.
		// In which case, just go to the edge years.
		int oYOB = this.inputYOB - preference;
		if(oYOB == GlobalSettings.getLowerBound()-1) oYOB++;
		if(oYOB == GlobalSettings.getUpperBound()+1) oYOB--;
		
		// First algorithm: NK-T5
		// get inputs rank based on their selected algorithm. If it can't find your name, it returns the rank of the person with
		// the closest name to you according to Levenshtein distance.
		int myRank = RankingAlgorithmFactory.getRankAlgorithm(algorithm, name, myGender, YOB, country, type, "ld").getRank();
		List<String> oNames = RankingAlgorithmFactory.getRankAlgorithm(algorithm, name, myGender, YOB, country, type, "ld").getNameFromRank(myRank, m_gender, oYOB, type, country);
		soulmateNames.put("nkt5", oNames);
		for(String recommended : oNames) finalNames.add(recommended);
		
		// Second algorithm: Closest name
		// Find the person with the smallest LD to you without having the same name as you.
		int minLD = Integer.MAX_VALUE;
		String selectedName = "";
		for(CSVRecord rec : AnalyzeNames.getFileParser(oYOB, type, country)){
			if(rec.get(1).equals(m_gender)) {
				if(rec.get(0).equals(name)) continue;
				int currLD = LD.calculate(inputName, rec.get(0));
				minLD = Math.min(minLD, currLD);
				if(minLD == currLD) selectedName = rec.get(0);
			}
		}
		oNames =  new ArrayList<String>();
		oNames.add(selectedName);
		soulmateNames.put("ld", oNames);
		for(String recommended : oNames) finalNames.add(recommended);
		
		// Third algorithm: pyc - Probably your classmate
		// take your birth year +- a year, and take the top 2 most popular names of the gender of interest.
		oNames =  new ArrayList<String>();
		Set<String> collectNames = new HashSet<String>();
		int count = 0;
		if(YOB != GlobalSettings.getLowerBound()) { // check for those boundary cases.
			for(CSVRecord rec : AnalyzeNames.getFileParser(YOB - 1, type, country)){
				if(rec.get(1).equals(m_gender)) {
					if(count == 2) break;
					collectNames.add(rec.get(0).replaceAll( "[^-a-zA-Z0-9]", ""));
					count++;
				}
			}
		}
		count = 0;
		for(CSVRecord rec : AnalyzeNames.getFileParser(YOB, type, country)){
			if(rec.get(1).equals(m_gender)) {
				if(count == 2) break;
				collectNames.add(rec.get(0).replaceAll( "[^-a-zA-Z0-9]", ""));
				count++;
			}
		}
		count = 0;
		if(YOB != GlobalSettings.getUpperBound()) {
			for(CSVRecord rec : AnalyzeNames.getFileParser(YOB + 1, type, country)){
				if(rec.get(1).equals(m_gender)) {
					if(count == 2) break;
					collectNames.add(rec.get(0).replaceAll( "[^-a-zA-Z0-9]", ""));
					count++;
				}
			}
		}
		for(String collected : collectNames) oNames.add(collected);
		soulmateNames.put("pyc", oNames);
		for(String recommended : oNames) finalNames.add(recommended);
		
		// 	Fourth Algorithm: chance - Chance encounter
		//	Randomly picks two names from your year of birth where the probability of any name being selected is its frequency
		//	divided by the number of members in that gender.
		oNames = new ArrayList<String>();
		List<Integer> deltas = new ArrayList<Integer>();
		List<String> potentialNames = new ArrayList<String>();
		int size = 0;
		for(CSVRecord rec : AnalyzeNames.getFileParser(YOB, type, country)) {
			if(rec.get(1).equals(m_gender)) {
				size += Integer.parseInt(rec.get(2));
				deltas.add(size);
				potentialNames.add(rec.get(0));
			}
		}
		Random rand = new Random();
		for(int i = 0; i < 2; ++i) {
			int choice = rand.nextInt(size);
			for(int c = 0; c < deltas.size(); ++c){
				if(deltas.get(c) >= choice) {
					oNames.add(potentialNames.get(c));
					break;
				}
			}
		}
		soulmateNames.put("chance", oNames);
		for(String recommended : oNames) finalNames.add(recommended);
		
		updateReportLog();
		
	}
	
	/* Report Functions */
	/**
	 * Utility class used to set the output foe exporting, formatting the HTML output table so it looks half decent after exporting.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	private void updateReportLog() {
		String thisHtml = String.format("<head> <style> table, th, td { border: 1px solid black; } table.center { margin-left: auto; margin-right: auto; } </style> </head> <h3>Potential Soulmates of %s</h3>", this.inputName); 
		thisHtml += "<table><tr><th>NK-T5</th><th>Similar Name</th><th>Probably Your Classmate</th><th>Chance Encounter</th></tr>";
		String tableRow = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><tr>";
		int depth = 0;
		while(true) {
			String nkName = (getSoulmateNamesSize("nkt5") <= depth )?"":getSoulmateNames("nkt5").get(depth);
			String ldName = (getSoulmateNamesSize("ld") <= depth )?"":getSoulmateNames("ld").get(depth);
			String pycName = (getSoulmateNamesSize("pyc") <= depth )?"":getSoulmateNames("pyc").get(depth);
			String chanceName = (getSoulmateNamesSize("chance") <= depth )?"":getSoulmateNames("chance").get(depth);
			if(nkName.length() + ldName.length() + pycName.length() + chanceName.length() == 0) break;
			thisHtml += String.format(tableRow, nkName, ldName, pycName, chanceName);
			depth++;
		}
		thisHtml += "</table>";
		thisHtml = "<div>" + thisHtml + "</div>";
		super.setHTML(thisHtml);
		ReportHistory.addReportLog(this);
	}
	
	// Getters
	/**
	 * Given an algorithm, this function returns a list of names that were outputted from that algorithm. This function requires the calling of the constructor beforehand.
	 * 
	 * @param String algo - The soulmate name algorithm {nkt5, ld, pyc, chance}
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public List<String> getSoulmateNames(String algo){
		return soulmateNames.get(algo);
	}
	
	/**
	 * Given an algorithm returns the number of names in the set.
	 * @param String algo - the soulmate name algorithm {nkt5, ld, pyc, chance}
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public int getSoulmateNamesSize(String algo) {
		return soulmateNames.get(algo).size();
	}
	
	/**
	 * This function returns a set of all the names collected from every algorithm. The reason a set was used is so that there are no duplicate names in the final set of names.
	 * @author Ryder Khoi Daniel
	 * v1.0
	 */
	public List<String> getFinalNames(){
		List<String> output = new ArrayList<String>();
		for(String name : finalNames) output.add(name);
		return output;
	}

}
