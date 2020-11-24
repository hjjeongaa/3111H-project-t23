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
    //output tables
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
    void initialize(){
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

        //Initialize and update both lists
        endOptions = FXCollections.observableArrayList();
        startOptions = FXCollections.observableArrayList();
    }
    
    /**
     * Call this when a value from type list is selected. Clears country, startYear, endYear comboBoxes
     * @author Yuxi Sun
     * v 2.0
     */
    @FXML
    void selectType() {
    	//get countries from /type/metadata.txt
    	//Reset range string
        System.out.println("select type");
        T3_startYear_ComboBox.setValue("");
        T3_endYear_ComboBox.setValue("");
        T3_startYear_ComboBox.setItems(null);
        T3_endYear_ComboBox.setItems(null);
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
    	//Getting start year and end year limits from /type/country/metadata.txt
        System.out.println("select Country");
        if (T3_country_ComboBox.getValue() == null){
            //if nothing has been selected do nothing
            return;
        }
        String type = T3_type_ComboBox.getValue(); // getting type
        String country = T3_country_ComboBox.getValue(); // getting country
        //update ranges
        Pair<String,String> validRange = DatasetHandler.getValidRange(type,country);
        //set up comboBox default values and valid lists
        T3_startYear_ComboBox.setValue(validRange.getKey());
        T3_endYear_ComboBox.setValue(validRange.getValue());
        //update relevant menus
        selectStart();
        selectEnd();
    }

    /**
     * Function is called to hide all error messages
     * Yuxi Sun
     */
    private void hideErrors(){
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
     * @return
     */
    private boolean validateInputs(String iStart, String iEnd, String country, String type) {
    	hideErrors();
        boolean valid = true;
    	Pair<String,String> validRange = DatasetHandler.getValidRange(type, country);
    	int validStart = Integer.parseInt(validRange.getKey());
    	int validEnd = Integer.parseInt(validRange.getValue());
    	//checking if start year and end year are set
        if (iStart == null){
            //start is empty
            T3_start_year_error_Text.setVisible(true);
            valid = false;
        }
        if (iEnd == null){
            //end is empty
            T3_end_year_error_Text.setVisible(true);
            valid = false;
        }
    	if (valid){
            //if years are not empty and valid perform further testing
            //check year range validity
    		int start = Integer.parseInt(iStart);
    		int end  = Integer.parseInt(iEnd);
        	if (start>=end) {
        		T3_range_error_Text.setText("Start year should be <= end year");
        		valid=false;
        	}else if (start<validStart) {
        		T3_range_error_Text.setText("Start year should be >= " + Integer.toString(validStart));
        		valid=false;
        	}else if (end>validEnd) {
        		T3_range_error_Text.setText("End year should be <= " + Integer.toString(validEnd));
        		valid=false;
        	}
    	}
    	if(type == null) {
    		//if type is not set
            T3_type_error_Text.setVisible(true);
    	}
        if(country == null) {
            //if country is not set
            T3_country_error_Text.setVisible(true);
        }
//T3_country_error_Text;
//T3_type_error_Text;
//T3_range_error_Text;
    	return valid;
    }
    //output Struct
    /**
     * Class used to display one row table
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
        public String getName(){return name.get();}
        public String getStartRank(){return startRank.get();}
        public String getStartYear(){return startYear.get();}
        public String getEndRank(){return endRank.get();}
        public String getEndYear(){return endYear.get();}
        public String getTrend(){return trend.get();}

        //constructor
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
    void trendInPopularity() {
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
	        //updating table
            
	        TrendInPopularity  report = new TrendInPopularity(iStartYear, iEndYear, gender, T3_country_ComboBox.getValue(), T3_type_ComboBox.getValue());
	        HashMap<String,Vector<String>> results  = report.getResults(); // fetching results
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
	//        System.out.println(report.getoReport());
        }
    }
}


//     /**
//      * updates end year list
//      * Yuxi Sun
//      * v1.0
//      */
//     @FXML 
//     void selectStart(){        
//         if (T3_startYear_ComboBox.getValue() == null){
//             //if nothing has been selected do nothing
//             return;
//         }
//      System.out.println("start trigger");
//         //getting valid range
//         Pair<String,String> validRange = DatasetHandler.getValidRange(T3_type_ComboBox.getValue(),T3_country_ComboBox.getValue());

//         int start = Integer.parseInt(T3_startYear_ComboBox.getValue())+1; //+1 to avoid collision
//         int end = Integer.parseInt(validRange.getValue());//avoid collision with end
//         //set up end list
        
//         endOptions.clear();
//         for (int i = start; i <= end ; ++i){
//             endOptions.add(Integer.toString(i));
//         }
        
//         T3_endYear_ComboBox.setItems(endOptions);
//     }
//     /**
//      * updates start year list
//      * Yuxi Sun
//      * v1.0
//      */
//     @FXML 
//     void selectEnd(){
//         if (T3_endYear_ComboBox.getValue() == null){
//             //if nothing has been selected do nothing
//             return;
//         }
//         System.out.println("end trigger");
//         //getting valid range
//         Pair<String,String> validRange = DatasetHandler.getValidRange(T3_type_ComboBox.getValue(),T3_country_ComboBox.getValue());
//         int start = Integer.parseInt(validRange.getKey());        //avoid collision with start
//         int end = Integer.parseInt(T3_endYear_ComboBox.getValue())-1; //+1 to avoid collision
//         //set up start list
// //        System.out.println(start);
// //        System.out.println(end);
//         startOptions.clear();
//         for (int i = start; i <= end ; ++i){
//             startOptions.add(Integer.toString(i));
//         }
//         T3_startYear_ComboBox.setItems(startOptions);
        
//     }
//     void updateTable(T3_row_structure newEntry){
//         t3_rows.add(newEntry);
//     }