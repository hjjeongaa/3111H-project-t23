/**
 * RecommendBabyName
 * 
 * For task 4. Outputs two predictions for baby names given a pair of parents' names.
 * @author Hyun Joon Jeong
 */

package comp3111.popnames;

public class RecommendBabyName extends Reports {
	private String fatherName;
	private String motherName;
	private int fatherYOB;
	private int motherYOB;
	private int vintageYear;
	private String boyName;
	private String girlName;
	public RecommendBabyName(String fatherName, String motherName, int fatherYOB, int motherYOB, int vintageYear, String country, String type) {
		super(fatherName+", "+motherName, null, country, type);
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.fatherYOB = fatherYOB;
		this.motherYOB = motherYOB;
		this.vintageYear = vintageYear;
		
		int fatherRank = new PopularityOfName(fatherYOB, fatherYOB, fatherName, "M", country, type).getRankAt(fatherYOB);
		int motherRank = new PopularityOfName(motherYOB, motherYOB, motherName, "F", country, type).getRankAt(motherYOB);
		
		if (fatherRank == 0) fatherRank = 1;
		if (motherRank == 0) motherRank = 1;
		boyName = new TopNNames(vintageYear, vintageYear, "M", country, type).getNameFromIndex(fatherRank-1);
		girlName = new TopNNames(vintageYear, vintageYear, "F", country, type).getNameFromIndex(motherRank-1);
	}
	public String generateReport() {
		String outputReport = String.format("Recommended baby names for %s (born in %d) and %s (born in %d), with a vintage year of %d:\n", fatherName, fatherYOB, motherName, motherYOB, vintageYear);
		outputReport += String.format("Boy's name:\t%s\n", boyName);
		outputReport += String.format("Girl's name:\t%s\n", girlName);
		return outputReport;
	}
}
