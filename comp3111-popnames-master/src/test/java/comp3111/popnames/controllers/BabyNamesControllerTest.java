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

public class BabyNamesControllerTest extends ApplicationTest{
	private Scene s;
	private TextField mNameTf;
	private TextField fNameTf;
	private TextField mYobTf;
	private TextField fYobTf;
	private TextField vYearTf;
	private Label mNameErrorLabel;
	private Label fNameErrorLabel;
	private Label mYobErrorLabel;
	private Label fYobErrorLabel;
	private Label vYearErrorLabel;
	private Label wcErrorLabel;

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/interfaces/Main_interface.fxml"));
    	AnchorPane root = (AnchorPane) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.show();
   		s = scene;
   		clickOn("#Main_babyNames_Button");
   		
   		mNameTf = (TextField)s.lookup("#BabyNames_motherName_textField");
		fNameTf = (TextField)s.lookup("#BabyNames_fatherName_textField");
		mYobTf = (TextField)s.lookup("#BabyNames_motherYear_textField");
		fYobTf = (TextField)s.lookup("#BabyNames_fatherYear_textField");
		vYearTf = (TextField)s.lookup("#BabyNames_vintageYear_textField");
		mNameErrorLabel = (Label)s.lookup("#BabyNames_motherNameError_label");
		fNameErrorLabel = (Label)s.lookup("#BabyNames_fatherNameError_label");
		mYobErrorLabel = (Label)s.lookup("#BabyNames_motherYearError_label");
		fYobErrorLabel = (Label)s.lookup("#BabyNames_fatherYearError_label");
		vYearErrorLabel = (Label)s.lookup("#BabyNames_vintageYearError_label");
		wcErrorLabel = (Label)s.lookup("#BabyNames_wordCloudError_label");
	}

	@Test
	public void normalTest() {
		clickOn("#BabyNames_information_button");
		clickOn("#BabyNames_information_button");
		mNameTf.setText("Julia");
		fNameTf.setText("Winston");
		mYobTf.setText("1984");
		fYobTf.setText("1984");
		vYearTf.setText("");
		//gender assumed male
		clickOn("#BabyNames_generate_button");

		String [][] sample = {
				{"Austin", "96.58%"},
				{"Jason", "95.73%"},
				{"Dustin", "94.87%"},
				{"Judson", "94.02%"},
				{"Fulton", "93.16%"},
				};
		boolean noErrors = true;
		TableView table = (TableView)s.lookup("#BabyNames_tableView");
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
		assertTrue(noErrors
				&& mNameErrorLabel.getText().isEmpty()
				&& fNameErrorLabel.getText().isEmpty()
				&& mYobErrorLabel.getText().isEmpty()
				&& fYobErrorLabel.getText().isEmpty()
				&& vYearErrorLabel.getText().isEmpty()
				&& wcErrorLabel.getText().isEmpty());
	}
	@Test
	public void ageVariationTest() {
		mNameTf.setText("Amy");
		fNameTf.setText("Simon");
		mYobTf.setText("1996");
		fYobTf.setText("2000");
		vYearTf.setText("2014");
		clickOn("#BabyNames_female_radioButton");
		
		clickOn("#BabyNames_generate_button");

		String [][] sample = {
				{"Aimee", "97.3%"},
				{"Ailyn", "96.4%"},
				{"Iman", "95.5%"},
				{"Samya", "94.59%"},
				{"Siya", "93.69%"},
				};
		boolean noErrors = true;
		TableView table = (TableView)s.lookup("#BabyNames_tableView");
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
		assertTrue(noErrors
				&& mNameErrorLabel.getText().isEmpty()
				&& fNameErrorLabel.getText().isEmpty()
				&& mYobErrorLabel.getText().isEmpty()
				&& fYobErrorLabel.getText().isEmpty()
				&& vYearErrorLabel.getText().isEmpty()
				&& wcErrorLabel.getText().isEmpty());

		mNameTf.setText("Michaela");
		fNameTf.setText("Michael");
		mYobTf.setText("2000");
		fYobTf.setText("1996");
		vYearTf.setText("");
		clickOn("#BabyNames_male_radioButton");
		
		clickOn("#BabyNames_generate_button");

		String [][] sample2 = {
				{"Mitchell", "97.2%"},
				{"Michaiah", "96.26%"},
				{"Mikaele", "95.33%"},
				{"Mihailo", "94.39%"},
				{"Micaiah", "92.52%"},
				};
		noErrors = true;
		table = (TableView)s.lookup("#BabyNames_tableView");
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
		assertTrue(noErrors
				&& mNameErrorLabel.getText().isEmpty()
				&& fNameErrorLabel.getText().isEmpty()
				&& mYobErrorLabel.getText().isEmpty()
				&& fYobErrorLabel.getText().isEmpty()
				&& vYearErrorLabel.getText().isEmpty()
				&& wcErrorLabel.getText().isEmpty());
	}
	@Test
	public void nameErrorTest() {
		mNameTf.setText("");
		fNameTf.setText("");
		clickOn("#BabyNames_generate_button");
		assertTrue(mNameErrorLabel.getText().contains("Please input a name")
				&& fNameErrorLabel.getText().contains("Please input a name")
				&& mYobErrorLabel.getText().isEmpty()
				&& fYobErrorLabel.getText().isEmpty()
				&& vYearErrorLabel.getText().isEmpty()
				&& wcErrorLabel.getText().isEmpty());
		mNameTf.setText("n aa m");
		fNameTf.setText("m aa n");
		clickOn("#BabyNames_generate_button");
		assertTrue(mNameErrorLabel.getText().contains("Please input a valid name")
				&& fNameErrorLabel.getText().contains("Please input a valid name")
				&& mYobErrorLabel.getText().isEmpty()
				&& fYobErrorLabel.getText().isEmpty()
				&& vYearErrorLabel.getText().isEmpty()
				&& wcErrorLabel.getText().isEmpty());
	}
	@Test
	public void yearErrorTest() {
		mNameTf.setText("Jessica");
		fNameTf.setText("Alex");
		
		mYobTf.setText("223");
		fYobTf.setText("22000");
		vYearTf.setText("103");
		clickOn("#BabyNames_generate_button");
		assertTrue(mNameErrorLabel.getText().isEmpty()
				&& fNameErrorLabel.getText().isEmpty()
				&& mYobErrorLabel.getText().contains("Mother's YOB is out of range")
				&& fYobErrorLabel.getText().contains("Father's YOB is out of range")
				&& vYearErrorLabel.getText().contains("Vintage year is out of range")
				&& wcErrorLabel.getText().isEmpty());
		
		mYobTf.setText("22000");
		fYobTf.setText("222");
		vYearTf.setText("22220");
		clickOn("#BabyNames_generate_button");
		assertTrue(mNameErrorLabel.getText().isEmpty()
				&& fNameErrorLabel.getText().isEmpty()
				&& mYobErrorLabel.getText().contains("Mother's YOB is out of range")
				&& fYobErrorLabel.getText().contains("Father's YOB is out of range")
				&& vYearErrorLabel.getText().contains("Vintage year is out of range")
				&& wcErrorLabel.getText().isEmpty());
		
		mYobTf.setText("???");
		fYobTf.setText("garbage");
		vYearTf.setText("&eO");
		clickOn("#BabyNames_generate_button");
		assertTrue(mNameErrorLabel.getText().isEmpty()
				&& fNameErrorLabel.getText().isEmpty()
				&& mYobErrorLabel.getText().contains("Mother's YOB is invalid")
				&& fYobErrorLabel.getText().contains("Father's YOB is invalid")
				&& vYearErrorLabel.getText().contains("Vintage year is invalid")
				&& wcErrorLabel.getText().isEmpty());
		
		mYobTf.setText("");
		fYobTf.setText("");
		vYearTf.setText("");
		clickOn("#BabyNames_generate_button");
		assertTrue(mNameErrorLabel.getText().isEmpty()
				&& fNameErrorLabel.getText().isEmpty()
				&& mYobErrorLabel.getText().contains("Mother's YOB is invalid")
				&& fYobErrorLabel.getText().contains("Father's YOB is invalid")
				&& vYearErrorLabel.getText().contains("")
				&& wcErrorLabel.getText().isEmpty());
		
	}
}
