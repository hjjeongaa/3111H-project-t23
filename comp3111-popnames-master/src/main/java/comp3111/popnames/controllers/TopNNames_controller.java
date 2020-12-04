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
    	String analysis = "";
    	List<String> tops = modeName(this.src);
    	List<Integer> topF = modeFreq(this.src);
    	if(tops.get(0).equals("-1") && tops.get(1).equals("-1")) {
    		// Both changed every year
    		analysis = String.format("WOW!, what a dynamic range of years! Between %d and %d no single name managed to stay on top for at least two years, not even the second most popular name managed to do that either!", startYear, endYear);
    	} else if(tops.get(0).equals("-1")) {
    		// First place changed every year but not second place
    		analysis = String.format("Between %d and %d, it appears that the most popular name never could manage to stay consistent, but second place did with the most consistently second place name being %s appearing %d time in second place.", startYear, endYear, tops.get(1), topF.get(1));
    	} else if(tops.get(1).equals("-1")) {
    		// Second place changed every year but not first place
    		analysis = String.format("Between %d and %d, the most cosistently popular name was %s apprearing a total of %d time in first place. It appears that the second most consistently popular kept on changing in the range of years, neat!", startYear, endYear, tops.get(0), topF.get(0));
    	} else {
    		// Both have a top name
    		analysis = String.format("Between %d and %d, the most consistently popular name was %s, appearing %d times in first. And the second most consistently popular name was %s, appearing %d times in second.", startYear, endYear, tops.get(0), topF.get(0), tops.get(1), topF.get(1));
    	}
    	
    	TopNNames_description_Label.setText(analysis);
    }
    
    @FXML
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
