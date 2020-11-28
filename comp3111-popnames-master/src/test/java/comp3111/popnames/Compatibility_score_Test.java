package comp3111.popnames;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Compatibility_score_Test {
	PredictCompatibilityScore report;
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void compatibilityScoreTest1() {
        report = new PredictCompatibilityScore("Joon", "M", 2000, "usa", "human" 
        		, "Ava", "F", 0, "usa", "human", "scr", "ld");
        HashMap<String,Double> oScore = report.getoScore();
        assertTrue(roundTo2dp(oScore.get("composite")) == 0.34 &&
    		roundTo2dp(oScore.get("parm")) == 0.55 &&
			roundTo2dp(oScore.get("pasrm")) == 0.47 &&
			roundTo2dp(oScore.get("ld")) == 0.0
        );
	}
	@Test
	public void compatibilityScoreTest2() {
        report = new PredictCompatibilityScore("Joon", "M", 2000, "usa", "human" 
        		, "Ava", "M", 0, "usa", "human", "scr", "ld");
        HashMap<String,Double> oScore = report.getoScore();
        assertTrue(roundTo2dp(oScore.get("composite")) == 0.64 &&
    		roundTo2dp(oScore.get("parm")) == 0.94 &&
			roundTo2dp(oScore.get("pasrm")) == 0.98 &&
			roundTo2dp(oScore.get("ld")) == 0.0
        );
	}
	private double roundTo2dp(Double score) {        
		return (double)Math.round(score*100)/100;
	}
}
