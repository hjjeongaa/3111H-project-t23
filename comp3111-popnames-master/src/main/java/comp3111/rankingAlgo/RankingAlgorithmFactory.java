package comp3111.rankingAlgo;

import java.util.Vector;

/**
 * Factory class used for different rank generation methods
 * @author Yuxi Sun
 * v 2.0
 */

public class RankingAlgorithmFactory{
	// logistical variables
	private static Vector<String> nonIterativeMethods = null;		//stores non iterative (give name get rank) methods supported by current package
	private static Vector<String> iterativeMethods = null;			//stores iterative methods supported by current package
	/**
	 * 
	 * @param rankingMethod the method of evaluating rankings: ["scr" Standard Competition Ranking,"mcr" Modified Competition Ranking,"dr" Dense Ranking,"or" Ordinal Ranking]
	 * @param name name being searched.
	 * @param gender gender of search
	 * @param yob year of search
	 * @param country country of search
	 * @param type type of data set being searched
	 * @param resolution method of resolving not found names ["standard": return #unique names in specified data set + 1]
	 * @return RankingAlgorithm object
	 */
	public static RankingAlgorithm getRankAlgorithm(String rankingMethod, String name, String gender, int yob, String country, String type, String resolution) {
		if("scr".equals(rankingMethod))//standard competition ranking
			return new SCR(name, gender, yob, country, type, resolution);
		else if("mcr".equals(rankingMethod))//modified competition ranking
			return new MCR(name, gender, yob, country, type, resolution);
		else if("dr".equals(rankingMethod))//dense ranking
			return new DR(name, gender, yob, country, type, resolution);
		else if("or".equals(rankingMethod))//ordinal ranking
			return new OR(name, gender, yob, country, type, resolution);
		else return null;
	}
	/**
	 * Used for data set iterators, the RankingAlgorithm function is fed a sequential set frequencies in 
	 * descending order using the addEntry(int freq) function and returns the current rank using the getRank() function. 
	 * 
	 * Note that this function doesn't perform data set accessing itself.
	 * NOTE THAT THESE ITERACTORS CANNOT PERFORM LOOK AHEAD AND HENCE DOES NOT SUPPORT MCR
	 * @param rankingMethod the method of ranking
	 * @param freq the frequency of the first name of the data set of specified gender
	 * @return RankingAlgorithm object
	 */
	public static RankingAlgorithm getRankAlgorithm(String rankingMethod, int freq) {
		if("scr".equals(rankingMethod))//standard competition ranking
			return new SCR(freq);
		else if("dr".equals(rankingMethod))//dense ranking
			return new DR(freq);
		else if("or".equals(rankingMethod))//ordinal ranking
			return new OR(freq);
		else return null;
	}
	public static Vector<String> getNonIterativeMethods(){
		//if unintialized 
		if (nonIterativeMethods == null) {
			nonIterativeMethods = new Vector<String>();
			nonIterativeMethods.add("scr");
			nonIterativeMethods.add("mcr");
			nonIterativeMethods.add("dr");
			nonIterativeMethods.add("or");
		}
		return nonIterativeMethods;
	}
	
	public static Vector<String> getIterativeMethods(){
		//if unintialized 
		if (iterativeMethods == null) {
			iterativeMethods = new Vector<String>();
			iterativeMethods.add("scr");
			iterativeMethods.add("dr");
			iterativeMethods.add("or");
		}
		return iterativeMethods;
	}
}
