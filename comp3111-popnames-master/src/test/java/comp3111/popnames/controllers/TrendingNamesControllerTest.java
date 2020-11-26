package comp3111.popnames.controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import comp3111.popnames.controllers.Compatibility_controller;
public class TrendingNamesControllerTest extends ApplicationTest {
	private Scene s;
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/interfaces/Main_interface.fxml"));
    	AnchorPane root = (AnchorPane) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
//   		stage.setTitle("Popular Names");
   		stage.show();
   		s = scene;
	}
//
//    

	//test empty country and empty start end year
	//test cases
	@Test
	public void test() {
		//TODO
		clickOn("#Main_trendingNames_Button");
		clickOn("#T3_type_ComboBox");
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn("#T3_generate_Button");
		//check all 3 error messages set
		Text countryError = (Text)s.lookup("#T3_country_error_Text");
		
		Text startYearError = (Text)s.lookup("#T3_start_year_error_Text");
		Text endYearError = (Text)s.lookup("#T3_end_year_error_Text");
		
		assertTrue(countryError.isVisible()&&startYearError.isVisible()&&endYearError.isVisible());
	}
//	same year Error
//	1880 1880
	@Test
	public void testStartEndEquals() {	
		clickOn("#Main_trendingNames_Button");
		clickOn("#T3_endYear_ComboBox");
		for (int i = 0; i<2019-1880;++i)
			type(KeyCode.UP);
		type(KeyCode.ENTER);
		clickOn("#T3_generate_Button");
		Text error = (Text)s.lookup("#T3_range_error_Text");
		assertTrue(error.isVisible() && error.getText().contains("<="));//checking if it is the correct error message

	}
	//start year larger then end year error
	//2001 2000
	@Test
	public void testStartLargerThanEnd() {	
		clickOn("#Main_trendingNames_Button");
		clickOn("#T3_startYear_ComboBox");
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn("#T3_endYear_ComboBox");
		for (int i = 0; i<2019-1880;++i)
			type(KeyCode.UP);
		type(KeyCode.ENTER);
		clickOn("#T3_generate_Button");
		Text error = (Text)s.lookup("#T3_range_error_Text");
		assertTrue(error.isVisible() && error.getText().contains("<="));//checking if it is the correct error message
	}
	//try get left out
	//1880 -> 1879
	@Test
	public void testLeftOutOfBound() {	
		clickOn("#Main_trendingNames_Button");
		//get the initial value of the start year
		ComboBox<String> start = (ComboBox)s.lookup("#T3_startYear_ComboBox");
		String initial = start.getValue();
		//try modify start year to be out of bounds
		clickOn("#T3_startYear_ComboBox");
		type(KeyCode.UP);
		type(KeyCode.ENTER);
		clickOn("#T3_generate_Button");
		assertTrue(start.getValue().equals(initial));//checking if the system does not let you go out of bounds.
	}
	//right out
	//2020 -> 2021 
	@Test
	public void testRightOutOfBound() {	
		clickOn("#Main_trendingNames_Button");
		//get the initial value of the end year
		ComboBox<String> end = (ComboBox)s.lookup("#T3_endYear_ComboBox");
		String initial = end.getValue();
		//try to modify end year to be out of bounds
		clickOn("#T3_endYear_ComboBox");
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn("#T3_generate_Button");
		assertTrue(end.getValue().equals(initial));//checking if the system does not let you go out of bounds.
	}

	
	//Valid
	//boundary
	//1880 2019 usa Male
	@Test
	public void testValidEntryEdgeCaseMale() {	
		clickOn("#Main_trendingNames_Button");
		clickOn("#T3_generate_Button");
		String [][] sample = {
				{"Atreus", "12535", "2008"," 788","2019", "11747"},
				{"Alva", "161", "1882", "12535", "2008","-12374"}
				};
		boolean same = true;
		TableView table = (TableView)s.lookup("#T3_output_Table");
		for (int i = 0; i < 2;++i) {//row
			for (int j = 0; j < 6;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
//			    System.out.println(data+" "+sample[i][j]);
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	same = false;
			    }
			}
		}
		assertTrue(same);
	}
	//1880 2019 usa Female
	@Test
	public void testValidEntryEdgeCaseFemale() {	
		clickOn("#Main_trendingNames_Button");
		clickOn("#T3_female_RadioButton");
		clickOn("#T3_generate_Button");
		String [][] sample = {
				{"Coraline", "17619", "2007"," 579","2015", "17040"},
				{"Maude", "20", "1882", "17619", "2007","-17599"}
				};
		boolean same = true;
		TableView table = (TableView)s.lookup("#T3_output_Table");
		for (int i = 0; i < 2;++i) {//row
			for (int j = 0; j < 6;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
//			    System.out.println(data+" "+sample[i][j]);
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	same = false;
			    }
			}
		}
		assertTrue(same);
	}

	//2018 2019 usa female
	@Test
	public void testValidEntryEdgeCaseSmallestRangeFemale() {	
		clickOn("#Main_trendingNames_Button");
		clickOn("#T3_female_RadioButton");
		clickOn("#T3_startYear_ComboBox");
		for (int i = 0; i<2018-1880;++i)
			type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn("#T3_generate_Button");
		String [][] sample = {
				{"Jenaiah", "13750", "2018"," 4233","2019", "9517"},
				{"Anifer", "3910", "2018", "15442", "2019","-11532"}
				};
		boolean same = true;
		TableView table = (TableView)s.lookup("#T3_output_Table");
		for (int i = 0; i < 2;++i) {//row
			for (int j = 0; j < 6;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
//			    System.out.println(data+" "+sample[i][j]);
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	same = false;
			    }
			}
		}
		assertTrue(same);
	}
	//test infobox
	@Test
	public void testInfoboxShowHide() {	
		clickOn("#Main_trendingNames_Button");
		moveTo("#T3_more_info_Text");
		TextArea info = (TextArea) s.lookup("#T3_infobox_TextArea");
		if (!info.isVisible()) {
			// if any infobox other than composite infobox is shown fail test case
			assertTrue(false);
		}
		moveTo("#T3_generate_Button");
		assertFalse(info.isVisible());//check if infobox is hidden
	}
	
	@After
	public void tearDown() throws Exception {
	}


}
