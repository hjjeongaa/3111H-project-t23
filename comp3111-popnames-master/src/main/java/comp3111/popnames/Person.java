package comp3111.popnames;


import org.apache.commons.csv.*;

import comp3111.rankingAlgo.RankingAlgorithm;
import comp3111.rankingAlgo.RankingAlgorithmFactory;
import edu.duke.*;
import java.util.*;
/**
 * Person class was created to assist the coding process for Task 4-5-6
 * @author Yuxi Sun
 * v1.1
 */
public class Person {
	private String name;
	private String gender;
	private int    yob;
	private String country;
	private String type;
	
	private int    rank; //rank of name (of said gender) in said data set.
	private int    size; //stores the number of unique names in the yob of said data set

	//getters
	public String getName()		{return name;};
	public String getGender()	{return gender;};
	public int    getYob()		{return yob;};
	public String getCountry()	{return country;};
	public String getType()		{return type;};
	public int    getRank()		{return rank;};
	public int    getSize()		{return size;};

	//mutators

	/**
	 * This function was written for the purpose of task 6 to fetch the rank and size variables.
	 * @param rankingAlgo specifies the type of ranking algorithm that should be used to calculate Ranking.
	 * @param resolver specifies the means of resolving a ranking issue if the name is not found in the data set ["standard" sets the rank to size of data set + 1,
	 * 	"dld" uses the DLD class to perform name matching (based on most similar names) and allows the not found name to adopt another names rank (given they are similar enough)].
	 * @author Yuxi Sun 
	 *  v2.0
	 */
	private void intializeRank(String rankingAlgo, String resolver) {
		RankingAlgorithm algo = RankingAlgorithmFactory.getRankAlgorithm(rankingAlgo,"Jabez","M",2000,"usa","human",resolver);
		this.size = algo.getSize();
		this.rank = algo.getRank();
	}
	
	//constructors
	/**
	 * This is the simple constructor that assumes the RankingAlgo used should be standard competition ranking and the resolution type used should be "standard"
	 * @param name		the name of the person
	 * @param gender	the gender of the person
	 * @param yob		the year of birth of the person
	 * @param country	the country in which the information was scraped from
	 * @param type		the type of database in which the information was scraped from (human/pet)
	 */
	public Person(String name, String gender, int yob, String country, String type){
		this.name = name;
		this.gender = gender;
		this.yob = yob;
		this.country = country;
		this.type = type;
		intializeRank("scr","standard");
	}

	/**
	 * This is the simple constructor that assumes the RankingAlgo used should be standard competition ranking and the resolution type used should be "standard"
	 * @param name		the name of the person
	 * @param gender	the gender of the person
	 * @param yob		the year of birth of the person
	 * @param country	the country in which the information was scraped from
	 * @param type		the type of database in which the information was scraped from (human/pet)
	 * @param rankingAlgo
	 * @param resolution
	 */
	public Person(String name, String gender, int yob, String country, String type, String rankingAlgo, String resolver){
		this.name = name;
		this.gender = gender;
		this.yob = yob;
		this.country = country;
		this.type = type;
		intializeRank(rankingAlgo, resolver);
	}
	/**
	 * Overloaded constructor such that person acts only as a data structure.
	 * @param name		the name of the person
	 * @param gender	the gender of the person
	 * @param yob		the year of birth of the person
	 * @param country	the country in which the information was scraped from
	 * @param type		the type of database in which the information was scraped from (human/pet)
	 * @param rank		the rank of the person
	 */
	public Person(String name, String gender, int yob, String country, String type, int rank){
		this.name = name;
		this.gender = gender;
		this.yob = yob;
		this.country = country;
		this.type = type;
		this.rank = rank;
	}
}