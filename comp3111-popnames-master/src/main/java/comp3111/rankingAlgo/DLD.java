package comp3111.rankingAlgo;
/**
 * Damerau–Levenshtein distance, a method of calculating similarities between two strings (names).
 * @author Marty Sun
 * v1.0
 */
public class DLD {
	/**
	 * Code is written based off the Algorithm defined here https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance
	 * @param a string or name
	 * @param b string or name
	 * @return float giving the number of changes needed to change string a to string b
	 */
	public static int calculate(String a, String b) {
		int[][] dp = new int[a.length()+1][b.length()+1];
		for (int i = 0; i< a.length(); ++i)
			dp[i][0] = 0;
		for (int i = 0; i< b.length(); ++i)
			dp[0][i] = 0;
		for (int i = 0; i<a.length();++i) {
			for (int j = 0; j<b.length(); ++j) {
				int cost = (a.charAt(i) == b.charAt(j))?0:1;
				dp[i][j] = Math.min(Math.min(dp[i-1][j] + 1, dp[i][j-1] + 1),dp[i-1][j-1] + cost);
				if (i>1 && j > 1 && a.charAt(i) == b.charAt(j-1)&& a.charAt(i-1) == b.charAt(j)) {
					dp[i][j] = Math.min(dp[i][j], dp[i-2][j-2] + 1);
				}
			}
		}
		return dp[a.length()][b.length()];
	}
}