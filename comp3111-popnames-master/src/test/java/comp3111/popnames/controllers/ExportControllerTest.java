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

import comp3111.export.ReportHistory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class ExportControllerTest extends ApplicationTest{
	private Scene s;
	//private Label nsError;

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/interfaces/Main_interface.fxml"));
    	AnchorPane root = (AnchorPane) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.show();
   		s = scene;
   		clickOn("#Main_export_Button");
	}
	
	@Test
	public void selectionTests() {
		clickOn("#Main_namePopularity_Button");
		clickOn("#NamePopularity_generate_button");
		//clickOn("#Main_topNames_Button");
		//clickOn("#TopNNames_generate_Button");
		clickOn("#Main_trendingNames_Button");
		clickOn("#T3_generate_Button");
		clickOn("#Main_babyNames_Button");
		clickOn("#BabyNames_generate_button");
		//clickOn("#Main_soulmate_Button");
		//lcickonsoulmategenerate
		clickOn("#Main_compatibility_Button");
		clickOn("#T6_generate_Button");
		
		clickOn("#Main_export_Button");
		clickOn("#Export_selectAll_button");
		boolean allTicked = true;
		for (int i = 0; i < ReportHistory.getReportHoldersList().size(); ++i) {
			if (!ReportHistory.getReportHoldersList().get(i).getSelected().booleanValue()) {
				allTicked = false; break;
			}
		}
		assertTrue(allTicked);
		
		clickOn("#Export_selectNone_button");
		boolean noneTicked = true;
		for (int i = 0; i < ReportHistory.getReportHoldersList().size(); ++i) {
			if (ReportHistory.getReportHoldersList().get(i).getSelected().booleanValue()) {
				noneTicked = false; break;
			}
		}
		assertTrue(noneTicked);
		
		clickOn("#Export_invertSelection_button");
		allTicked = true;
		for (int i = 0; i < ReportHistory.getReportHoldersList().size(); ++i) {
			if (!ReportHistory.getReportHoldersList().get(i).getSelected().booleanValue()) {
				allTicked = false; break;
			}
		}
		assertTrue(allTicked);
		
		clickOn("#Export_exportSelected_button");
		type(KeyCode.ENTER);
	}
}
