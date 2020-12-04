package comp3111.popnames.controllers;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class TopNNamesControllerTest extends ApplicationTest{

	private Scene mainscene;
	
	// Error labels
	private Label invalidStart;
	private Label invalidEnd;
	private Label invalidStartRange;
	private Label invalidEndRange;
	private Label invalidNError1;
	private Label invalidNError2;
	
	// User Input components
	private TextField startYear;
	private TextField endYear;
	private TextField nInput;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/interfaces/Main_interface.fxml"));
    	AnchorPane root = (AnchorPane) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.show();
   		mainscene = scene;
   		clickOn("#Main_topNames_Button");
   		
   		startYear = (TextField)mainscene.lookup("#TopNNames_startYear_TextField");
   		endYear = (TextField)mainscene.lookup("#TopNNames_endYear_TextField");
   		nInput = (TextField)mainscene.lookup("#TopNNames_N_TextField");
		
   		invalidStart = (Label)mainscene.lookup("#TopNNames_invalidStart_Label");
   		invalidEnd = (Label)mainscene.lookup("#TopNNames_invalidEnd_Label");
   		invalidStartRange = (Label)mainscene.lookup("#TopNNames_startRangeError_Label");
   		invalidEndRange = (Label)mainscene.lookup("#TopNNames_endRangeError_Label");
   		invalidNError1 = (Label)mainscene.lookup("#TopNNames_NError1_Label");
   		invalidNError2 = (Label)mainscene.lookup("#TopNNames_NError2_Label");
	}
	
	private boolean noError() {
		return (!invalidStart.isVisible() && !invalidEnd.isVisible() && !invalidStartRange.isVisible() && !invalidEndRange.isVisible() && !invalidNError1.isVisible() && !invalidNError2.isVisible());
	}
	
	@Test
	public void normalTest1() {
		startYear.setText("1975");
		endYear.setText("2001");
		nInput.setText("50");
		clickOn("#TopNNames_generate_Button");
		String [][] sample = {
				{"1", "Michael", "68457"},
				{"2", "Jason", "52199"},
				{"3", "Christopher", "46587"},
				{"4", "James", "39595"}};
		boolean noErrors = true;
		TableView table = (TableView)mainscene.lookup("#TopNNames_outputTable_TableView");
		for (int i = 0; i < 4;++i) {
			for (int j = 0; j < 3;++j) {
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors && noError());
	}
	
	@Test
	public void normalTest2() {
		startYear.setText("1900");
		endYear.setText("1920");
		nInput.setText("35");
		clickOn("#TopNNames_isFemale_RadioButton");
		clickOn("#TopNNames_generate_Button");
		String [][] sample = {
				{"1", "Mary", "16707"},
				{"2", "Helen", "6343"},
				{"3", "Anna", "6114"},
				{"4", "Margaret", "5306"}};
		boolean noErrors = true;
		TableView table = (TableView)mainscene.lookup("#TopNNames_outputTable_TableView");
		for (int i = 0; i < 4;++i) {
			for (int j = 0; j < 3;++j) {
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors && noError());
	}
	
	@Test
	public void testEdgeCaseLower() {
		startYear.setText("1880");
		endYear.setText("1950");
		nInput.setText("20");
		clickOn("#TopNNames_generate_Button");
		String [][] sample = {
				{"1", "John", "9655"},
				{"2", "William", "9532"},
				{"3", "James", "5927"},
				{"4", "Charles", "5348"}};
		boolean noErrors = true;
		TableView table = (TableView)mainscene.lookup("#TopNNames_outputTable_TableView");
		for (int i = 0; i < 4;++i) {
			for (int j = 0; j < 3;++j) {
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors && noError());
	}
	
	@Test
	public void testEdgeCaseUpper() {
		startYear.setText("2019");
		endYear.setText("2019");
		nInput.setText("20");
		clickOn("#TopNNames_generate_Button");
		String [][] sample = {
				{"1", "Liam", "20502"},
				{"2", "Noah", "19048"},
				{"3", "Oliver", "13891"},
				{"4", "William", "13542"}};
		boolean noErrors = true;
		TableView table = (TableView)mainscene.lookup("#TopNNames_outputTable_TableView");
		for (int i = 0; i < 4;++i) {
			for (int j = 0; j < 3;++j) {
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors && noError());
	}
	
	@Test
	public void testInvalidStart() {
		startYear.setText("apple");
		endYear.setText("2019");
		nInput.setText("15");
		clickOn("#TopNNames_generate_Button");
		
		assertTrue(invalidStart.isVisible());
		assertTrue(!invalidEnd.isVisible());
		assertTrue(!invalidStartRange.isVisible());
		assertTrue(!invalidEndRange.isVisible());
		assertTrue(!invalidNError1.isVisible());
		assertTrue(!invalidNError2.isVisible());
	}
	
	@Test
	public void testInvalidEnd() {
		startYear.setText("1880");
		endYear.setText("Soap");
		nInput.setText("15");
		clickOn("#TopNNames_generate_Button");
		
		assertTrue(!invalidStart.isVisible());
		assertTrue(invalidEnd.isVisible());
		assertTrue(!invalidStartRange.isVisible());
		assertTrue(!invalidEndRange.isVisible());
		assertTrue(!invalidNError1.isVisible());
		assertTrue(!invalidNError2.isVisible());
	}
	
	@Test
	public void testInvalidN() {
		startYear.setText("1880");
		endYear.setText("1920");
		nInput.setText("What");
		clickOn("#TopNNames_generate_Button");
		
		assertTrue(!invalidStart.isVisible());
		assertTrue(!invalidEnd.isVisible());
		assertTrue(!invalidStartRange.isVisible());
		assertTrue(!invalidEndRange.isVisible());
		assertTrue(invalidNError1.isVisible());
		assertTrue(!invalidNError2.isVisible());
	}
	
	@Test
	public void testInvalidStartRange1() {
		startYear.setText("1776");
		endYear.setText("1920");
		nInput.setText("15");
		clickOn("#TopNNames_generate_Button");
		
		assertTrue(!invalidStart.isVisible());
		assertTrue(!invalidEnd.isVisible());
		assertTrue(invalidStartRange.isVisible());
		assertTrue(!invalidEndRange.isVisible());
		assertTrue(!invalidNError1.isVisible());
		assertTrue(!invalidNError2.isVisible());
	}
	
	@Test
	public void testInvalidStartRange2() {
		startYear.setText("2042");
		endYear.setText("1920");
		nInput.setText("15");
		clickOn("#TopNNames_generate_Button");
		
		assertTrue(!invalidStart.isVisible());
		assertTrue(!invalidEnd.isVisible());
		assertTrue(invalidStartRange.isVisible());
		assertTrue(!invalidEndRange.isVisible());
		assertTrue(!invalidNError1.isVisible());
		assertTrue(!invalidNError2.isVisible());
	}
	
	@Test
	public void testInvalidEndRange1() {
		startYear.setText("2000");
		endYear.setText("2042");
		nInput.setText("15");
		clickOn("#TopNNames_generate_Button");
		
		assertTrue(!invalidStart.isVisible());
		assertTrue(!invalidEnd.isVisible());
		assertTrue(!invalidStartRange.isVisible());
		assertTrue(invalidEndRange.isVisible());
		assertTrue(!invalidNError1.isVisible());
		assertTrue(!invalidNError2.isVisible());
	}
	
	@Test
	public void testInvalidEndRange2() {
		startYear.setText("2000");
		endYear.setText("1999");
		nInput.setText("15");
		clickOn("#TopNNames_generate_Button");
		
		assertTrue(!invalidStart.isVisible());
		assertTrue(!invalidEnd.isVisible());
		assertTrue(!invalidStartRange.isVisible());
		assertTrue(invalidEndRange.isVisible());
		assertTrue(!invalidNError1.isVisible());
		assertTrue(!invalidNError2.isVisible());
	}
	
	@Test
	public void testInvalidNRange() {
		startYear.setText("2000");
		endYear.setText("2010");
		nInput.setText("-1337");
		clickOn("#TopNNames_generate_Button");
		
		assertTrue(!invalidStart.isVisible());
		assertTrue(!invalidEnd.isVisible());
		assertTrue(!invalidStartRange.isVisible());
		assertTrue(!invalidEndRange.isVisible());
		assertTrue(invalidNError1.isVisible());
		assertTrue(invalidNError2.isVisible());
	}
	
}
