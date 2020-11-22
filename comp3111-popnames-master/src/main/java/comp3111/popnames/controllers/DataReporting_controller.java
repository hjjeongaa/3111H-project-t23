package comp3111.popnames.controllers;

import comp3111.popnames.AnalyzeNames;
import comp3111.popnames.PopularityOfName;
import comp3111.popnames.TopNNames;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DataReporting_controller {

	@FXML
    private TextField DataReporting_nameField_TextField;

    @FXML
    private RadioButton DataReporting_isFemale_RadioButton;

    @FXML
    private ToggleGroup DataReport_genderSelect_ToggleGroup;

    @FXML
    private TextField DataReporting_yearField_TextField;

    @FXML
    private Label DataReporting_errorName_Label;

    @FXML
    private Label DataReporting_errorYear_Label;

    @FXML
    private Button DataReporting_getRank_Button;

    @FXML
    private Button DataReporting_getTopFive_Button;

    @FXML
    private Button DataReporting_getSummary_Button;

    @FXML
    private Label DataReporting_rankAndSummaryOutput_Label;

    @FXML
    private TableView<?> DataReporting_top5Table_TableView;

    @FXML
    private TableColumn<?, ?> DataReporting_rankColumn_TableColumn;

    @FXML
    private TableColumn<?, ?> DaraReporting_nameColumn_TableColumn;
    
    /* Methods */
    
    private void clearScreen() {
    	// anything like outputs and errors are cleared.
    	DataReporting_rankAndSummaryOutput_Label.setVisible(false);
    	DataReporting_top5Table_TableView.setVisible(false);
    	DataReporting_rankColumn_TableColumn.setVisible(false);
    	DaraReporting_nameColumn_TableColumn.setVisible(false);
    	
    	DataReporting_errorYear_Label.setVisible(false);
    	DataReporting_errorName_Label.setVisible(false);
    }
    
    private String getCleanedName() {
    	String o = DataReporting_nameField_TextField.getText();
    	if(o.length() == 0) {
    		DataReporting_errorName_Label.setVisible(true);
    	}
    	return o;
    }
    
    private int getCleanedYear() {
    	int y = -1;
    	try {
    		y = Integer.parseInt(DataReporting_yearField_TextField.getText());
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		DataReporting_errorYear_Label.setVisible(true);
    	}
    	return y;
    }
    
    @FXML
    void getRank() {
    	clearScreen();
    	
    	String name = getCleanedName();
    	int year = getCleanedYear();
    	String gender;
    	boolean anyErrors = false;
    	
    	if( name.length() == 0 || year == -1) anyErrors = true;
    	
    	if(!anyErrors) {
    		// All inputs are valid, now to display the desired output.
    		gender = (DataReporting_isFemale_RadioButton.isSelected())?"F":"M";
    		int rank = AnalyzeNames.getRank(year, name, gender);
    		if(rank == -1) {
    			DataReporting_rankAndSummaryOutput_Label.setText(String.format("Sorry %s could not be found in %d", name, year));
    			DataReporting_rankAndSummaryOutput_Label.setVisible(true);
    		} else {
    			DataReporting_rankAndSummaryOutput_Label.setText(String.format("The Rank of %s in %d is %d.", name, year, rank));
    			DataReporting_rankAndSummaryOutput_Label.setVisible(true);
    		}
    	}
		return;
    }
    
    @FXML
    void getSummary() {
    	clearScreen();
    	 int year = getCleanedYear();
    	 if(year != -1) {
    		 DataReporting_rankAndSummaryOutput_Label.setText(AnalyzeNames.getSummary(year));
    		 DataReporting_rankAndSummaryOutput_Label.setVisible(true);
    	 }
    	 return;
    }

    @FXML
    void getTopFiveNames() {
    	clearScreen();
    	int year = getCleanedYear();
    	if(year != -1) {
    		
   	 	}
   	 	return;
    }

}
