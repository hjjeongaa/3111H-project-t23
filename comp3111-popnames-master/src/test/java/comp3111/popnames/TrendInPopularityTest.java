package comp3111.popnames;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TrendInPopularityTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	//Valid
	//boundary
	//1880 2019 usa Male
	@Test
	public void testValidEntryEdgeCaseMale() {	
        TrendInPopularity  report = new TrendInPopularity(1880, 2019, "M", "usa", "human");
		String [][] sample = {
				{"Atreus", "12535", "2008"," 788","2019", "11747"},
				{"Alva", "161", "1882", "12535", "2008","-12374"}
				};
		boolean same = true;
        HashMap<String,Vector<String>> results  = report.getResults(); // fetching results

        for (int i = 0; i <results.get("name").size();++i){//row
    		if(results.get("name").get(i).equals(sample[i][0]) &&
    		results.get("startRank").get(i).equals(sample[i][1])	&&
    		results.get("startYear").get(i).equals(sample[i][2])	&&
    		results.get("endRank").get(i).equals(sample[i][3])	&&
    		results.get("endYear").get(i).equals(sample[i][4]) &&
    		results.get("trend").get(i).equals(sample[i][5])) {
    			same = true;
    		}else {
    			same = false;
    		}
        }
		assertTrue(same);
	}
	//1880 2019 usa Female
	@Test
	public void testValidEntryEdgeCaseFemale() {
        TrendInPopularity  report = new TrendInPopularity(1880, 2019, "F", "usa", "human");
		String [][] sample = {
				{"Coraline", "17619", "2007"," 579","2015", "17040"},
				{"Maude", "20", "1882", "17619", "2007","-17599"}
				};
		boolean same = true;
        HashMap<String,Vector<String>> results  = report.getResults(); // fetching results

        for (int i = 0; i <results.get("name").size();++i){//row
    		if(results.get("name").get(i).equals(sample[i][0]) &&
    		results.get("startRank").get(i).equals(sample[i][1])	&&
    		results.get("startYear").get(i).equals(sample[i][2])	&&
    		results.get("endRank").get(i).equals(sample[i][3])	&&
    		results.get("endYear").get(i).equals(sample[i][4]) &&
    		results.get("trend").get(i).equals(sample[i][5])) {
    			same = true;
    		}else {
    			same = false;
    		}
        }
		assertTrue(same);
	}

	//2018 2019 usa female
	@Test
	public void testValidEntryEdgeCaseSmallestRangeFemale() {	
        TrendInPopularity  report = new TrendInPopularity(2018, 2019, "F", "usa", "human");
		String [][] sample = {
				{"Jenaiah", "13750", "2018"," 4233","2019", "9517"},
				{"Anifer", "3910", "2018", "15442", "2019","-11532"}
				};
		boolean same = true;
        HashMap<String,Vector<String>> results  = report.getResults(); // fetching results

        for (int i = 0; i <results.get("name").size();++i){//row
    		if(results.get("name").get(i).equals(sample[i][0]) &&
    		results.get("startRank").get(i).equals(sample[i][1])	&&
    		results.get("startYear").get(i).equals(sample[i][2])	&&
    		results.get("endRank").get(i).equals(sample[i][3])	&&
    		results.get("endYear").get(i).equals(sample[i][4]) &&
    		results.get("trend").get(i).equals(sample[i][5])) {
    			same = true;
    		}else {
    			same = false;
    		}
        }
		assertTrue(same);
	}
}
