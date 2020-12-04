/**
 * RecommendBabyName
 * 
 * For task 4. Outputs a list of recommended baby names, given a pair of parents' names, their YOBs, the baby's gender, and an optional vintageYear (the year the baby should be born in, default 2019)
 * @author Hyun Joon Jeong
 */

package comp3111.popnames;

import comp3111.export.ReportHistory;

import java.util.*;
import java.time.LocalDateTime;
import java.awt.Color;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import org.apache.commons.text.similarity.LevenshteinDistance;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.palette.ColorPalette;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.*;


public class RecommendBabyName extends ReportLog {
	private LocalDateTime time;
	private String fatherName;
	private String motherName;
	private String babyGender;
	private int fatherYOB;
	private int motherYOB;
	private int vintageYear;
	private byte[] wordCloudImageBytes;
	
	//babyNamesList: A list of 3-tuples, each tuple contains a suggested baby name, its LD-distance from the father/mother's name, and the difference in LD-distance between the father and mother's name
	private List<Triple<String,Integer,Integer>> babyNamesList;
	
	/**
	 * Constructor for RecommendBabyName
	 * Computes a list of suggested baby names (up to 100) given the following parameters.
	 * The algorithm primarily makes use of the Levenshtein distance - an algorithm that measures the edit distance between two strings (how many edits e.g. replacements, letter shifts, are needed to transform string 1 to string 2)
	 * Created for Task 4's wow factor. Should I call it the NK-T7?
	 * It creates two sets of lists - baby names created from the father's name, and baby names created from the mother's name.
	 * They are sorted according to whether the differences between the mother and father LD-distances are the same.
	 * After creating two lists, they are combined to create one master list of baby names.
	 * How many names are taken from the mother's list and how many from the father's list depends on the gender of the baby whose name is being generated, as well as whether the parent is closer to vintageYear or not. (A parent with a name closer to the baby's YOB should give a more 'appropriate' name for the baby, right?? Maybe...)
	 * If one list fails to meet the quota, names from the other parent's list are drawn until the master list reaches 100 names, or the other parent's list becomes empty too.
	 * @param fatherName father's name
	 * @param motherName mother's name
	 * @param fatherYOB father's YOB
	 * @param motherYOB mother's YOB
	 * @param vintageYear This is supposed to be the baby's YOB. Usually it is set to 2019, but the user can specify it freely to generate a name for a baby being born at any past year.
	 * @param babyGender The gender of the name the user wants to generate.
	 * @param country Data set's country.
	 * @param type Data set's type/species.
	 */
	public RecommendBabyName(String fatherName, String motherName, int fatherYOB, int motherYOB, int vintageYear, String babyGender, String country, String type) {
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.fatherYOB = fatherYOB;
		this.motherYOB = motherYOB;
		this.vintageYear = vintageYear;
		this.babyGender = babyGender;
		//We need to set the time now, the moment the results start generating.
		this.time = LocalDateTime.now();
		
		LevenshteinDistance ld = new LevenshteinDistance();
		//From here on, LD-distance refers to the Levenshtein distance between the baby name and the father/mother's name.
		//LD-diff refers to the DIFFERENCE between the LD-distances between the baby name and the father's name, and the baby name and the mother's name.
		//Comparator used to sort the baby names list. Ranking by LD-distance first gives us names that are too similar to the father or mother's name (e.g. father's name = Michael -> baby name = Michaela, Michel, Micheal. etc)
		//Instead we sort by the LD-diff. An LD-diff of 0 means both the father and mother's name have equal LD-distances. Sort of means that half of the baby name is taken from the father, and half of the baby name taken from the mother.
		//After that we sort by the LD-dist, then the name's rank in the vintageYear.
		Comparator<Triple<String,Integer,Integer>> candidateNameComparator = new Comparator<Triple<String,Integer,Integer>>() {
			public int compare(Triple<String,Integer,Integer> firstTriple, Triple<String,Integer,Integer> secondTriple) {
				if (firstTriple.getRight()*5 + firstTriple.getMiddle() > secondTriple.getRight()*5 + secondTriple.getMiddle()) {
					return 1;
				} else if (firstTriple.getRight()*5 + firstTriple.getMiddle() < secondTriple.getRight()*5 + secondTriple.getMiddle()) {
					return -1;
				} else
					return 0;
			}
		};
		
		List<Triple<String,Integer,Integer>> fatherCandidateNames = new ArrayList<Triple<String,Integer,Integer>>();
		List<Triple<String,Integer,Integer>> motherCandidateNames = new ArrayList<Triple<String,Integer,Integer>>();
		int minimumNameFrequency = 0;
		for (CSVRecord rec : AnalyzeNames.getFileParser(vintageYear, type, country)) {
			String thisName = rec.get(0);
			//LD-dists for father & mother
			int scoreWithFather = ld.apply(fatherName, thisName);
			int scoreWithMother = ld.apply(motherName, thisName);
			//LD-diff
			int scoreDifference = Math.abs(scoreWithFather-scoreWithMother);
			//Optional: Set the minimum frequency for a name to be considered. This avoids very rare and unusual names that occur only because the LD-dist algorithm favors it.
			if (rec.get(1).equals(babyGender) && Integer.parseInt(rec.get(2)) > minimumNameFrequency) {
				//Get LD-dists which are equal to approx. half the parent's name's length. (To encourage the 'baby name comes from both halves of the parent' phenomenon)
				if (scoreWithFather == (fatherName.length()/2) || scoreWithFather == (fatherName.length()/2)+1) {
					fatherCandidateNames.add(Triple.of(thisName, scoreWithFather, scoreDifference));
				}
				if (scoreWithMother == (motherName.length()/2) || scoreWithMother == (motherName.length()/2)+1) {
					motherCandidateNames.add(Triple.of(thisName, scoreWithMother, scoreDifference));
				}
			}
		}
		fatherCandidateNames.sort(candidateNameComparator);
		motherCandidateNames.sort(candidateNameComparator);
		
		//Calculate the weighting of which parent's name similarities should take priority (in total, there will be 100 names (or otherwise depending on the amount variable)
		double fatherWeight = 1.0;
		double motherWeight = 1.0;
		//Find the parent with the closer YOB to vintageYear.
		int fatherDistance = Math.abs(fatherYOB-vintageYear);
		int motherDistance = Math.abs(motherYOB-vintageYear);
		//reduce the weighting of the parent with the larger difference between their YOB and vintageYear, based on how much larger their YOB is.
		if (fatherDistance > motherDistance) {
			fatherWeight *= ((double)motherDistance) / ((double)fatherDistance);
		} else {
			motherWeight *= ((double)fatherDistance) / ((double)motherDistance);
		}
		final int amount = 100;
		babyNamesList = new ArrayList<Triple<String,Integer,Integer>>();
		//Using these weights, calculate what portion of the total # of names should come from which parent.
		int fatherNamesAmount = 0;
		int motherNamesAmount = 0;
		if (babyGender.equals("M")) {
			//male name being generated
			//decrease mother's weighting
			fatherNamesAmount = (int) Math.round(amount * (fatherWeight/(motherWeight*0.7 + fatherWeight)));
			motherNamesAmount = amount - fatherNamesAmount;
		} else {
			//female name being generated
			//do the opposite.
			motherNamesAmount = (int) Math.round(amount * (motherWeight/(motherWeight + fatherWeight*0.7)));
			fatherNamesAmount = amount - motherNamesAmount;
		}
		//we need all these for the case where either parent's list doesn't have enough names, so that we can take from the other parent's list instead to fill the 100 names quota.
		int fatherListIndex = 0;
		int fatherInsertedAmount = 0;
		int motherListIndex = 0;
		int motherInsertedAmount = 0;
		//try and insert the right proportion of names from the father
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
		//try and insert the right proportion of names from the mother
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
		//if there are <amount names in the master list, we either exhaust the father names list first or the mother names list first depending on the gender of the baby
		if (babyGender.equals("M")) {
			while (babyNamesList.size() < amount && fatherListIndex < fatherCandidateNames.size()) 
				babyNamesList.add(fatherCandidateNames.get(fatherListIndex++));
			while (babyNamesList.size() < amount && motherListIndex < motherCandidateNames.size()) 
				babyNamesList.add(motherCandidateNames.get(motherListIndex++));
		} else {
			while (babyNamesList.size() < amount && motherListIndex < motherCandidateNames.size()) 
				babyNamesList.add(motherCandidateNames.get(motherListIndex++));
			while (babyNamesList.size() < amount && fatherListIndex < fatherCandidateNames.size()) 
				babyNamesList.add(fatherCandidateNames.get(fatherListIndex++));
		}
		babyNamesList.sort(candidateNameComparator);
		int i = 0;
		if (babyNamesList.size() > 20)
			generateWordCloudImageBytes();
		else
			wordCloudImageBytes = null;
		ReportHistory.addReportLog(this);
	}
	/**
	 * Returns the master baby names list generated by the algorithm.
	 * @return the list of 3-tuples that stores the baby names, their LD-dists from the father/mother, and their LD-diff
	 */
	public List<Triple<String,Integer,Integer>> getBabyNamesList() {
		return babyNamesList;
	}
	/**
	 * Helper function for calculating how similar two colors are.
	 * @param c1 first color
	 * @param c2 second color
	 * @return verdict on their similarity
	 */
	private boolean similarColor(Color c1, Color c2){
	    int distance = (c1.getRed() - c2.getRed())*(c1.getRed() - c2.getRed()) + (c1.getGreen() - c2.getGreen())*(c1.getGreen() - c2.getGreen()) + (c1.getBlue() - c2.getBlue())*(c1.getBlue() - c2.getBlue());
	    if(distance > 20){
	        return true;
	    }else{
	        return false;
	    }
	}
	/**
	 * Returns the PNG byte array of a word cloud image generated by the algorithm.
	 * @return the byte array of a PNG image containing the word cloud.
	 */
	private void generateWordCloudImageBytes() {
		WordCloud wc = new WordCloud(new Dimension(500,220), CollisionMode.PIXEL_PERFECT);
		//background of the word cloud will be white
		wc.setBackgroundColor(Color.WHITE);
		//color palette for the word cloud
		Color[] thisPalette = new Color[30];
		//generate a base color
		Random rand = new Random();
		float r = 0.0f;
		float g = 0.0f;
		float b = 0.0f;
		while (!(r+g+b>1.5f && r+g+b < 2.9f)) {
			r = rand.nextFloat();
			g = rand.nextFloat();
		    b = rand.nextFloat();
		    Color color = new Color(r,g,b,1);
		    //ensure its not too similar to the background color
		    if (!similarColor(color,Color.WHITE))
		    	break;
		}
		//generate a color swatch based off the base color we generated above
		for (int i = 0; i < 30; ++i) {
			//generate a range of colors from dark to bright
			float newR = r/2+(r/2/(i+1));
			float newG = g/2+(g/2/(i+1));
			float newB = b/2+(b/2/(i+1));
			//how much the RGB can deviate from the base color
			float offsetRange = 0.2f;
			//check that the new RGB is still in range
			float rOffset; do {rOffset = -(offsetRange/2)+rand.nextFloat()*offsetRange;} while (!(rOffset + newR > 0.0f && rOffset+newR < 1.0f));
			float gOffset; do {gOffset = -(offsetRange/2)+rand.nextFloat()*offsetRange;} while (!(gOffset + newG > 0.0f && gOffset+newG < 1.0f));
			float bOffset; do {bOffset = -(offsetRange/2)+rand.nextFloat()*offsetRange;} while (!(bOffset + newB > 0.0f && bOffset+newB < 1.0f));
			Color c = new Color(newR+rOffset, newG+gOffset, newB+bOffset, 1);
		    thisPalette[i] = c;
		}
		//set the color palette
		wc.setColorPalette(new ColorPalette(thisPalette));
		//empty array of word frequencies that will be passed to wc
        List<WordFrequency> wf = new ArrayList<WordFrequency>();
        //combine the name's LD-dist and LD-diff to create an arbitrary 'score' which will determine what the size of a name will be
        List<Pair<String,Integer>> scores = new ArrayList<Pair<String,Integer>>();
        //our scoring system is smaller = better. we find the maximum score out of our baby names array and invert all the scores, since the word cloud is generated based on bigger frequency = bigger word size.
        int maxScore = 0;
        for (int i = 0; i < babyNamesList.size(); ++i) {
        	int score = babyNamesList.get(i).getMiddle() + (babyNamesList.get(i).getRight()*5);
        	if (maxScore < score) {
        		maxScore = score;
        	}
        	scores.add(Pair.of(babyNamesList.get(i).getLeft(), score));
        }
        //invert the scores and add a scaling factor so that the word sizes are more exponential
        for (int i = 0; i < babyNamesList.size(); ++i) {
        	WordFrequency thisWf = new WordFrequency(scores.get(i).getLeft(), (int)(Math.pow((double)(maxScore-scores.get(i).getRight()),2.0)));
        	wf.add(thisWf);
        }
        wc.build(wf);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        wc.writeToStreamAsPNG(output);
        wordCloudImageBytes =  output.toByteArray();
        
	}
	/**
	 * Fulfills getTime() in ReportLog superclass.
	 * @return the time the task started.
	 */
	public LocalDateTime getTime() {
		return this.time;
	}
	/**
	 * Fulfills getoReport() in ReportLog superclass.
	 * @return a summary of the task.
	 */
	public String getoReport() {
		return "Baby " +  (babyGender.equals("M")? "boy":"girl")+ " for " + fatherName+" (" + fatherYOB + "), " + motherName + " ("+motherYOB+"), Vintage year: "+vintageYear;
	}
	/**
	 * Fulfills getTask() in ReportLog superclass.
	 * @return the name of the task.
	 */
	public String getTask() {
		return "Recommended Baby Name";
	}
	/**
	 * Fulfills getHTML() in ReportLog superclass.
	 * @return a HTML document with the babyNamesList in tabular form, complete with a header briefly describing the table.
	 */
	public byte[] getWordCloudImageBytes() {
		return wordCloudImageBytes;
	}
	public String getHTML() {
		String html = "<head> <style> table, th, td { border: 1px solid black; } table.center { margin-left: auto; margin-right: auto; } </style> </head>";
		html +=  String.format("<h3>Recommended Baby Names for %s and %s</h3>", motherName,fatherName);
		html += "<table><tr><th>Name</th><th>Score</th><th>Name</th><th>Score</th><th>Name</th><th>Score</th><th>Name</th><th>Score</th></tr>";
		String tableRow = "<td>%s</td><td>%s</td>";
		String[][] rows = new String[4][25];
		//System.out.println(babyNamesList.size());
		for (int i = 0; i < babyNamesList.size(); i++) {
			rows[i/25][i%25] = String.format(tableRow, babyNamesList.get(i).getLeft(), babyNamesList.get(i).getRight());
		}
		for (int i = 0; i < 25; ++i) {
			html += "<tr>";
			for (int j = 0; j < 4; ++j) {
				if (i+j*25 >= babyNamesList.size()) {
					html += "<td>_</td><td>_</td>";
				} else
					html += rows[j][i];
			}
			html += "</tr>";
				
		}
		//30 rows
		html += "</table>";
		if (wordCloudImageBytes != null) {
			String base64image = "data:image/png;base64,"+Base64.encodeBase64String(wordCloudImageBytes);
			html += "<img width='500' height='220' src='" + base64image + "'>";
		}
		html = "<div>" + html + "</div>";
		return html;
	}
}
