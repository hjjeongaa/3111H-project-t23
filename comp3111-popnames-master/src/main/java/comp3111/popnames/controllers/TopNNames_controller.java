/**
* TopNNames_controller.java
* This is the controller for the interface for the TopNNames task.
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3111.popnames.GlobalSettings;
import comp3111.popnames.TopNNames;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class TopNNames_controller {
	/* Shared Stuff */
	
	/* Stuff for Table */
	private ObservableList<TopNNamesDataModel> tableViewList;
	
	//Data model for each row of the TableView; each row has two columns, rank and name.
	public class TopNNamesDataModel {
		private final SimpleStringProperty rank;
		private final SimpleStringProperty name;
		private final SimpleStringProperty freq;
		
		public TopNNamesDataModel(String Rank, String Name, String Freq) {
			this.rank = new SimpleStringProperty(Rank);
			this.name = new SimpleStringProperty(Name);
			this.freq = new SimpleStringProperty(Freq);
		}
		//Each table column will use these methods to retrieve the values of each cell.
		public String getRank() {return this.rank.get();}
		public String getName() {return this.name.get();}
		public String getFreq() {return this.freq.get();}
	}
	
	
	/* Elements */
	@FXML
    private TextField TopNNames_startYear_TextField;

    @FXML
    private TextField TopNNames_endYear_TextField;

    @FXML
    private RadioButton TopNNames_isMale_RadioButton;

    @FXML
    private ToggleGroup TopNNames_getGender;

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
    private Label TopNNames_description_Label;

    @FXML
    private TableView<TopNNamesDataModel> TopNNames_outputTable_TableView;

    @FXML
    private TableColumn<TopNNamesDataModel, String> TopNNames_rank_TableColumn;

    @FXML
    private TableColumn<TopNNamesDataModel, String> TopNNames_name_TableColumn;
    
    @FXML
    private TableColumn<TopNNamesDataModel, String> TopNNames_freq_TableColumn;

    @FXML
    private Slider TopNNames_yearselector_Slider;
    
    @FXML
    private Label TopNNames_yearlabel_Label;

    /* Table Initialization */
    @FXML
    /**
     * Initialize the table.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void initialize() {
    	// Link each table column with the correct variable inside the data model for the tableview.
    	TopNNames_rank_TableColumn.setCellValueFactory(new PropertyValueFactory<TopNNamesDataModel,String>("rank"));
    	TopNNames_name_TableColumn.setCellValueFactory(new PropertyValueFactory<TopNNamesDataModel,String>("name"));
    	TopNNames_freq_TableColumn.setCellValueFactory(new PropertyValueFactory<TopNNamesDataModel,String>("freq"));
    	
    	// Create an array of data models and link this array with the tableview.
    	this.tableViewList = FXCollections.<TopNNamesDataModel>observableArrayList();
    	TopNNames_outputTable_TableView.setItems(this.tableViewList);
    }
    
    /* Methods */
    /**
     * Used to clear the screen of errors and disable the slider.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    private void clearScreen() {
    	TopNNames_invalidStart_Label.setVisible(false);
    	TopNNames_invalidEnd_Label.setVisible(false);
    	
    	TopNNames_startRangeError_Label.setVisible(false);
    	TopNNames_endRangeError_Label.setVisible(false);
    	
    	TopNNames_NError1_Label.setVisible(false);
    	TopNNames_NError2_Label.setVisible(false);
    	
    	TopNNames_description_Label.setText("");
    	
    	TopNNames_yearselector_Slider.setDisable(true);
    }
    
    /**
     * returns the start year if its valid, shows error messages if not and returns -1.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    private int getCleanedStartYear( int lowerBound, int upperBound) {
    	int res = -1;
    	try {
    		res = Integer.parseInt(TopNNames_startYear_TextField.getText());
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		TopNNames_invalidStart_Label.setVisible(true);
    		return res;
    	}
    	if(res < lowerBound || res > upperBound ) {
    		TopNNames_startRangeError_Label.setText(String.format("Year Range %d-%d",lowerBound, upperBound));
    		TopNNames_startRangeError_Label.setVisible(true);
    		res = -1;
    	} 
    	return res;
    }
    
    /**
     * Returns the end year if its valid or shows error messages if the year is not valid and returns -1.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    private int getCleanedEndYear( int lowerBound, int upperBound) {
    	int res = -1;
    	try {
    		res = Integer.parseInt(TopNNames_endYear_TextField.getText());
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		TopNNames_invalidEnd_Label.setVisible(true);
    		return res;
    	}
    	if( res < lowerBound || res > upperBound ) {
    		TopNNames_endRangeError_Label.setText(String.format("Year Range %d-%d", lowerBound, upperBound));
    		TopNNames_endRangeError_Label.setVisible(true);
    		res = -1;
    	}
    	return res;
    }
    
    /**
     * returns the number of names specified, or -1 if the number is invalid. If it is invalid error messages will also be shown.
     * @author Ryder Khoi Daniel
     * v1.0
     */
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
    
    /**
     * Used to calculate the names that appear most often in the top two position.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    private List<String> modeName(TopNNames source) {
    	List<String> output = new ArrayList<String>();
    	List<String> topNames1 = new ArrayList<String>();
    	List<String> topNames2 = new ArrayList<String>();
    	for(int year = source.getStartYear(); year <= source.getEndYear(); ++year) {
    		topNames1.add(source.getListOfNamesFromYear(year).get(0));
    		topNames2.add(source.getListOfNamesFromYear(year).get(1));
    	}
    	
    	String mode1 = "-1"; int msf1 = 1;
    	String mode2 = "-1"; int msf2 = 1;
    	HashMap<String,Integer> modeFind1 = new HashMap<String, Integer>();
    	HashMap<String,Integer> modeFind2 = new HashMap<String, Integer>();
    	
    	for(String name : topNames1) {
    		if(modeFind1.get(name) == null) modeFind1.put(name, 1);
    		else modeFind1.put(name, modeFind1.get(name)+1);
    	}
    	for(String name : topNames2) {
    		if(modeFind2.get(name) == null) modeFind2.put(name, 1);
    		else modeFind2.put(name, modeFind2.get(name)+1);
    	}
    	
    	for (Map.Entry me : modeFind1.entrySet()) {
            msf1 = Math.max(msf1, (int) me.getValue());
            if(msf1 == (int) me.getValue()) mode1 = (String) me.getKey();
        }
    	for (Map.Entry me : modeFind2.entrySet()) {
            msf2 = Math.max(msf2, (int) me.getValue());
            if(msf2 == (int) me.getValue()) mode2 = (String) me.getKey();
        }
    	
    	output.add(mode1);
    	output.add(mode2);
    	
    	return output;
    }
    
    /**
     * Used to find the frequencies of those names appearing in top positions.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    private List<Integer> modeFreq(TopNNames source) {
    	List<Integer> output = new ArrayList<Integer>();
    	List<String> topNames1 = new ArrayList<String>();
    	List<String> topNames2 = new ArrayList<String>();
    	for(int year = source.getStartYear(); year <= source.getEndYear(); ++year) {
    		topNames1.add(source.getListOfNamesFromYear(year).get(0));
    		topNames2.add(source.getListOfNamesFromYear(year).get(1));
    	}
    	
    	Integer mode1 = -1; int msf1 = 1;
    	Integer mode2 = -1; int msf2 = 1;
    	HashMap<String,Integer> modeFind1 = new HashMap<String, Integer>();
    	HashMap<String,Integer> modeFind2 = new HashMap<String, Integer>();
    	
    	for(String name : topNames1) {
    		if(modeFind1.get(name) == null) modeFind1.put(name, 1);
    		else modeFind1.put(name, modeFind1.get(name)+1);
    	}
    	for(String name : topNames2) {
    		if(modeFind2.get(name) == null) modeFind2.put(name, 1);
    		else modeFind2.put(name, modeFind2.get(name)+1);
    	}
    	
    	for (Map.Entry me : modeFind1.entrySet()) {
            msf1 = Math.max(msf1, (int) me.getValue());
            if(msf1 == (int) me.getValue()) mode1 = (Integer) me.getValue();
        }
    	for (Map.Entry me : modeFind2.entrySet()) {
            msf2 = Math.max(msf2, (int) me.getValue());
            if(msf2 == (int) me.getValue()) mode2 = (Integer) me.getValue();
        }
    	
    	output.add(mode1);
    	output.add(mode2);
    	
    	return output;
    }
    
    private TopNNames src;
    private int nums;
    
    @FXML
    /**
     * This is run when the button is pressed. Based on the user inputs, the data structure is set up, and all other elements may now access it rapidly. If any of the inputs are bad, then an error message will show up and nothing will happen.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void generateTopNNames() {
    	clearScreen();
    	// Input sanitation.
    	int numberOfNames = getCleanedNumberOfNames();
    	
    	int startYear = getCleanedStartYear(GlobalSettings.getLowerBound(), GlobalSettings.getUpperBound()); // these values are hard coded for now.
    	if(startYear == -1) return;
    	int endYear = getCleanedEndYear(startYear, 2019);
    	if(endYear == -1) return;
    	if(numberOfNames == -1) return;
    	
    	String gender = (TopNNames_isMale_RadioButton.isSelected())?"M":"F";
    	
    	this.src = new TopNNames(startYear, endYear, gender, numberOfNames ,GlobalSettings.getCountry(), "human");
    	this.nums = numberOfNames;
    	
    	// Enable the slider now that everything is good
    	
    	if(startYear != endYear) TopNNames_yearselector_Slider.setDisable(false);
    	
    	TopNNames_yearselector_Slider.setMin(startYear);
    	TopNNames_yearselector_Slider.setMax(endYear);
    	TopNNames_yearselector_Slider.setValue(startYear);
    	
    	updateTable();
    	List<String> tops = modeName(this.src);
    	List<Integer> topF = modeFreq(this.src);
    	String analysis  = String.format("Between %d and %d, the most consistently popular name was %s, appearing %d times in first. And the second most consistently popular name was %s, appearing %d times in second.", startYear, endYear, tops.get(0), topF.get(0), tops.get(1), topF.get(1));
    	
    	TopNNames_description_Label.setText(analysis);
    }
    
    @FXML
    /**
     * Called whenever there is a drag or mouse event detected. Allows for a dynamic view of the names.
     * This function gets information from the slider and draws the desired output to the table.
     * @author Ryder Khoi Daniel
     * v1.0
     */
    void updateTable() {
    	int yearOfInterest = (int) TopNNames_yearselector_Slider.getValue();
    	TopNNames_yearlabel_Label.setText(String.format("Year: %d", yearOfInterest));
    	tableViewList.clear();
    	
    	List<String> names = this.src.getListOfNamesFromYear(yearOfInterest);
    	List<Integer> freqs = this.src.getListOfFrequenciesFromYear(yearOfInterest);
    	
    	if(names == null || freqs == null) return;
    	for(int rank = 0; rank < this.nums; ++rank) {
    		String s_rank = Integer.toString(rank + 1);
    		String name = names.get(rank);
    		String s_frequency = Integer.toString(freqs.get(rank));
    		
    		TopNNamesDataModel Entry = new TopNNamesDataModel(s_rank, name, s_frequency);
    		tableViewList.add(Entry);
    	}
    	
    }

}
