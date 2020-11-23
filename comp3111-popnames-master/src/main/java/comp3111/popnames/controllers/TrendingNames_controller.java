package comp3111.popnames.controllers;

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

import edu.duke.FileResource;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.*;
import javafx.util.Pair;

import java.io.*;

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
    private TextField T3_startYear_TextField;

    @FXML
    private TextField T3_endYear_TextField;

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
    private Text T3_start_year_limit_Text;

    @FXML
    private Text T3_end_year_limit_Text;
    
    @FXML
    private TableColumn<?, ?> T3_trend_TableColumn;

    private ObservableList<T3_row_structure> t3_rows;

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
        ObservableList<String> type = FXCollections.observableArrayList(DatasetHandler.getTypes());
        T3_type_ComboBox.setItems(type);
        T3_type_ComboBox.setValue("human");
        
        //set country options based on the default of usa
        ObservableList<String> countries = FXCollections.observableArrayList(DatasetHandler.getCountries("human"));
        T3_country_ComboBox.setItems(countries);
        T3_country_ComboBox.setValue("usa");
        
        //update ranges according to default
        Pair<String,String> validRange = DatasetHandler.getValidRange("human","usa");
        T3_start_year_limit_Text.setText(validRange.getKey());
        T3_end_year_limit_Text.setText(validRange.getValue());
    }
    
    /**
     * Call this when a value from type list is selected. 
     * @author Yuxi Sun
     * v 1.0
     */
    @FXML
    void updateType() {
    	//get countries from /type/metadata.txt
    	//Reset range string
        T3_start_year_limit_Text.setText("");
        T3_end_year_limit_Text.setText("");
        //update country list
        String type = T3_type_ComboBox.getValue(); // getting type
        ObservableList<String> countries = FXCollections.observableArrayList(DatasetHandler.getCountries(type));
        T3_country_ComboBox.setItems(countries);
    }
    
    /**
     * Call this when a value of country is selected.
     * @author Yuxi Sun
     * v 1.0
     */
    @FXML
    void updateCountry() {
    	//Getting start year and end year limits from /type/country/metadata.txt
        String type = T3_type_ComboBox.getValue(); // getting type
        String country = T3_country_ComboBox.getValue(); // getting country
        //update ranges
        Pair<String,String> validRange = DatasetHandler.getValidRange(type,country);
        T3_start_year_limit_Text.setText(validRange.getKey());
        T3_end_year_limit_Text.setText(validRange.getValue());
    }
    void updateTable(T3_row_structure newEntry){
        t3_rows.add(newEntry);
    }
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
    	this.t3_rows.clear();
    	int iStartYear = Integer.parseInt(T3_startYear_TextField.getText());
        int iEndYear = Integer.parseInt(T3_endYear_TextField.getText());
        String gender = (T3_male_RadioButton.isSelected())?"M":"F";
        String oReport = "";
   	    // error handling/ boundary checking
        // checking for year out of bound
        boolean rangeError = false;
        if (iStartYear>iEndYear) {
        	oReport += "Start Year should be smaller than to End Year\n";
        	rangeError = true;
        }
        if (iStartYear<1880) {
        	oReport += "Start Year should be within the valid range of 1880 - 2019\n";
        	rangeError = true;
        }
        if (iEndYear>2019) {
        	oReport += "End Year should be within the valid range of 1880 - 2019\n";
        	rangeError = true;
        }

        if (rangeError) {
//            textAreaConsole.setText(oReport);
        	System.out.println(oReport);
        	return;
        }
        //updating table
        TrendInPopularity  report = new TrendInPopularity(iStartYear, iEndYear, gender, "usa", "human");
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
