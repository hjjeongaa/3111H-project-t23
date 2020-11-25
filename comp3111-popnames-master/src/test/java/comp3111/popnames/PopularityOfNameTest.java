//package comp3111.popnames;
//
//import static org.junit.Assert.*;
//import org.junit.Test;
//import org.testfx.assertions.api.Assertions;
//import org.testfx.framework.junit.ApplicationTest;
//
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.fxml.FXMLLoader;
//
//public class PopularityOfNameTest extends ApplicationTest {
//	private Scene s;
//	private TextArea t;
//	private TextField inputField;
//	
//	@Override
//	public void start(Stage stage) throws Exception {
//    	FXMLLoader loader = new FXMLLoader();
//    	loader.setLocation(getClass().getResource("/ui.fxml"));
//   		VBox root = (VBox) loader.load();
//   		Scene scene =  new Scene(root);
//   		stage.setScene(scene);
//   		stage.setTitle("Popular Names");
//   		stage.show();
//   		s = scene;
//		t = (TextArea)s.lookup("#textAreaConsole");
//	}
//	//test whether an out of bounds start year for task 3 returns proper error message
//	@Test
//	public void testTask3startYearOutOfBounds() {	
//		clickOn("#tabReport3");
//		inputField = (TextField)s.lookup("#T3_startYear_TextField");
//		inputField.setText("1870");
//		clickOn("#T3_generateReport_Button");
//		sleep(1000);
//		String output = t.getText();
//		assertTrue(output.contains("The start year is out of bounds"));
//	}
//	//test whether an out of bounds end year for task 3 returns proper error message
//	@Test
//	public void testTask3endYearOutOfBounds() {
//		clickOn("#tabReport3");
//		inputField = (TextField)s.lookup("#T3_startYear_TextField");
//		inputField.setText("2000");
//		inputField = (TextField)s.lookup("#T3_endYear_TextField");
//		inputField.setText("1990");
//		clickOn("#T3_generateReport_Button");
//		sleep(1000);
//		String output1 = t.getText();
//		inputField.setText("2030");
//		clickOn("#T3_generateReport_Button");
//		sleep(1000);
//		String output2 = t.getText();
//		assertTrue(output1.contains("The end year is out of bounds") && output2.contains("The end year is out of bounds"));
//	}
//	//test a precalculated set of inputs for task 2 and see if it returns the same output
//	@Test
//	public void testTask2sampleInput() {	
//		clickOn("#tabReport2");
//		inputField = (TextField)s.lookup("#T2_name_TextField");
//		inputField.setText("Samuel");
//		inputField = (TextField)s.lookup("#T2_startYear_textField");
//		inputField.setText("1990");
//		inputField = (TextField)s.lookup("#T2_endYear_TextField");
//		inputField.setText("2005");
//		clickOn("#T2_generateReport_Button");
//		sleep(1000);
//		String output = t.getText();
//		assertTrue(output.contains("Rank of Samuel between 1990 and 2005 is 30"));
//	
//	}
//}
