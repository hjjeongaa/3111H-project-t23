/**
 * SoulmateName
 * 
 * Task 5. Given your name, year of birth, gender of interest, and age preference, can predict your soulmates name using various algorithms.
 * @author Ryder Khoi Daniel
 */

package comp3111.popnames;

import java.time.LocalDateTime;
import java.util.*;

import org.apache.commons.csv.CSVRecord;

import comp3111.rankingAlgo.LD;
import comp3111.rankingAlgo.RankingAlgorithmFactory;

public class SoulmateName extends ReportLog {
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
	
	public SoulmateName(String name, String myGender, int YOB, String m_gender, int preference, String algo,String country, String type) {
		this.inputName = name;
		this.inputGender = myGender;
		this.inputYOB = YOB;
		this.matesGender = m_gender;
		this.agePreference = preference;
		this.time = LocalDateTime.now();
		
		this.algorithm = algo;
		
		this.soulmateNames = new HashMap<String,List<String>>();
		this.finalNames = new HashSet<String>();
		
		
		// Systematically go through algorithms to populate the list soulmateNames.
		// Things that will be used across algorithms:
		int oYOB = this.inputYOB - preference;
		
		// First algorithm: NK-T5
		// get inputs rank based on their selected algorithm. If it can't find your name, it returns the rank of the person with
		// the closest name to you according to Levenshtein distance.
		int myRank = RankingAlgorithmFactory.getRankAlgorithm(algorithm, name, myGender, YOB, country, type, "ld").getRank();
		List<String> oNames = RankingAlgorithmFactory.getRankAlgorithm(algorithm, name, myGender, YOB, country, type, "ld").getNameFromRank(myRank, m_gender, oYOB, type, country);
		soulmateNames.put("nkt5", oNames);
		for(String recommended : oNames) finalNames.add(recommended);
		
		// Second algorithm: Closest name
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
		for(CSVRecord rec : AnalyzeNames.getFileParser(YOB - 1, type, country)){
			if(rec.get(1).equals(m_gender)) {
				if(count == 2) break;
				collectNames.add(rec.get(0));
				count++;
			}
		}
		count = 0;
		for(CSVRecord rec : AnalyzeNames.getFileParser(YOB, type, country)){
			if(rec.get(1).equals(m_gender)) {
				if(count == 2) break;
				collectNames.add(rec.get(0));
				count++;
			}
		}
		count = 0;
		for(CSVRecord rec : AnalyzeNames.getFileParser(YOB + 1, type, country)){
			if(rec.get(1).equals(m_gender)) {
				if(count == 2) break;
				collectNames.add(rec.get(0));
				count++;
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
		
	}
	
	// Getters
	
	public List<String> getSoulmateNames(String algo){
		return soulmateNames.get(algo);
	}
	
	public int getSoulmateNamesSize(String algo) {
		return soulmateNames.get(algo).size();
	}
	
	public List<String> getFinalNames(){
		List<String> output = new ArrayList<String>();
		for(String name : finalNames) output.add(name);
		return output;
	}
	
	// WOW Factor
	public void journeyThroughTime(String name, String mates_name) {
		// TODO:
	}
	
	
	/* Stuff for Reporting */
	public String getoReport() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTask() {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHTML() {
		// TODO Auto-generated method stub
		return null;
	}

}
