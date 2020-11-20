package comp3111.rankingAlgo;


/**
 * Factory class used for different rank generation methods
 * @author Yuxi Sun
 * v1.0
 */

public class RankingAlgorithmFactory{
	/**
	 * 
	 * @param rankingMethod the method of evaluating rankings: ["scr" Standard Competition Ranking,"mcr" Modified Competition Ranking,"dr" Dense Ranking,"or" Ordinal Ranking]
	 * @param name name being searched.
	 * @param gender gender of search
	 * @param yob year of search
	 * @param country country of search
	 * @param type type of dataset being searched
	 * @param resolution method of resolving unfound names ["standard": return #unique names in specified dataset + 1]
	 * @return
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

}
