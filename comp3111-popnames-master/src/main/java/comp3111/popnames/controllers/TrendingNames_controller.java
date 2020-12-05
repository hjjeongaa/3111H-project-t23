package comp3111.popnames.controllers;
/**
 * T3 controller
 * Yuxi Sun
 * v2.0
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import comp3111.popnames.DatasetHandler;
import comp3111.popnames.TrendInPopularity;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.*;
import javafx.util.Pair;
/**
 * Controller for Trend In Popularity T3 Interface
 * @author Yuxi Sun
 *
 */
public class TrendingNames_controller {

    @FXML
    private RadioButton T3_male_RadioButton;

    @FXML
    private ToggleGroup T111;

    @FXML
    private RadioButton T3_female_RadioButton;

    @FXML
    private ComboBox<String> T3_type_ComboBox;
 
    @FXML
    private ComboBox<String> T3_country_ComboBox;

    @FXML
    private Button T3_generate_Button;

    @FXML
    private ComboBox<String> T3_startYear_ComboBox;

    @FXML
    private ComboBox<String> T3_endYear_ComboBox;
    
    
    //error texts
    @FXML
    private Text T3_country_error_Text;

    @FXML
    private Text T3_type_error_Text;

    @FXML
    private Text T3_range_error_Text;
    
    @FXML
    private Text T3_start_year_error_Text;

    @FXML
    private Text T3_end_year_error_Text;
    
    //infobox
    @FXML
    private TextArea T3_infobox_TextArea;
   
    
    //output tables constructs
    @FXML
    private TextArea T3_summary_TextArea;
    
    @FXML
    private TableView<T3_row_structure> T3_output_Table;

    @FXML
    private TableColumn<?, ?> T3_name_TableColumn;

    @FXML
    private TableColumn<?, ?> T3_start_rank_TableColumn;

    @FXML
    private TableColumn<?, ?> T3_start_year_TableColumn;

    @FXML
    private TableColumn<?, ?> T3_end_rank_TableColumn;

    @FXML
    private TableColumn<?, ?> T3_end_year_TableColumn;
    
    @FXML
    private TableColumn<?, ?> T3_trend_TableColumn;

    private ObservableList<T3_row_structure> t3_rows;
    
    //Combobox lists
    private ObservableList<String> types;
    private ObservableList<String> countries;
    private ObservableList<String> endOptions;
    private ObservableList<String> startOptions;
    
    @FXML
    /**
     * Initializes lists for combo boxes
     * @author Yuxi Sun
     */
    void initialize(){
        //Initialize and update both lists
        endOptions = FXCollections.observableArrayList();
        startOptions = FXCollections.observableArrayList();
        T3_startYear_ComboBox.setItems(startOptions);
        T3_endYear_ComboBox.setItems(endOptions);
    	//perform link with T3_row_structure and fxml table
        T3_name_TableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        T3_start_rank_TableColumn.setCellValueFactory(new PropertyValueFactory<>("startRank"));
        T3_start_year_TableColumn.setCellValueFactory(new PropertyValueFactory<>("startYear"));
        T3_end_rank_TableColumn.setCellValueFactory(new PropertyValueFactory<>("endRank"));
        T3_end_year_TableColumn.setCellValueFactory(new PropertyValueFactory<>("endYear"));
        T3_trend_TableColumn.setCellValueFactory(new PropertyValueFactory<>("trend"));
        this.t3_rows = FXCollections.<T3_row_structure>observableArrayList();
        T3_output_Table.setItems(t3_rows);
        
        //set type options
        types = FXCollections.observableArrayList(DatasetHandler.getTypes());
        T3_type_ComboBox.setItems(types);
        T3_type_ComboBox.setValue("human");
        
        //set country options based on the default of usa
        countries = FXCollections.observableArrayList(DatasetHandler.getCountries("human"));
        T3_country_ComboBox.setItems(countries);
        T3_country_ComboBox.setValue("usa");
        
        //update ranges according to default
        Pair<String,String> validRange = DatasetHandler.getValidRange("human","usa");
        T3_startYear_ComboBox.setValue(validRange.getKey());
        T3_endYear_ComboBox.setValue(validRange.getValue());
        
        selectCountry();
    }
    
    /**
     * Call this when a value from type list is selected. Clears country, startYear, endYear comboBoxes
     * @author Yuxi Sun
     * v 2.0
     */
    @FXML
    void selectType() {
    	//hide all errors
    	hideErrors();
    	//get countries from /type/metadata.txt
    	//Reset range string
        T3_startYear_ComboBox.setValue("");
        T3_endYear_ComboBox.setValue("");
        //update country list
        String type = T3_type_ComboBox.getValue(); // getting type
        countries.clear();
        for (String country: DatasetHandler.getCountries(type)){
            countries.add(country);
        }
        T3_country_ComboBox.setValue("");
        //clearing the StartYear and EndYear values and lists
    }

    /**
     * Call this when a value of country is selected.
     * @author Yuxi Sun
     * v 1.0
     */
    @FXML
    void selectCountry() {
    	//hide all errors
    	hideErrors();
    	//Getting start year and end year limits from /type/country/metadata.txt
        if (isComboBoxEmpty(T3_country_ComboBox.getValue())){
            //if nothing has been selected do nothing
            return;
        }
        String type = T3_type_ComboBox.getValue(); // getting type
        String country = T3_country_ComboBox.getValue(); // getting country
        //update ranges
        Pair<String,String> validRange = DatasetHandler.getValidRange(type,country);

        //update relevant menus

      int start = Integer.parseInt(validRange.getKey());
      int end = Integer.parseInt(validRange.getValue());
      //set up end list
      endOptions.clear();
      startOptions.clear();
      for (int i = start; i <= end ; ++i){
          endOptions.add(Integer.toString(i));
          startOptions.add(Integer.toString(i));
      }
      //set up comboBox default values and valid lists
      T3_startYear_ComboBox.setValue(Integer.toString(start));
      T3_endYear_ComboBox.setValue(Integer.toString(end));
    }
    
    
    //Helper functions
    /**
     * checks if a ComboBox is empty
     * @author Yuxi Sun
     */
    private boolean isComboBoxEmpty(String entry){
        return entry == null || entry.isBlank();
    }
    @FXML
    /**
     * Function is called to hide all error messages
     * @author Yuxi Sun
     */
    void hideErrors(){
        T3_country_error_Text.setVisible(false);
        T3_type_error_Text.setVisible(false);
        T3_range_error_Text.setVisible(false);
        T3_start_year_error_Text.setVisible(false);
        T3_end_year_error_Text.setVisible(false);
    }
    
    
    //validation
    /**
     * Input validation and error message handler for T3 Controller
     * @param start start year specified by user
     * @param end end year specified by user
     * @param country specified by user
     * @param type specified by user
     * @return true if input fields are valid
     * @author Yuxi Sun
     */
    private boolean validateInputs(String iStart, String iEnd, String country, String type) {
    	hideErrors();
    	int validStart;
    	int validEnd;
        boolean valid = true;
//    	//will not occur due to UI interface
//        if (isComboBoxEmpty(type)) {
//        	valid = false;
//        	T3_type_error_Text.setVisible(true);
//        }
        if (isComboBoxEmpty(country)) {
        	valid = false;
        	T3_country_error_Text.setVisible(true);
        }
    	//checking if start year and end year are set
        if (isComboBoxEmpty(iStart)){
            //start is empty
            T3_start_year_error_Text.setVisible(true);
            valid = false;
        }
        if (isComboBoxEmpty(iEnd)){
            //end is empty
            T3_end_year_error_Text.setVisible(true);
            valid = false;
        }
    	if (valid){
            //if years are not empty and valid perform further testing
    		//fetch valid data range
        	Pair<String,String> validRange = DatasetHandler.getValidRange(type, country);
        	validStart = Integer.parseInt(validRange.getKey());
        	validEnd = Integer.parseInt(validRange.getValue());
            //check year range validity
    		int start = Integer.parseInt(iStart);
    		int end  = Integer.parseInt(iEnd);
        	if (start>=end) {
        		T3_range_error_Text.setText("Start year should be < end year");
        		T3_range_error_Text.setVisible(true);
        		valid=false;
        	}
//        	//will not occur due to UI interface
//        	else if (start<validStart) {
//        		T3_range_error_Text.setText("Start year should be >= " + Integer.toString(validStart));
//        		T3_range_error_Text.setVisible(true);
//        		valid=false;
//        	}else if (end>validEnd) {
//        		T3_range_error_Text.setText("End year should be <= " + Integer.toString(validEnd));
//        		T3_range_error_Text.setVisible(true);
//        		valid=false;
//        	}
    	}
//    	//will not occur due to UI interface
//    	if(isComboBoxEmpty(type)) {
//    		//if type is not set
//            T3_type_error_Text.setVisible(true);
//    	}
    	
        if(isComboBoxEmpty(country)) {
            //if country is not set
            T3_country_error_Text.setVisible(true);
        }
    	return valid;
    }
    /**
     * updates the table
     * @param newEntry a T3_row_structure representing a new row 
     * @author Yuxi Sun
     */
	void updateTable(T3_row_structure newEntry){
		t3_rows.add(newEntry);
	}
    //output Struct
    /**
     * Class used as a data structure to represent one row of the table
     * @author Yuxi Sun
     * v 1.0
     */
    public class T3_row_structure{
        private SimpleStringProperty name;
        private SimpleStringProperty startRank;
        private SimpleStringProperty startYear;
        private SimpleStringProperty endRank;
        private SimpleStringProperty endYear;
        private SimpleStringProperty trend;

        //accessors
        /**
         * @return name of the row
         * @author Yuxi Sun
         */
        public String getName(){return name.get();}
        /**
         * @return start rank of the row
         * @author Yuxi Sun
         */
        public String getStartRank(){return startRank.get();}
        /**
         * @return start year of the row
         * @author Yuxi Sun
         */
        public String getStartYear(){return startYear.get();}
        /**
         * @return end rank of the row
         * @author Yuxi Sun
         */
        public String getEndRank(){return endRank.get();}
         /**
          * @return end year of the row
         * @author Yuxi Sun
          */
        public String getEndYear(){return endYear.get();}
        /**
         * @return trend of the row
         * @author Yuxi Sun
         */
        public String getTrend(){return trend.get();}

        //constructor
        /**
         * Constructor of data structure
         * @param name name associated with row
         * @param startRank start rank of the row
         * @param startYear start year of the row
         * @param endRank end rank of the row
         * @param endYear end year of the row
         * @param trend trend of the row
         * @author Yuxi Sun
         */
        public T3_row_structure(String name, String startRank, String startYear, String endRank, String endYear, String trend){
            this.name = new SimpleStringProperty(name);
            this.startRank = new SimpleStringProperty(startRank);
            this.startYear = new SimpleStringProperty(startYear);
            this.endRank = new SimpleStringProperty(endRank);
            this.endYear = new SimpleStringProperty(endYear);
            this.trend = new SimpleStringProperty(trend);
        }
    }


    @FXML
    /**
     * Generate function that validates inputs and creates a TrendInPopularity and updates graphic interface.
         * @author Yuxi Sun
     */
    void trendInPopularity() {
    	//clear summary
    	T3_summary_TextArea.clear();
    	//clearing table
    	this.t3_rows.clear();
    	//getting relevant info
    	String startYear = T3_startYear_ComboBox.getValue();
        String endYear = T3_endYear_ComboBox.getValue();
        String gender = (T3_male_RadioButton.isSelected())?"M":"F";
        String country = T3_country_ComboBox.getValue();
        String type = T3_type_ComboBox.getValue();
        
   	    // error handling/ boundary checking
        if (validateInputs(startYear, endYear, country,type)) {
        	int iStartYear = Integer.parseInt(startYear);
            int iEndYear = Integer.parseInt(endYear);
        	//if inputs are valid
            //generate report
	        TrendInPopularity  report = new TrendInPopularity(iStartYear, iEndYear, gender, T3_country_ComboBox.getValue(), T3_type_ComboBox.getValue());
	        HashMap<String,Vector<String>> results  = report.getResults(); // fetching results

	        //updating summary text to only show the first name rising and first name falling.
	        String rising_names="";
	        String falling_names="";
	        String rising_trend="";
	        String falling_trend="";
	        String summary = "";
	        for (int i = 0; i < results.get("name").size();++i) {
	        	if(Integer.parseInt(results.get("trend").get(i))>=0) {//if trend is positive
	        		if (rising_names.length()==0) {//if no names have been added to rising_names yet
//	        			rising_names = results.get("name").get(i);
//	        	        rising_trend = results.get("trend").get(i);
	        	        summary += String.format("%s is found to have shown the largest rise in popularity from rank %s in year %s to rank %s in year %s.", 
	        	        		results.get("name").get(i), 
	        	        		results.get("startRank").get(i),
	        	        		results.get("startYear").get(i),
	        	        		results.get("endRank").get(i),
	        	        		results.get("endYear").get(i));
	        		               
	        		}else {//do nothing
	        			//falling_names = falling_names +  ", " + results.get("name").get(i);
	        		}
	        	}else {//if trend is negative
	        		if (falling_names.length()==0) {//if no names have been added to falling_names yet
//	        			falling_names = results.get("name").get(i);
//	        	        falling_trend = results.get("trend").get(i);
	        	        summary += String.format(" On the other hand, %s is found to have shown the largest rise in popularity from rank %s in year %s to rank %s in year %s.", 
	        	        		results.get("name").get(i), 
	        	        		results.get("startRank").get(i),
	        	        		results.get("startYear").get(i),
	        	        		results.get("endRank").get(i),
	        	        		results.get("endYear").get(i));
	        		}else {//do nothing
	        			//falling_names = falling_names +  ", " + results.get("name").get(i);
	        		}
	        	}
	        }
	        
	        //setting the text
	        T3_summary_TextArea.setText(summary);
	        //updating table
	        for (int i = 0; i <results.get("name").size();++i){
	            //create T3_row_structure
	        	T3_row_structure entry = new T3_row_structure(results.get("name").get(i),
	            results.get("startRank").get(i),
	            results.get("startYear").get(i),
	            results.get("endRank").get(i),
	            results.get("endYear").get(i),
	            results.get("trend").get(i));
	            //update table
	        	updateTable(entry);
	        }
        }
    }
    
    //infoBox
    @FXML 
    /**
     * Helper function that shows infobox
     * @author Yuxi Sun
     */
    void showInfoBox(){
    	T3_infobox_TextArea.setVisible(true);
    }
    @FXML 
    /**
     * Hides function that shows infobox
     * @author Yuxi Sun
     */
    void hideInfoBox(){
    	T3_infobox_TextArea.setVisible(false);
    }
}