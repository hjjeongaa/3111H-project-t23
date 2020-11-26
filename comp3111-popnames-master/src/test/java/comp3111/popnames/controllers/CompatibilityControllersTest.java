package comp3111.popnames.controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

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
public class CompatibilityControllersTest extends ApplicationTest{

	private Scene s;
	//user content
	private ComboBox<String> uType;
	private ComboBox<String> uCountry;
	private ComboBox<String> uYOB;
	//mate content
	private ComboBox<String> mType;
	private ComboBox<String> mCountry;
	private ComboBox<String> preference;
	
	//errors
	private Text uNameError;
	private Text uTypeError;
	private Text uCountryError;
	private Text uYOBError;
	
	private Text mNameError;
	private Text mTypeError;
	private Text mCountryError;
	private Text preferenceError;

	private Text rangeError;
	
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
   		uType = (ComboBox<String>) s.lookup("#T6_user_type_ComboBox");
   		uCountry = (ComboBox<String>) s.lookup("#T6_user_country_ComboBox");
   		uYOB = (ComboBox<String>) s.lookup("#T6_user_yob_ComboBox");
   		mType = (ComboBox<String>) s.lookup("#T6_mate_type_ComboBox");
   		mCountry = (ComboBox<String>) s.lookup("#T6_mate_country_ComboBox");
   		preference = (ComboBox<String>) s.lookup("#T6_preferences_ComboBox");
   		
   		
   		//load errors
   		uNameError = (Text) s.lookup("#T6_user_name_error_Text");
   		uTypeError = (Text) s.lookup("#T6_user_type_error_Text");
   		uCountryError = (Text) s.lookup("#T6_country_type_error_Text");
   		uYOBError = (Text) s.lookup("#T6_user_yob_error_Text");

   		mNameError = (Text) s.lookup("#T6_mate_name_error_Text");
   		mTypeError = (Text) s.lookup("#T6_mate_type_error_Text");
   		mCountryError = (Text) s.lookup("#T6_mate_country_error_Text");
   		preferenceError = (Text) s.lookup("#T6_pref_null_error_Text");
   		
   		rangeError = (Text) s.lookup("#T6_pref_range_error_Text");
   		
	}
	@After
	public void tearDown() throws Exception {
	}
	
	//test error messages
	@Test
	public void testErrorAndReset() {	
		clickOn("#Main_compatibility_Button");
		clickOn("#T6_user_type_ComboBox");
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		//check that entries have been reset to null
		if (!isComboBoxEmpty(uCountry) || !isComboBoxEmpty(uYOB) || !isComboBoxEmpty(preference)) {
			//if the values aren't reset then fail this test
			assertTrue(false);
		}
		clickOn("#T6_generate_Button");
		//check that country and yob errors are shown
		if (!(uCountryError.isVisible() && uYOBError.isVisible() && preferenceError.isVisible()))
			assertTrue(false);
		//select human
		clickOn("#T6_user_type_ComboBox");
		type(KeyCode.UP);
		type(KeyCode.ENTER);
		//select country 
		clickOn("#T6_user_country_ComboBox");
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn("#T6_generate_Button");
		//no errors should be shown
		assertFalse(uCountryError.isVisible() || uYOBError.isVisible() || preferenceError.isVisible());
	}
	

	//test info box hide shows

	@Test
	public void testInfobox() {	
		TextArea info = (TextArea) s.lookup("#T6_infobox_TextArea");
		TextArea compInfo = (TextArea) s.lookup("#T6_composite_infobox_TextArea");
		TextArea parmInfo = (TextArea) s.lookup("#T6_parm_infobox_TextArea");
		TextArea pasrmInfo = (TextArea) s.lookup("#T6_pasrm_infobox_TextArea");
		TextArea ldInfo = (TextArea) s.lookup("#T6_ld_infobox_TextArea");
		clickOn("#Main_compatibility_Button");
		moveTo("#T6_more_info_Text");
		if (!info.isVisible()||compInfo.isVisible()||parmInfo.isVisible()||pasrmInfo.isVisible()||ldInfo.isVisible()) {
			// if any infobox other than composite infobox is shown fail test case
			System.out.println(1);
			assertTrue(false);
		}
		moveTo("#T6_composite_Text");
		if (info.isVisible()||!compInfo.isVisible()||parmInfo.isVisible()||pasrmInfo.isVisible()||ldInfo.isVisible()) {
			// if any infobox other than composite infobox is shown fail test case
			System.out.println(2);
			assertTrue(false);
		}
		moveTo("#T6_parm_Text");
		if (info.isVisible()||compInfo.isVisible()||!parmInfo.isVisible()||pasrmInfo.isVisible()||ldInfo.isVisible()) {
			// if any infobox other than composite infobox is shown fail test case
			System.out.println(3);
			assertTrue(false);
			
		}
		moveTo("#T6_pasrm_Text");
		if (info.isVisible()||compInfo.isVisible()||parmInfo.isVisible()||!pasrmInfo.isVisible()||ldInfo.isVisible()) {
			// if any infobox other than composite infobox is shown fail test case
			System.out.println(5);
			assertTrue(false);
		}
		moveTo("#T6_ld_Text");
		if (info.isVisible()||compInfo.isVisible()||parmInfo.isVisible()||pasrmInfo.isVisible()||!ldInfo.isVisible()) {
			// if any infobox other than composite infobox is shown fail test case
			System.out.println(5);
			assertTrue(false);
		}
		moveTo("#T6_generate_Button");
		// if any infobox is shown fail test case
		assertFalse(info.isVisible()||compInfo.isVisible()||parmInfo.isVisible()||pasrmInfo.isVisible()||ldInfo.isVisible());
		
	}
	//remove unnecessary check
	private boolean isComboBoxEmpty(ComboBox<String> a) {
		return a.getValue()==null || a.getValue().isBlank();
	}

	@Test
	public void testPreference() {	
		clickOn("#Main_compatibility_Button");
		clickOn("#T6_user_yob_ComboBox");
		for(int i = 0; i<61;++i) {
			type(KeyCode.UP);//set user YOB to 1880
		}
		type(KeyCode.ENTER);
		clickOn("#T6_mate_country_ComboBox");
		type(KeyCode.DOWN);//select Scotland
		type(KeyCode.ENTER);
		Text perferenceRangeError = (Text) s.lookup("#T6_pref_range_error_Text");
		assertTrue(perferenceRangeError.isVisible());
	}
	//empty names
	@Test
	public void testEmptyUserName() {	
		clickOn("#Main_compatibility_Button");
		TextField uName = (TextField) s.lookup("#T6_user_name_TextField");
		uName.setText("");
		clickOn("#T6_user_country_ComboBox");
		type(KeyCode.DOWN);//select Scotland
		type(KeyCode.ENTER);
		clickOn("#T6_generate_Button");
		Text uNameError = (Text) s.lookup("#T6_user_name_error_Text");
		assertTrue(uNameError.isVisible());
	}
	@Test
	public void testEmptyMateName() {	
		clickOn("#Main_compatibility_Button");
		clickOn("#T6_mate_female_RadioButton");
		clickOn("#T6_user_female_RadioButton");
		TextField mateName = (TextField) s.lookup("#T6_mate_name_TextField");
		mateName.setText("");
		clickOn("#T6_mate_country_ComboBox");
		type(KeyCode.DOWN);//select Scotland
		type(KeyCode.ENTER);
		clickOn("#T6_generate_Button");
		Text mateNameError = (Text) s.lookup("#T6_mate_name_error_Text");
		assertTrue(mateNameError.isVisible());
	}
	public void testValidEntryBothFemale() {	
		clickOn("#Main_compatibility_Button");
		clickOn("#T6_mate_female_RadioButton");
		clickOn("#T6_user_female_RadioButton");
		clickOn("#T6_generate_Button");
		boolean userError = uNameError.isVisible() || uTypeError.isVisible() || uCountryError.isVisible()|| uYOBError.isVisible();
		
		boolean mateError = mNameError.isVisible() || mTypeError.isVisible() || mCountryError.isVisible() || preferenceError.isVisible() || rangeError.isVisible();
		assertTrue(userError || mateError);
	}
	//change mate country check
	//test mate type change
	//test mate 
	
}
