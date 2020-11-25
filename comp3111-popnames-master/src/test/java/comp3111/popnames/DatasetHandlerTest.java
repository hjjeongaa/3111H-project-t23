package comp3111.popnames;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.util.Pair;

public class DatasetHandlerTest {
	Vector<String> types; 
	Vector<String> countries;
	@Before
	public void setUp() throws Exception {
		types = DatasetHandler.getTypes();
		countries = DatasetHandler.getCountries("human");
	}

	@After
	public void tearDown() throws Exception {
	}
	//perform full range testing
	//getTypes
	@Test
	public void getTypesTestValid() {
		assertTrue(types.contains("human") && types.contains("pet"));
	}
	@Test
	public void getTypesTestInvalid() {
		assertFalse(types.contains("aliens"));
	}
	//getCountries
	@Test
	public void getCountriesTestValidUSA() {
		assertTrue(countries.contains("usa"));
	}
	@Test
	public void getCountriesTestValidScotland() {
		assertTrue(countries.contains("scotland"));
	}	
	@Test
	public void getCountriesTestValidNorway() {
		assertTrue(countries.contains("norway"));
	}
	@Test
	public void getCountriesTestValidNI() {
		assertTrue(countries.contains("NorthernIreland"));
	}
	@Test
	public void getCountriesTestValidFrance() {
		assertTrue(countries.contains("France"));
	}
	@Test
	public void getCountriesTestValidEnglandAndWales() {
		assertTrue(countries.contains("EnglandAndWales"));
	}
	@Test
	public void getCountriesTestInvalid() {
		assertFalse(countries.contains("DonaldTrump"));
	}
	//Range testing
	@Test
	public void getValidRangeTestValidEnglandAndWales() {
		Pair<String,String> validRange = DatasetHandler.getValidRange("human","EnglandAndWales");
		assertTrue(validRange.getKey().equals("1996") && validRange.getValue().equals("2016"));
	}
	@Test
	public void getValidRangeTestValidUSA() {
		Pair<String,String> validRange = DatasetHandler.getValidRange("human","usa");
		assertTrue(validRange.getKey().equals("1880") && validRange.getValue().equals("2019"));
	}
	@Test
	public void getValidRangeTestInvalidCountry() {
		Pair<String,String> validRange = DatasetHandler.getValidRange("human","TrumpLand");
		assertTrue(validRange == null);
	}
	@Test
	public void getValidRangeTestInvalidType() {
		Pair<String,String> validRange = DatasetHandler.getValidRange("alien","usa");
		assertTrue(validRange == null);
	}
	@Test
	public void getValidRangeTestInvalidTypeandCountry() {
		Pair<String,String> validRange = DatasetHandler.getValidRange("alien","TrumpLand");
		assertTrue(validRange == null);
	}
}
