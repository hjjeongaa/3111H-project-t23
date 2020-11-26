/**
 * NamePopularity_controller
 * UI controller for task 2's tab
 * @author Hyun Joon Jeong
 */

package comp3111.popnames.controllers;

import comp3111.popnames.PopularityOfName;

//UI
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.util.*;
import org.apache.commons.lang3.tuple.Triple;

public class NamePopularity_controller {
	//will hold data model of each row in the table view
	private ObservableList<NamePopularityTableDataModel> tableViewList;
	/**
	 * Data model for the table view which will store all results.
	 * Table view's columns consist of the name's year, rank, total # of names in the year, and the name's rank's percentile.
	 */
	public class NamePopularityTableDataModel {
		private final SimpleStringProperty year;
		private final SimpleStringProperty rank;
		private final SimpleStringProperty total;
		private final SimpleStringProperty percentile;
		
		public NamePopularityTableDataModel(String year, String rank, String total, String percentile) {
			this.year = new SimpleStringProperty(year);
			this.rank = new SimpleStringProperty(rank);
			this.total = new SimpleStringProperty(total);
			this.percentile = new SimpleStringProperty(percentile);
		}
		public String getYear() {return this.year.get();}
		public String getRank() {return this.rank.get();}
		public String getTotal() {return this.total.get();}
		public String getPercentile() {return this.percentile.get();}
	}
	
    @FXML
    private TextField NamePopularity_name_textField;

    @FXML
    private Label NamePopularity_nameError_label;
    
    @FXML
    private RadioButton NamePopularity_male_radioButton;

    @FXML
    private RadioButton NamePopularity_female_radioButton;

    @FXML
    private TextField NamePopularity_startYear_textField;

    @FXML
    private TextField NamePopularity_endYear_textField;

    @FXML
    private Label NamePopularity_yearError_label;
    
    @FXML
    private Button NamePopularity_generate_button;

    @FXML
    private TableView<NamePopularityTableDataModel> NamePopularity_tableView;

    @FXML
    private TableColumn<NamePopularityTableDataModel, String> NamePopularity_tableView_year_tableColumn;

    @FXML
    private TableColumn<NamePopularityTableDataModel, String> NamePopularity_tableView_rank_tableColumn;
    
    @FXML
    private TableColumn<NamePopularityTableDataModel, String> NamePopularity_tableView_total_tableColumn;

    @FXML
    private TableColumn<NamePopularityTableDataModel, String> NamePopularity_tableView_percentile_tableColumn;
    /**
     * initialize: called once when the UI is initialized
     * Bind the table view's columns to the data model, and link tableViewList with the table view.
     */
    @FXML
    void initialize() {
    	NamePopularity_tableView_year_tableColumn.setCellValueFactory(new PropertyValueFactory<NamePopularityTableDataModel,String>("year"));
    	NamePopularity_tableView_rank_tableColumn.setCellValueFactory(new PropertyValueFactory<NamePopularityTableDataModel,String>("rank"));
    	NamePopularity_tableView_total_tableColumn.setCellValueFactory(new PropertyValueFactory<NamePopularityTableDataModel,String>("total"));
    	NamePopularity_tableView_percentile_tableColumn.setCellValueFactory(new PropertyValueFactory<NamePopularityTableDataModel,String>("percentile"));
    	this.tableViewList = FXCollections.<NamePopularityTableDataModel>observableArrayList();
    	NamePopularity_tableView.setItems(this.tableViewList);
    	NamePopularity_tableView.setPlaceholder(new Label("You haven't generated anything yet."));
    }
    /**
     * Helper function to create a red highlight around text fields with an error.
     * @param errorField the text field to highlight
     * @param on whether the error highlight should be on or not
     */
    private void errorEffect(TextField errorField, boolean on) {
    	DropShadow errorShadow = (DropShadow) (errorField.getEffect());
		errorShadow.setColor(Color.color(1,0,0,(on? 1 : 0)));
		errorField.setEffect(errorShadow);
    }
    /**
     * Called when the generate button is pressed.
     * Performs input checking on all the inputs, and handles error UI. If none of the inputs have errors, the PopularityOfName object is called and a report is generated. The resultant List from the object is used to fill the table view in the UI.
     */
    @FXML
    void generatePopularityOfName() {
    	//variables needed to generate report
    	String name = NamePopularity_name_textField.getText();
    	int startYear = -1;
    	int endYear = -1;
    	String gender;
    	
    	final int MAX_YEAR = 2019;
    	final int MIN_YEAR = 1880;
    	
    	//Handle empty name
    	boolean nameError = name.isBlank();
    	errorEffect(NamePopularity_name_textField, nameError);
    	NamePopularity_nameError_label.setText((nameError)? "Please input a name." : "");
    	
    	//Handle start year
    	boolean startYearError = false;
    	boolean endYearError = false;
    	try {
    		startYear = Integer.parseInt(NamePopularity_startYear_textField.getText());
    		if(startYear > MAX_YEAR || startYear < MIN_YEAR) {
    			startYearError = true;
    			NamePopularity_yearError_label.setText("Your start year is out of range.");
    		} else {
    			errorEffect(NamePopularity_startYear_textField, false);
    			//Handle end year. Try/catch must be nested because the error message depends on whether both the start year and end year having errors
    			try {
	        		endYear = Integer.parseInt(NamePopularity_endYear_textField.getText());
	        		if(endYear > MAX_YEAR || endYear < startYear) {
	        			endYearError = true;
	        			NamePopularity_yearError_label.setText("Your end year is out of range.");
	        		} else {
	        			NamePopularity_yearError_label.setText("");
	        		}
	        	} catch(NumberFormatException e) {
	        		endYearError = true;
	        		NamePopularity_yearError_label.setText("Your end year is invalid.");
	        	}
    		}
    	} catch(NumberFormatException e) {
    		startYearError = true;
    		NamePopularity_yearError_label.setText("Your start year is invalid.");
    	}
    	errorEffect(NamePopularity_startYear_textField, startYearError);
    	errorEffect(NamePopularity_endYear_textField, endYearError);
	    		
    	if(!(nameError || startYearError || endYearError)) {
    		//Clear tableview from any previous entries
    		this.tableViewList.clear();
    		gender = (NamePopularity_male_radioButton.isSelected())?"M":"F";
    		//Generate the report.
    		PopularityOfName namePopularity = new PopularityOfName(startYear, endYear, name, gender,"usa", "human");
    		List<Triple<Integer,Integer,Double>> namePopularityList = namePopularity.getPopularityList();
    		//Using the list of results obtained from PopularityOfName, fill the tableview by converting each entry in the List to the data model for the table.
    		for (int i = 0; i < endYear - startYear; ++i) {
    			Triple<Integer,Integer,Double> yearlyStatistics = namePopularityList.get(i);
    			
    			String formattedYear = Integer.toString(i+startYear);
    			String formattedRank = "Not found";
    			String formattedTotal = "Empty";
    			String formattedPercentile = "0%";
    			
    			int rank = yearlyStatistics.getLeft();
    			int total = yearlyStatistics.getMiddle();
    			Double percentile = yearlyStatistics.getRight();
    			
    			//Format year and percentile
    			if (rank != 0) {
    				formattedRank = Integer.toString(yearlyStatistics.getLeft());
    				DecimalFormat df = new DecimalFormat("#.##");
    				formattedPercentile = df.format(percentile)+"%";
    			}
    			//Format total
    			if (total != 0) {
    				formattedTotal = Integer.toString(yearlyStatistics.getMiddle());
    			}
    			
    			NamePopularityTableDataModel row = new NamePopularityTableDataModel(formattedYear, formattedRank, formattedTotal, formattedPercentile);
    			this.tableViewList.add(row);
    		}
    	}
    }
}