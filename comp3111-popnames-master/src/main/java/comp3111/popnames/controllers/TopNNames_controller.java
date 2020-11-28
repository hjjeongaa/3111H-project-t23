package comp3111.popnames.controllers;

import comp3111.popnames.TopNNames;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TopNNames_controller {
	/* Stuff for Table */
	private ObservableList<TopNNamesDataModel> tableViewList;
	
	//Data model for each row of the TableView; each row has two columns, rank and name.
	public class TopNNamesDataModel {
		private final SimpleStringProperty rank;
		private final SimpleStringProperty name;
		private final SimpleStringProperty frequency;
		
		public TopNNamesDataModel(String Rank, String Name, String Frequency) {
			this.rank = new SimpleStringProperty(Rank);
			this.name = new SimpleStringProperty(Name);
			this.frequency = new SimpleStringProperty(Frequency);
		}
		//Each table column will use these methods to retrieve the values of each cell.
		public String getRank() {return this.rank.get();}
		public String getName() {return this.name.get();}
		public String getFrequency() {return this.frequency.get();}
	}
	
	
	/* Elements */
    @FXML
    private TextField TopNNames_startYear_TextField;

    @FXML
    private TextField TopNNames_endYear_TextField;

    @FXML
    private RadioButton TopNNames_isMale_RadioButton;

    @FXML
    private RadioButton TopNNames_isFemale_RadioButton;

    @FXML
    private Label TopNNames_invalidEnd_Label;

    @FXML
    private Label TopNNames_endRangeError_Label;

    @FXML
    private Label TopNNames_invalidStart_Label;

    @FXML
    private Label TopNNames_startRangeError_Label;

    @FXML
    private TextField TopNNames_N_TextField;

    @FXML
    private Label TopNNames_NError1_Label;

    @FXML
    private Label TopNNames_NError2_Label;

    @FXML
    private Button TopNNames_generate_Button;

    @FXML
    private TableView<TopNNamesDataModel> TopNNames_outputTable_TableView;

    @FXML
    private TableColumn<TopNNamesDataModel, String> TopNNames_rankColumn_TableColumn;

    @FXML
    private TableColumn<TopNNamesDataModel, String> TopNNames_nameColumn_TableColumn;

    @FXML
    private TableColumn<TopNNamesDataModel, String> TopNNames_frequencyColumn_TableColumn;

    /* Table Initialization */
    @FXML
    void initialize() {
    	//Link each table column with the correct variable inside the data model for the tableview.
    	TopNNames_rankColumn_TableColumn.setCellValueFactory(new PropertyValueFactory<TopNNamesDataModel,String>("rank"));
    	TopNNames_nameColumn_TableColumn.setCellValueFactory(new PropertyValueFactory<TopNNamesDataModel,String>("name"));
    	TopNNames_frequencyColumn_TableColumn.setCellValueFactory(new PropertyValueFactory<TopNNamesDataModel,String>("frequency"));
    	
    	//Create an array of data models and link this array with the tableview.
    	this.tableViewList = FXCollections.<TopNNamesDataModel>observableArrayList();
    	TopNNames_outputTable_TableView.setItems(this.tableViewList);
    }
    
    /* Methods */
    
    private void clearScreen() {
    	TopNNames_invalidStart_Label.setVisible(false);
    	TopNNames_invalidEnd_Label.setVisible(false);
    	
    	TopNNames_startRangeError_Label.setVisible(false);
    	TopNNames_endRangeError_Label.setVisible(false);
    	
    	TopNNames_NError1_Label.setVisible(false);
    	TopNNames_NError2_Label.setVisible(false);
    }
    
    private int getCleanedStartYear( int lowerBound, int upperBound) {
    	int res = -1;
    	try {
    		res = Integer.parseInt(TopNNames_startYear_TextField.getText());
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		TopNNames_invalidStart_Label.setVisible(true);
    	}
    	if(res < lowerBound || res > upperBound ) {
    		TopNNames_startRangeError_Label.setVisible(true);
    		res = -1;
    	} 
    	return res;
    }
    
    private int getCleanedEndYear( int lowerBound, int upperBound) {
    	int res = -1;
    	try {
    		res = Integer.parseInt(TopNNames_endYear_TextField.getText());
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		TopNNames_invalidEnd_Label.setVisible(true);
    	}
    	if( res < lowerBound ) {
    		TopNNames_endRangeError_Label.setVisible(true);
    		res = -1;
    	} else if(res > upperBound) {
    		TopNNames_endRangeError_Label.setText("End Year Out of Range");
    		TopNNames_endRangeError_Label.setVisible(true);
    		res = -1;
    	}
    	return res;
    }
    
    private int getCleanedNumberOfNames() {
    	int res = -1;
    	try {
    		res = Integer.parseInt(TopNNames_N_TextField.getText());
    		if(res < 1) {
    			TopNNames_NError1_Label.setVisible(true);
    			TopNNames_NError2_Label.setVisible(true);
    			res = -1;
    		}
    	} catch(NumberFormatException e) {
    		// N is not an integer
    		TopNNames_NError1_Label.setVisible(true);
    	}
    	return res;
    }
    
    @FXML
    void generateTopNNames() {
    	clearScreen();
    	
    	// Input sanitation.
    	int numberOfNames = getCleanedNumberOfNames();
    	
    	int startYear = getCleanedStartYear(1880, 2019); // these values are hard coded for now.
    	if(startYear == -1) return;
    	int endYear = getCleanedEndYear(startYear, 2019);
    	if(endYear == -1) return;
    	if(numberOfNames == -1) return;
    	
    	String gender = (TopNNames_isMale_RadioButton.isSelected())?"M":"F";
    	
    	// Do work on the inputs now that everything is good.
    	tableViewList.clear();
    	TopNNames source = new TopNNames(startYear, endYear, gender, numberOfNames ,"usa", "human");
    	for(int rank = 0; rank < numberOfNames; ++rank) {
    		String s_rank = Integer.toString(rank + 1);
    		String name = source.getNameFromIndex(rank);
    		String s_frequency = Integer.toString(source.getFrequencyFromIndex(rank));
    		
    		TopNNamesDataModel Entry = new TopNNamesDataModel(s_rank, name, s_frequency);
    		tableViewList.add(Entry);
    	}
    }

}
