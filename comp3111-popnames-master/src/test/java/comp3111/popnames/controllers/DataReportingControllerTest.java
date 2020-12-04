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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class DataReportingControllerTest extends ApplicationTest{
	private Scene s;
	private TextField nameTf;
	private TextField yearTf;
	private Label nameErrorLabel;
	private Label yearErrorLabel;
	private Label summaryOutputLabel;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/interfaces/Main_interface.fxml"));
    	AnchorPane root = (AnchorPane) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.show();
   		s = scene;
   		clickOn("#Main_dataReporting_Button");
   		nameTf = (TextField)s.lookup("#DataReporting_nameField_TextField");
   		yearTf = (TextField)s.lookup("#DataReporting_yearField_TextField");
   		nameErrorLabel = (Label)s.lookup("#DataReporting_errorName_Label");
   		yearErrorLabel = (Label)s.lookup("#DataReporting_errorYear_Label");
   		summaryOutputLabel = (Label)s.lookup("#DataReporting_rankAndSummaryOutput_Label");
	}
	
	@Test
	public void rankNormalTest() {
		nameTf.setText("Johnathan");
		yearTf.setText("1960");
		clickOn("#DataReporting_isMale_RadioButton");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(summaryOutputLabel.getText().contains("The Rank of Johnathan in 1960 is 652."));
		nameTf.setText("Simon");
		yearTf.setText("2000");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(summaryOutputLabel.getText().contains("The Rank of Simon in 2000 is 263."));
		nameTf.setText("Alicia");
		yearTf.setText("1997");
		clickOn("#DataReporting_isFemale_RadioButton");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(summaryOutputLabel.getText().contains("The Rank of Alicia in 1997 is 89."));
	}
	@Test
	public void topFiveNormalTest1() {
		yearTf.setText("1997");
		clickOn("#DataReporting_isFemale_RadioButton");
		clickOn("#DataReporting_getTopFive_Button");
		String [][] sample = {
				{"1", "Emily"},
				{"2", "Jessica"},
				{"3", "Ashley"},
				{"4", "Sarah"},
				{"5", "Hannah"}
				};
		boolean noErrors = true;
		TableView table = (TableView)s.lookup("#DataReporting_top5Table_TableView");
		for (int i = 0; i < 5;++i) {//row
			for (int j = 0; j < 2;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors);
	}
	@Test
	public void topFiveNormalTest2() {
		yearTf.setText("2015");
		clickOn("#DataReporting_isMale_RadioButton");
		clickOn("#DataReporting_getTopFive_Button");
		String [][] sample2 = {
				{"1", "Noah"},
				{"2", "Liam"},
				{"3", "Mason"},
				{"4", "Jacob"},
				{"5", "William"}
				};
		boolean noErrors = true;
		TableView table = (TableView)s.lookup("#DataReporting_top5Table_TableView");
		for (int i = 0; i < 5;++i) {//row
			for (int j = 0; j < 2;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample2[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors);
		
		
	}
	
	@Test
	public void topFiveNormalTest3() {
		yearTf.setText("1880");
		clickOn("#DataReporting_isFemale_RadioButton");
		clickOn("#DataReporting_getTopFive_Button");
		String [][] sample3 = {
				{"1", "Mary"},
				{"2", "Anna"},
				{"3", "Emma"},
				{"4", "Elizabeth"},
				{"5", "Minnie"}
				};
		boolean noErrors = true;
		TableView table = (TableView)s.lookup("#DataReporting_top5Table_TableView");
		for (int i = 0; i < 5;++i) {//row
			for (int j = 0; j < 2;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample3[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors);
	}
	
	@Test
	public void summaryNormalTest() {
		yearTf.setText("1990");
		clickOn("#DataReporting_getSummary_Button");
		assertTrue(summaryOutputLabel.getText().contains("Total Births = 3,950,252")
				&& summaryOutputLabel.getText().contains("Baby Girls = 1,897,709")
				&& summaryOutputLabel.getText().contains("Baby Boys = 2,052,543")
				&& summaryOutputLabel.getText().contains("Unique Names (baby girls) = 15,231")
				&& summaryOutputLabel.getText().contains("Unique Names (baby boys) = 9,482"));
		yearTf.setText("2019");
		clickOn("#DataReporting_getSummary_Button");
		assertTrue(summaryOutputLabel.getText().contains("Total Births = 3,445,321")
				&& summaryOutputLabel.getText().contains("Baby Girls = 1,665,373")
				&& summaryOutputLabel.getText().contains("Baby Boys = 1,779,948")
				&& summaryOutputLabel.getText().contains("Unique Names (baby girls) = 17,905")
				&& summaryOutputLabel.getText().contains("Unique Names (baby boys) = 14,049"));
	}
	@Test
	public void noNameTest() {
		nameTf.setText("Asdfghjkl");
		yearTf.setText("1960");
		clickOn("#DataReporting_isMale_RadioButton");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(summaryOutputLabel.getText().contains("Sorry"));
	}
	@Test
	public void invalidNameTest() {
		nameTf.setText("");
		yearTf.setText("1940");
		clickOn("#DataReporting_isMale_RadioButton");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(nameErrorLabel.isVisible());
		nameTf.setText("m  aa  m");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(nameErrorLabel.isVisible());
	}
	@Test
	public void invalidYearTest() {
		nameTf.setText("Johnathan");
		yearTf.setText("&eO");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(yearErrorLabel.isVisible());
		clickOn("#DataReporting_getTopFive_Button");
		assertTrue(yearErrorLabel.isVisible());
		clickOn("#DataReporting_getSummary_Button");
		assertTrue(yearErrorLabel.isVisible());
		yearTf.setText("1879");
		clickOn("#DataReporting_isMale_RadioButton");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(yearErrorLabel.isVisible());
		clickOn("#DataReporting_getTopFive_Button");
		assertTrue(yearErrorLabel.isVisible());
		clickOn("#DataReporting_getSummary_Button");
		assertTrue(yearErrorLabel.isVisible());
		yearTf.setText("2020");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(yearErrorLabel.isVisible());
		clickOn("#DataReporting_getTopFive_Button");
		assertTrue(yearErrorLabel.isVisible());
		clickOn("#DataReporting_getSummary_Button");
		assertTrue(yearErrorLabel.isVisible());
		yearTf.setText("2019");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(!yearErrorLabel.isVisible());
		clickOn("#DataReporting_getTopFive_Button");
		assertTrue(!yearErrorLabel.isVisible());
		clickOn("#DataReporting_getSummary_Button");
		assertTrue(!yearErrorLabel.isVisible());
		yearTf.setText("1880");
		clickOn("#DataReporting_getRank_Button");
		assertTrue(!yearErrorLabel.isVisible());
		clickOn("#DataReporting_getTopFive_Button");
		assertTrue(!yearErrorLabel.isVisible());
		clickOn("#DataReporting_getSummary_Button");
		assertTrue(!yearErrorLabel.isVisible());
	}
}
