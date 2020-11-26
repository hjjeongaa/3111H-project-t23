package comp3111.rankingAlgo;

/**
 * Levenshtein distance, a method of calculating similarities between two strings (names).
 * @author Marty Sun
 * v1.0
 */
public class LD {
	/**
	 * Code is written based off the Algorithm defined here https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance
	 * @param a string or name
	 * @param b string or name
	 * @return int giving the number of changes needed to change string a to string b, returns -1 or -2 if a or b are empty strings respectively.
	 * @author Yuxi Sun
	 */
	public static int calculate(String a, String b) {
		if (a.length() == 0) {
			return -1; //invalid a length
		}
		if (b.length() == 0) {
			return -2; //invalid b length
		}
		int[][] dp = new int[a.length()+1][b.length()+1];
		for (int i = 0; i< a.length()+1; ++i)
			dp[i][0] = i;
		for (int i = 0; i< b.length()+1; ++i)
			dp[0][i] = i;
		for (int i = 1; i<a.length()+1;++i) {
			for (int j = 1; j<b.length()+1; ++j) {
				int cost = (a.charAt(i-1) == b.charAt(j-1))?0:1;
				dp[i][j] = Math.min(Math.min(dp[i-1][j] + 1, dp[i][j-1] + 1),dp[i-1][j-1] + cost);
				if (i>1 && j > 1 && 
				(a.charAt(i-1) == b.charAt(j-2)) &&
				(a.charAt(i-2) == b.charAt(j-1))) {
					dp[i][j] = Math.min(dp[i][j], dp[i-2][j-2] + 1);
				}
			}
		}
		return dp[a.length()][b.length()];
	}
}