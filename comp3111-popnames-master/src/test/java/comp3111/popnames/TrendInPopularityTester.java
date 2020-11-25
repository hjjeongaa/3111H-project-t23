package comp3111.popnames;

import static org.junit.Assert.*;
import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class TrendInPopularityTester extends ApplicationTest {
	//test cases
	
	//same year Error
	//2000 2000
	//start year larger then end year error
	//2001 2000
	
	//left out
	//1879 2000
	
	//right out
	//2000 2020 
	//both out
	//Valid
	//boundary
	//1880 2019 usa Male
		//('Atreus', {'dates': [((2008, 12535), (2019, 788))], 'hval': 11747})
		//('Alva', {'dates': [((1882, 161), (2008, 12535))], 'hval': -12374})
	
	//18802019 usa Female
		//('Coraline', {'dates': [((2007, 17619), (2015, 579))], 'hval': 17040})
		//('Maude', {'dates': [((1882, 20), (2007, 17619))], 'hval': 17599})
	//2018 2019 usa female
//	Jenaiah {'dates': [((2018, 13750), (2019, 4233))], 'hval': 9517}
//	Anifer {'dates': [((2018, 3910), (2019, 15442))], 'hval': 11532}
	
//
//	private Scene s;
//	private TextArea t;
//	private TextField f;
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
	
//	@Test
//	public void testT3InterfaceGenerateValidBounds() {	
//		clickOn("#tabReport3");
//		f = (TextField)s.lookup("#T3_startYear_TextField");
//		f.setText("1900");
//		f = (TextField)s.lookup("#T3_endYear_TextField");
//		f.setText("2000");;
//		clickOn("#GenerateT3");
//		String s1 = t.getText();
//		assertFalse(s1.contains("Start Year should be within the valid range of 1880 - 2019")||
//				s1.contains("End Year should be within the valid range of 1880 - 2019")||
//				s1.contains("Start Year should be Smaller to End Year")
//				);
//	}
//	@Test
//	public void testT3InterfaceGenerateStartEndInvalidBounds1() {
//		clickOn("#tabReport3");
//		f = (TextField)s.lookup("#T3_startYear_TextField");
//		f.setText("2000");
//		f = (TextField)s.lookup("#T3_endYear_TextField");
//		f.setText("2000");
//		clickOn("#GenerateT3");
//		String s1 = t.getText();
////		sleep(5000);
//		assertTrue(s1.contains("Start Year should be Smaller to End Year"));
//	}
//	
//	@Test
//	public void testT3InterfaceGenerateStartEndInvalidBounds2() {
//		clickOn("#tabReport3");
//		f = (TextField)s.lookup("#T3_startYear_TextField");
//		f.setText("2001");
//		f = (TextField)s.lookup("#T3_endYear_TextField");
//		f.setText("2000");
//		clickOn("#GenerateT3");
//		String s1 = t.getText();
////		sleep(5000);
//		assertTrue(s1.contains("Start Year should be Smaller to End Year"));
//	}
//	
//	@Test
//	public void testT3InterfaceGenerateStartOutOfBounds() {
//		clickOn("#tabReport3");
//		f = (TextField)s.lookup("#T3_startYear_TextField");
//		f.setText("0");
//		f = (TextField)s.lookup("#T3_endYear_TextField");
//		f.setText("2000");
//		clickOn("#GenerateT3");
//		String s1 = t.getText();
////		sleep(5000);
//		assertTrue(s1.contains("Start Year should be within the valid range of 1880 - 2019"));
//	}
//	@Test
//	public void testT3InterfaceGenerateEndOutOfBounds() {
//		clickOn("#tabReport3");
//		f = (TextField)s.lookup("#T3_startYear_TextField");
//		f.setText("2000");
//		f = (TextField)s.lookup("#T3_endYear_TextField");
//		f.setText("10000");
//		clickOn("#GenerateT3");
//		String s1 = t.getText();
////		sleep(5000);
//		assertTrue(s1.contains("End Year should be within the valid range of 1880 - 2019"));
//	}
//	@Test
//	public void testT3InterfaceGenerateBothOutOfBounds() {	
//		clickOn("#tabReport3");
//		f = (TextField)s.lookup("#T3_startYear_TextField");
//		f.setText("0");
//		f = (TextField)s.lookup("#T3_endYear_TextField");
//		f.setText("10000");
//		clickOn("#GenerateT3");
//		String s1 = t.getText();
////		sleep(5000);
//		assertTrue(s1.contains("Start Year should be within the valid range of 1880 - 2019") && s1.contains("End Year should be within the valid range of 1880 - 2019"));
//
//
//		//regression testing
//		//F 1941 - 1945: Alexis - 3758
//	}
}

