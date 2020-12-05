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

public class NamePopularityControllerTest extends ApplicationTest{
	private Scene s;
	private TextField nameTf;
	private TextField sYearTf;
	private TextField eYearTf;
	private Label nameErrorLabel;
	private Label yearErrorLabel;
	private Label summaryLabel;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/interfaces/Main_interface.fxml"));
    	AnchorPane root = (AnchorPane) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.show();
   		s = scene;
   		clickOn("#Main_namePopularity_Button");
   		nameTf = (TextField)s.lookup("#NamePopularity_name_textField");
   		sYearTf = (TextField)s.lookup("#NamePopularity_startYear_textField");
   		eYearTf = (TextField)s.lookup("#NamePopularity_endYear_textField");
   		nameErrorLabel = (Label)s.lookup("#NamePopularity_nameError_label");
   		yearErrorLabel = (Label)s.lookup("#NamePopularity_yearError_label");
   		summaryLabel = (Label)s.lookup("#NamePopularity_summary_label");
	}
	
	@Test
	public void normalTest() {
		nameTf.setText("Alexander");
		clickOn("#NamePopularity_generate_button");
		String [][] sample = {
				{"1995", "21", "19446","1.02%"},
				{"1996", "22", "17959", "0.95%"},
				{"1997", "22", "17110", "0.91%"},
				};
		boolean noErrors = true;
		TableView table = (TableView)s.lookup("#NamePopularity_tableView");
		for (int i = 0; i < 3;++i) {//row
			for (int j = 0; j < 4;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors
				&& nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().isEmpty());
	}
	
	@Test
	public void edgeTests() {
		nameTf.setText("Alexander");
		sYearTf.setText("1880");
		eYearTf.setText("1880");
		clickOn("#NamePopularity_generate_button");
		String [][] sample = {
				{"1880", "73", "211","0.19%"}
				};
		boolean noErrors = true;
		TableView table = (TableView)s.lookup("#NamePopularity_tableView");
		for (int i = 0; i < 1;++i) {//row
			for (int j = 0; j < 4;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors
				&& nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().isEmpty());
		
		nameTf.setText("Amy");
		sYearTf.setText("2019");
		eYearTf.setText("2019");
		clickOn("#NamePopularity_female_radioButton");
		clickOn("#NamePopularity_generate_button");
		String [][] sample2 = {
				{"2019", "203", "1471","0.09%"}
				};
		noErrors = true;
		//TableView table = (TableView)s.lookup("#NamePopularity_tableView");
		for (int i = 0; i < 1;++i) {//row
			for (int j = 0; j < 4;++j) {//col
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if (!data.strip().equals(sample2[i][j].strip())) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors
				&& nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().isEmpty());
	}
	
	@Test
	public void notFoundTest() {
		nameTf.setText("Asdfghjkl");
		sYearTf.setText("2014");
		eYearTf.setText("2019");
		clickOn("#NamePopularity_generate_button");
		boolean noErrors = true;
		TableView table = (TableView)s.lookup("#NamePopularity_tableView");
		for (int i = 0; i < 6;++i) {//row
			for (int j = 0; j < 4;++j) {//col
				if (j == 0 || j == 2) continue;
			    TableColumn col = (TableColumn) table.getColumns().get(j);//get jth cols data
			    String data = (String) col.getCellObservableValue(table.getItems().get(i)).getValue();
			    if ((j == 1 && !data.strip().equals("Not found")) ||( j == 3 && !data.strip().equals("0%"))) {
			    	System.out.println(i+" "+j);
			    	noErrors = false;
			    }
			}
		}
		assertTrue(noErrors
				&& nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().isEmpty());
	}
	@Test
	public void invalidNameTest() {
		
		nameTf.setText("Ha ha ha");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().contains("valid")
				&& yearErrorLabel.getText().isEmpty());
		
		nameTf.setText("    \t ");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().contains("Please input a name")
				&& yearErrorLabel.getText().isEmpty());
	}
	
	@Test
	public void invalidYearTest() {
		nameTf.setText("Johnathan");
		
		sYearTf.setText("0");
		eYearTf.setText("20000");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().contains("start year is out of range"));
		
		sYearTf.setText("2005");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().contains("end year is out of range"));
		
		sYearTf.setText("Chernobyl");
		eYearTf.setText("AZ-5");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().contains("start year is invalid"));
		
		sYearTf.setText("1986");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().contains("end year is invalid"));
	}
	
	@Test
	public void yearRangeTest() {
		nameTf.setText("Johnathan");
		
		sYearTf.setText("2000");
		eYearTf.setText("1980");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().contains("end year is out of range"));
		
		sYearTf.setText("1880");
		eYearTf.setText("1879");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().contains("end year is out of range"));
		
		sYearTf.setText("2020");
		eYearTf.setText("2019");
		clickOn("#NamePopularity_generate_button");
		assertTrue(nameErrorLabel.getText().isEmpty()
				&& yearErrorLabel.getText().contains("start year is out of range"));
	}
}
