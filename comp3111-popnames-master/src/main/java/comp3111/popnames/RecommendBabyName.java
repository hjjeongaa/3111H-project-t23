/**
 * RecommendBabyName
 * 
 * For task 4. Outputs two predictions for baby names given a pair of parents' names.
 * @author Hyun Joon Jeong
 */

package comp3111.popnames;

import comp3111.export.ReportHistory;

import java.util.*;
import java.time.LocalDateTime;
import java.lang.Math;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.csv.*;


public class RecommendBabyName extends ReportLog {
	private LocalDateTime time;
	private String fatherName;
	private String motherName;
	private String babyGender;
	private int fatherYOB;
	private int motherYOB;
	private int vintageYear;
	
	private List<Triple<String,Integer,Integer>> babyNamesList;
	
	
	public RecommendBabyName(String fatherName, String motherName, int fatherYOB, int motherYOB, int vintageYear, String babyGender, String country, String type) {
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.fatherYOB = fatherYOB;
		this.motherYOB = motherYOB;
		this.vintageYear = vintageYear;
		this.babyGender = babyGender;
		this.time = LocalDateTime.now();
		
		LevenshteinDistance ld = new LevenshteinDistance();
		Comparator<Triple<String,Integer,Integer>> candidateNameComparator = new Comparator<Triple<String,Integer,Integer>>() {
			public int compare(Triple<String,Integer,Integer> firstTriple, Triple<String,Integer,Integer> secondTriple) {
				if (firstTriple.getRight() * firstTriple.getMiddle() > secondTriple.getRight() * secondTriple.getMiddle()) {
					return 1;
				} else if (firstTriple.getRight() * firstTriple.getMiddle() < secondTriple.getRight() * secondTriple.getMiddle()) {
					return -1;
				} else
					return 0;
				/*
				if (firstTriple.getRight() > secondTriple.getRight()) {
					return 1;
				} else if (firstTriple.getRight() < secondTriple.getRight()) {
					return -1;
				} else {
					if (firstTriple.getMiddle() > secondTriple.getMiddle()) {
						return 1;
					} else if  (firstTriple.getMiddle() < secondTriple.getMiddle()) {
						return -1;
					} else {
						return 0;
					}
				}
				*/
			}
		};
		
		List<Triple<String,Integer,Integer>> fatherCandidateNames = new ArrayList<Triple<String,Integer,Integer>>();
		List<Triple<String,Integer,Integer>> motherCandidateNames = new ArrayList<Triple<String,Integer,Integer>>();
		int rank = 0;
		for (CSVRecord rec : AnalyzeNames.getFileParser(vintageYear, type, country)) {
			String thisName = rec.get(0);
			int scoreWithFather = ld.apply(fatherName, thisName);
			int scoreWithMother = ld.apply(motherName, thisName);
			int scoreDifference = Math.abs(scoreWithFather-scoreWithMother);
			if (rec.get(1).equals(babyGender)) { // && rank < 1000
				if (scoreWithFather == (fatherName.length()/2) || scoreWithFather == (fatherName.length()/2)+1) {
					fatherCandidateNames.add(Triple.of(thisName, scoreWithFather, scoreDifference));
				}
				if (scoreWithMother == (motherName.length()/2) || scoreWithMother == (motherName.length()/2)+1) {
					motherCandidateNames.add(Triple.of(thisName, scoreWithMother, scoreDifference));
				}
				rank++;
			}
		}
		fatherCandidateNames.sort(candidateNameComparator);
		motherCandidateNames.sort(candidateNameComparator);
		
		//calculate the weighting of which parent's name similarities should take priority (in total, there will be 100 boy and 100 girl names provided)
		double fatherWeight = 1.0;
		double motherWeight = 1.0;
		//find the parent with the closer YOB to the child's YOB. A parent's name with a YOB closer to vintageYear will likely have a name more suitable for that vintageYear.
		int fatherDistance = Math.abs(fatherYOB-vintageYear);
		int motherDistance = Math.abs(motherYOB-vintageYear);
		//reduce the weighting of the parent with the larger difference between their YOB and vintageYear.
		if (fatherDistance > motherDistance) {
			fatherWeight *= ((double)motherDistance) / ((double)fatherDistance);
		} else {
			motherWeight *= ((double)fatherDistance) / ((double)motherDistance);
		}
		final int amount = 100;
		babyNamesList = new ArrayList<Triple<String,Integer,Integer>>();
		//decrease weighting of parent according to the child's gender
		int fatherNamesAmount = 0;
		int motherNamesAmount = 0;
		if (babyGender.equals("M")) {
			//decrease mother's weighting
			fatherNamesAmount = (int) Math.round(amount * (fatherWeight/(motherWeight*0.7 + fatherWeight)));
			motherNamesAmount = amount - fatherNamesAmount;
		} else {
			motherNamesAmount = (int) Math.round(amount * (motherWeight/(motherWeight + fatherWeight*0.7)));
			fatherNamesAmount = amount - motherNamesAmount;
		}
		int fatherListIndex = 0;
		int fatherInsertedAmount = 0;
		int motherListIndex = 0;
		int motherInsertedAmount = 0;
		while (fatherListIndex < fatherCandidateNames.size() && fatherInsertedAmount < fatherNamesAmount) {
			Boolean nameExists = false;
			for (int i = 0; i < babyNamesList.size(); ++i) {
				if (babyNamesList.get(i).getLeft().equals(fatherCandidateNames.get(fatherListIndex).getLeft())) {
					nameExists = true; break;
				}
			}
			if (!nameExists) {
				babyNamesList.add(fatherCandidateNames.get(fatherListIndex));
				++fatherInsertedAmount;
			}
			++fatherListIndex;
		}
		while (motherListIndex < motherCandidateNames.size() && motherInsertedAmount < motherNamesAmount) {
			Boolean nameExists = false;
			for (int i = 0; i < babyNamesList.size(); ++i) {
				if (babyNamesList.get(i).getLeft().equals(motherCandidateNames.get(motherListIndex).getLeft())) {
					nameExists = true; break;
				}
			}
			if (!nameExists) {
				babyNamesList.add(motherCandidateNames.get(motherListIndex));
				++motherInsertedAmount;
			}
			++motherListIndex;
		}
		if (babyGender.equals("M")) {
			while (babyNamesList.size() < 100 && fatherListIndex < fatherCandidateNames.size()) 
				babyNamesList.add(fatherCandidateNames.get(fatherListIndex++));
			while (babyNamesList.size() < 100 && motherListIndex < motherCandidateNames.size()) 
				babyNamesList.add(motherCandidateNames.get(motherListIndex++));
		} else {
			while (babyNamesList.size() < 100 && motherListIndex < motherCandidateNames.size()) 
				babyNamesList.add(motherCandidateNames.get(motherListIndex++));
			while (babyNamesList.size() < 100 && fatherListIndex < fatherCandidateNames.size()) 
				babyNamesList.add(fatherCandidateNames.get(fatherListIndex++));
		}
		babyNamesList.sort(candidateNameComparator);
		ReportHistory.addReportLog(this);
	}
	public List<Triple<String,Integer,Integer>> getBabyNamesList() {
		return babyNamesList;
	}
	public LocalDateTime getTime() {
		return this.time;
	}
	public String getoReport() {
		return "Baby " +  (babyGender.equals("M")? "boy":"girl")+ " for " + fatherName+" (" + fatherYOB + "), " + motherName + " ("+motherYOB+"), Vintage year: "+vintageYear;
	}
	public String getTask() {
		return "Recommended Baby Name";
	}
	public String getHTML() {
		String html = "<h1>Recommended "+(babyGender.equals("M")?"boy":"girl")+" names for "+fatherName+", born in "+fatherYOB+", and "+motherName+", born in "+motherYOB+":</h1>";
		html += "<table><tr><th>Name</th><th>Score</th></tr>";
		String tableRow = "<tr><td>%s</td><td>%s</td><tr>";
		for (int i = 0; i < babyNamesList.size(); ++i) {
			html += String.format(tableRow, babyNamesList.get(i).getLeft(), babyNamesList.get(i).getRight());
		}
		html += "</table>";
		html = "<div>" + html + "</div>";
		return html;
	}
}
