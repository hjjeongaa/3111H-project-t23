package comp3111.popnames.controllers;

import comp3111.popnames.GlobalSettings;
import java.time.format.DateTimeFormatter;

import comp3111.popnames.ReportLog;

import comp3111.popnames.AnalyzeNames;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataReporting_controller {

	private ObservableList<TaskZeroDataModel> tableViewList;
	
	//Data model for each row of the TableView; each row has two columns, rank and name.
	public class TaskZeroDataModel {
		private final SimpleStringProperty rank;
		private final SimpleStringProperty name;
		
		public TaskZeroDataModel(String Rank, String Name) {
			this.rank = new SimpleStringProperty(Rank);
			this.name = new SimpleStringProperty(Name);
		}
		//Each table column will use these methods to retrieve the values of each cell.
		public String getRank() {return this.rank.get();}
		public String getName() {return this.name.get();}
	}
	
	@FXML
    private TextField DataReporting_nameField_TextField;

    @FXML
    private RadioButton DataReporting_isFemale_RadioButton;

    @FXML
    private RadioButton DataReporting_isMale_RadioButton;

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
    private TableView<TaskZeroDataModel> DataReporting_top5Table_TableView;

    @FXML
    private TableColumn<TaskZeroDataModel, String> DataReporting_rankColumn_TableColumn;

    @FXML

    private TableColumn<TaskZeroDataModel, String> DataReporting_nameColumn_TableColumn;
    
    /* Methods */
    
    private void clearScreen() {
    	// anything like outputs and errors are cleared.
    	DataReporting_rankAndSummaryOutput_Label.setVisible(false);
    	DataReporting_top5Table_TableView.setVisible(false);
    	
    	DataReporting_errorYear_Label.setVisible(false);
    	DataReporting_errorName_Label.setVisible(false);
    }
    
    private String getCleanedName() {
    	String o = DataReporting_nameField_TextField.getText();
    	o = ReportLog.validateName(o);
    	if(o.isEmpty()) {
    		DataReporting_errorName_Label.setVisible(true);
    	}
    	return o;
    }
    
    private int getCleanedYear() {
    	int y = -1;
    	try {
    		y = Integer.parseInt(DataReporting_yearField_TextField.getText());
    		if (!(y >= GlobalSettings.getLowerBound() && y <= GlobalSettings.getUpperBound())) {y=-1; DataReporting_errorYear_Label.setVisible(true);}
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		DataReporting_errorYear_Label.setVisible(true);
    	}
    	return y;
    }
    
    @FXML
    void initialize() {
    	//Link each table column with the correct variable inside the data model for the tableview.
    	DataReporting_rankColumn_TableColumn.setCellValueFactory(new PropertyValueFactory<TaskZeroDataModel,String>("rank"));
    	DataReporting_nameColumn_TableColumn.setCellValueFactory(new PropertyValueFactory<TaskZeroDataModel,String>("name"));

    	//Create an array of data models and link this array with the tableview.
    	this.tableViewList = FXCollections.<TaskZeroDataModel>observableArrayList();
    	DataReporting_top5Table_TableView.setItems(this.tableViewList);
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
    	String gender = (DataReporting_isFemale_RadioButton.isSelected())?"F":"M";
    	if(year != -1) {
    		
    		//Get rid of any previous rows that might be sitting in the table.
    		tableViewList.clear();
    		//Create data models and add it to the table's array.
    		TaskZeroDataModel Entry1 = new TaskZeroDataModel("1", AnalyzeNames.getName(year, 1, gender));
    		TaskZeroDataModel Entry2 = new TaskZeroDataModel("2", AnalyzeNames.getName(year, 2, gender));
    		TaskZeroDataModel Entry3 = new TaskZeroDataModel("3", AnalyzeNames.getName(year, 3, gender));
    		TaskZeroDataModel Entry4 = new TaskZeroDataModel("4", AnalyzeNames.getName(year, 4, gender));
    		TaskZeroDataModel Entry5 = new TaskZeroDataModel("5", AnalyzeNames.getName(year, 5, gender));
    		
    		tableViewList.add(Entry1);
    		tableViewList.add(Entry2);
    		tableViewList.add(Entry3);
    		tableViewList.add(Entry4);
    		tableViewList.add(Entry5);
    		
    		DataReporting_top5Table_TableView.setVisible(true);
   	 	}
   	 	return;
    }

}