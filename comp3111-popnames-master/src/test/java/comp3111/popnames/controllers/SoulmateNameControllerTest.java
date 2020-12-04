package comp3111.popnames.controllers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SoulmateNameControllerTest extends ApplicationTest{
	
	private Scene mainscene;
	
	// Info Pane
	private Pane infoPane; // Soulmate_infoPane_Pane
	
	// Error Labels
	private Label nameError; // Soulmate_errorName_Label
	private Label YOBError;  // Soulmate_errorYear_Label
	
	// User input components
	private TextField name; // Soulmate_inputName_TextField
	private TextField YOB; // Soulmate_YOB_TextField
	private ComboBox<String> algoSelection; // Soulmate_nkAlgo_ComboBox
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/interfaces/Main_interface.fxml"));
    	AnchorPane root = (AnchorPane) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.show();
   		mainscene = scene;
   		clickOn("#Main_soulmate_Button");
   		
   		name = (TextField)mainscene.lookup("#Soulmate_inputName_TextField");
   		YOB = (TextField)mainscene.lookup("#Soulmate_YOB_TextField");
   		algoSelection = (ComboBox<String>)mainscene.lookup("#Soulmate_nkAlgo_ComboBox");
		
   		nameError = (Label)mainscene.lookup("#Soulmate_errorName_Label");
   		YOBError = (Label)mainscene.lookup("#Soulmate_errorYear_Label");
   		
   		infoPane = (Pane)mainscene.lookup("#Soulmate_infoPane_Pane");
	}
	
	private void choose(int choice) {
		clickOn("#Soulmate_nkAlgo_ComboBox");
		for(int i = 0; i < choice; ++i) type(KeyCode.DOWN);
		type(KeyCode.ENTER);
	}
	
	@Test
	public void normalTest1() {
		name.setText("Ryder");
		YOB.setText("2000");
		clickOn("#Soulmate_preferenceIsFemale_Button");
		choose(2);
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		String sample = "Aiyana";
		TableView table = (TableView)mainscene.lookup("#Soulmate_NKout_TableView");
		TableColumn col = (TableColumn) table.getColumns().get(0);
		String data = (String) col.getCellObservableValue(table.getItems().get(0)).getValue();
		assertTrue(data.equals(sample));
	}
	
	@Test
	public void normalTest2() {
		name.setText("Abhishek");
		YOB.setText("2001");
		clickOn("#Soulmate_preferenceIsFemale_Button");
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		String sample = "Cienna";
		TableView table = (TableView)mainscene.lookup("#Soulmate_NKout_TableView");
		TableColumn col = (TableColumn) table.getColumns().get(0);
		String data = (String) col.getCellObservableValue(table.getItems().get(0)).getValue();
		assertTrue(data.equals(sample));
	}
	
	@Test
	public void edgeTest1() {
		name.setText("Bill");
		YOB.setText("1880");
		clickOn("#Soulmate_preferenceIsFemale_Button");
		clickOn("#Soulmate_isOlder_Button");
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		String sample = "Lina";
		TableView table = (TableView)mainscene.lookup("#Soulmate_NKout_TableView");
		TableColumn col = (TableColumn) table.getColumns().get(0);
		String data = (String) col.getCellObservableValue(table.getItems().get(0)).getValue();
		assertTrue(data.equals(sample));
	}
	
	@Test
	public void edgeTest2() {
		name.setText("Mary");
		YOB.setText("2019");
		clickOn("#Soulmate_inputisFemale_RadioButton");
		clickOn("#Soulmate_preferenceIsMale_Button");
		clickOn("#Soulmate_isYounger_Button");
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		String sample = "Braxton";
		TableView table = (TableView)mainscene.lookup("#Soulmate_NKout_TableView");
		TableColumn col = (TableColumn) table.getColumns().get(0);
		String data = (String) col.getCellObservableValue(table.getItems().get(0)).getValue();
		assertTrue(data.equals(sample));
	}
	
	@Test
	public void invalidTest1() {
		
		YOB.setText("2019");
		clickOn("#Soulmate_inputisFemale_RadioButton");
		clickOn("#Soulmate_preferenceIsMale_Button");
		clickOn("#Soulmate_isYounger_Button");
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		assertTrue(nameError.isVisible());
	}
	
	@Test
	public void invalidTest2() {
		name.setText("Mary");
		
		clickOn("#Soulmate_inputisFemale_RadioButton");
		clickOn("#Soulmate_preferenceIsMale_Button");
		clickOn("#Soulmate_isYounger_Button");
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		assertTrue(YOBError.isVisible());
		
		YOB.setText("2000");
		clickOn("#Soulmate_findSoulmate_Button");
		assertTrue(!YOBError.isVisible());
		
		YOB.setText("-2000");
		clickOn("#Soulmate_findSoulmate_Button");
		assertTrue(YOBError.isVisible());
		
		YOB.setText("2000");
		clickOn("#Soulmate_findSoulmate_Button");
		assertTrue(!YOBError.isVisible());
		
		YOB.setText("4000");
		clickOn("#Soulmate_findSoulmate_Button");
		assertTrue(YOBError.isVisible());
	}
	
	@Test
	public void infoPanelTest() {
		Label title = (Label)mainscene.lookup("#Soulmate_infoPaneTitle_Label");
		clickOn("#Soulmate_nk_HBox");
		assertTrue(infoPane.isVisible() && title.getText().equals("NK-T5"));
		clickOn("#Soulmate_infoPaneExit_Button");
		
		clickOn("#Soulmate_ld_HBox");
		assertTrue(infoPane.isVisible() && title.getText().equals("Closest Name"));
		clickOn("#Soulmate_infoPaneExit_Button");
		
		clickOn("#Soulmate_pyc_HBox");
		assertTrue(infoPane.isVisible() && title.getText().equals("Probably Your Classmate"));
		clickOn("#Soulmate_infoPaneExit_Button");
		
		clickOn("#Soulmate_chance_HBox");
		assertTrue(infoPane.isVisible() && title.getText().equals("Chance Enounter"));
		clickOn("#Soulmate_infoPaneExit_Button");
		
		assertTrue(!infoPane.isVisible());
	}
	
	@Test
	public void JTTTest1() {
		name.setText("Bob");
		YOB.setText("1996");
		
		clickOn("#Soulmate_preferenceIsFemale_Button");
		clickOn("#Soulmate_isYounger_Button");
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		clickOn("#Soulmate_JTT_Button");
		Label desc = (Label)mainscene.lookup("#Soulmate_JTTMessage_Label");
		assertTrue(desc.getText().equals("Please select one of the names from below."));
		
		clickOn("#Soulmate_JTTNameSelection_TableView");
		type(KeyCode.DOWN);
		
		Button JTTButton = (Button)mainscene.lookup("#Soulmate_JTT_Button");
		assertTrue(!JTTButton.isDisabled());
		
		clickOn("#Soulmate_JTT_Button");
		
	}
	
	@Test
	public void JTTTest2() {
		name.setText("Alice");
		YOB.setText("1926");
		
		clickOn("#Soulmate_inputisFemale_RadioButton");
		clickOn("#Soulmate_preferenceIsMale_Button");
		clickOn("#Soulmate_isOlder_Button");
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		clickOn("#Soulmate_JTTNameSelection_TableView");
		type(KeyCode.DOWN);
		
		Button JTTButton = (Button)mainscene.lookup("#Soulmate_JTT_Button");
		assertTrue(!JTTButton.isDisabled());
		
		clickOn("#Soulmate_JTT_Button");
	}
	
	@Test
	public void JTTTest3() {
		name.setText("qwertyuiop");
		YOB.setText("1901");
		
		clickOn("#Soulmate_inputisFemale_RadioButton");
		clickOn("#Soulmate_preferenceIsMale_Button");
		clickOn("#Soulmate_isOlder_Button");
		
		clickOn("#Soulmate_findSoulmate_Button");
		
		clickOn("#Soulmate_JTTNameSelection_TableView");
		type(KeyCode.DOWN);
		
		Button JTTButton = (Button)mainscene.lookup("#Soulmate_JTT_Button");
		assertTrue(!JTTButton.isDisabled());
		
		clickOn("#Soulmate_JTT_Button");
	}
}
