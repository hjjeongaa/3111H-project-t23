/**
 * BabyNames_controller
 * UI controller for task 4's tab.
 * @author Hyun Joon Jeong
 */

package comp3111.popnames.controllers;

import comp3111.popnames.GlobalSettings;
import comp3111.popnames.RecommendBabyName;
import comp3111.popnames.ReportLog;

import java.util.*;
import org.apache.commons.lang3.tuple.Triple;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BabyNames_controller {
	//will hold data model of each row in the table view
	private ObservableList<BabyNamesTableDataModel> tableViewList;
	/**
	 * Data model class for table view's rows.
	 * Table view's columns consist of the recommended baby name and their percentage score.
	 */
	public class BabyNamesTableDataModel {
		private final SimpleStringProperty name;
		private final SimpleStringProperty percentageScore;
		
		public BabyNamesTableDataModel(String name, String percentageScore) {
			this.name = new SimpleStringProperty(name);
			this.percentageScore = new SimpleStringProperty(percentageScore);
		}
		public String getName() {return this.name.get();}
		public String getPercentageScore() {return this.percentageScore.get();}
	}
	@FXML
	private Label BabyNames_title_label;

	@FXML
	private Label BabyNames_information_label;
	
	@FXML
	private Button BabyNames_information_button;
	
	@FXML
	private GridPane BabyNames_inputGrid_gridpane;
	
    @FXML
    private TextField BabyNames_motherName_textField;

    @FXML
    private TextField BabyNames_fatherName_textField;

    @FXML
    private TextField BabyNames_fatherYear_textField;

    @FXML
    private TextField BabyNames_motherYear_textField;

    @FXML
    private TextField BabyNames_vintageYear_textField;
    
    @FXML
    private Label BabyNames_motherNameError_label;

    @FXML
    private Label BabyNames_motherYearError_label;

    @FXML
    private Label BabyNames_fatherNameError_label;

    @FXML
    private Label BabyNames_fatherYearError_label;

    @FXML
    private Label BabyNames_vintageYearError_label;
    
    @FXML
    private Label BabyNames_wordCloudError_label;
    
    @FXML
    private RadioButton BabyNames_male_radioButton;

    @FXML
    private RadioButton BabyNames_female_radioButton;

    @FXML
    private Button BabyNames_generate_button;
    
    @FXML
    private ImageView BabyNames_wordCloud_imageView;

    @FXML
    private TableView<BabyNamesTableDataModel> BabyNames_tableView;

    @FXML
    private TableColumn<BabyNamesTableDataModel, String> BabyNames_tableView_name_tableColumn;

    @FXML
    private TableColumn<BabyNamesTableDataModel, String> BabyNames_tableView_percentageScore_tableColumn;
    
    /**
     * initialize: called once when the UI is initialized
     * Bind the table view's columns to the data model, and link tableViewList with the table view.
     */
    @FXML
    void initialize() {
    	BabyNames_tableView_name_tableColumn.setCellValueFactory(new PropertyValueFactory<BabyNamesTableDataModel,String>("name"));
    	BabyNames_tableView_percentageScore_tableColumn.setCellValueFactory(new PropertyValueFactory<BabyNamesTableDataModel,String>("percentageScore"));
    	this.tableViewList = FXCollections.<BabyNamesTableDataModel>observableArrayList();
    	BabyNames_tableView.setItems(this.tableViewList);
    	BabyNames_tableView.setPlaceholder(new Label("Empty"));
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
     * Performs input checking on all the inputs, and handles error UI. If none of the inputs have errors, the RecommendBabyName object is initialized and the list is generated.
     */
    @FXML
    void generateBabyNames() {
        // Variables needed to generated the baby names list.
        String fatherName = BabyNames_fatherName_textField.getText();
        String motherName = BabyNames_motherName_textField.getText();
        int fatherYear = -1;
        int motherYear = -1;
        int vintageYear = -1;
        final int MAX_YEAR = GlobalSettings.getUpperBound();
        final int MIN_YEAR = GlobalSettings.getLowerBound();
        
        //Handle empty names.
        boolean fatherNameBlank = fatherName.isBlank();
        boolean motherNameBlank = motherName.isBlank();
        fatherName = ReportLog.validateName(fatherName);
        motherName = ReportLog.validateName(motherName);
        boolean fatherNameError = fatherName.isEmpty();
        boolean motherNameError = motherName.isEmpty();
        //set error effect and error message if needed
        errorEffect(BabyNames_fatherName_textField, fatherNameError || fatherNameBlank);
        errorEffect(BabyNames_motherName_textField, motherNameError || motherNameBlank);
        String fatherNameErrorMessage = "";
        String motherNameErrorMessage = "";
        if (fatherNameBlank) {fatherNameErrorMessage = "Please input a name.";}
        else if (fatherNameError) {fatherNameErrorMessage = "Please input a valid name.";}
        if (motherNameBlank) {motherNameErrorMessage = "Please input a name.";}
        else if (motherNameError) {motherNameErrorMessage = "Please input a valid name.";}
        BabyNames_fatherNameError_label.setText(fatherNameErrorMessage);
        BabyNames_motherNameError_label.setText(motherNameErrorMessage);
        
        //Handle invalid father year
        //Different error messages appear for whether the inputted year is out of range or invalid
        boolean fatherYearError = false;
        try {
            fatherYear = Integer.parseInt(BabyNames_fatherYear_textField.getText());
            if(fatherYear > MAX_YEAR || fatherYear < MIN_YEAR) {
                fatherYearError = true;
                BabyNames_fatherYearError_label.setText("Father's YOB is out of range.");
            } else {
            	BabyNames_fatherYearError_label.setText("");
            }
        } catch(NumberFormatException e) {
            fatherYearError = true;
            BabyNames_fatherYearError_label.setText("Father's YOB is invalid.");
        }
        //Handle invalid mother's year
        boolean motherYearError = false;
        try {
            motherYear = Integer.parseInt(BabyNames_motherYear_textField.getText());
            if(motherYear > MAX_YEAR || motherYear < MIN_YEAR) {
                motherYearError = true;
                BabyNames_motherYearError_label.setText("Mother's YOB is out of range.");
            } else {
            	BabyNames_motherYearError_label.setText("");
            }
        } catch(NumberFormatException e) {
            motherYearError = true;
            BabyNames_motherYearError_label.setText("Mother's YOB is invalid.");
        }
        //Special case for vintage year - if it is blank, it is automatically set to 2019.
        boolean vintageYearError = false;
        try {
            vintageYear = Integer.parseInt(BabyNames_vintageYear_textField.getText());
            if(vintageYear > MAX_YEAR || vintageYear < MIN_YEAR) {
                vintageYearError = true;
                BabyNames_vintageYearError_label.setText("Vintage year is out of range.");
            } else {
            	BabyNames_vintageYearError_label.setText("");
            }
        } catch(NumberFormatException e) {
        	if (!BabyNames_vintageYear_textField.getText().isBlank()) {
        		vintageYearError = true;
                BabyNames_vintageYearError_label.setText("Vintage year is invalid.");
        	} else {
        		BabyNames_vintageYearError_label.setText("");
        		vintageYear = GlobalSettings.getUpperBound();
        	}
        }
        //Set error effects for year text fields if needed
        errorEffect(BabyNames_fatherYear_textField, fatherYearError);
        errorEffect(BabyNames_motherYear_textField, motherYearError);
        errorEffect(BabyNames_vintageYear_textField, vintageYearError);
        
        //Without any errors, the baby names can be generated.
        if(!(fatherNameError || fatherNameBlank || motherNameError || motherNameBlank || fatherYearError || motherYearError || vintageYearError)) {
        	//Clear tableview from previous results
            this.tableViewList.clear();
            String gender = (BabyNames_male_radioButton.isSelected()? "M" : "F");
            //Generate baby names
            RecommendBabyName babyNames = new RecommendBabyName(fatherName, motherName, fatherYear, motherYear, vintageYear, gender,GlobalSettings.getCountry(), "human");
            List<Triple<String,Integer,Integer>> babyNamesList = babyNames.getBabyNamesList();
            //Assign each baby name a table data model and add it to the tableViewList.
            int maxScore = 0;
    		for (int i = 0; i < babyNamesList.size(); i++) {
    			int score = (i)+babyNamesList.get(i).getMiddle()+(5*babyNamesList.get(i).getRight());
    			if (maxScore < score) {
    				maxScore = score;
    			}
    		}
            for (int i = 0; i < babyNamesList.size(); ++i) {
                Triple<String,Integer,Integer> babyName = babyNamesList.get(i);
                DecimalFormat df = new DecimalFormat("#.##");
                int score = (i)+babyNamesList.get(i).getMiddle()+(5*babyNamesList.get(i).getRight());
                String formattedPercentageScore = df.format(100.0*(1.0-((double)score/(double)maxScore)))+"%";//df.format(babyName.getRight())+"%";
                
                BabyNamesTableDataModel row = new BabyNamesTableDataModel(babyName.getLeft(), formattedPercentageScore);
                this.tableViewList.add(row);
            }
            if (babyNamesList.size() > 20 && babyNames.getWordCloudImageBytes() != null) {
            	BabyNames_wordCloudError_label.setText("");
            	InputStream input = new ByteArrayInputStream(babyNames.getWordCloudImageBytes());
                Image image = new Image(input);
                BabyNames_wordCloud_imageView.setImage(image);
            } else {
            	BabyNames_wordCloudError_label.setText("Word cloud too small.");
            }
        }
    }
    private boolean informationShown = false;
    @FXML
    void toggleInformation() {
    	if (!informationShown) {
    		BabyNames_title_label.setText("How do I work?");
    		BabyNames_inputGrid_gridpane.setVisible(false);
    		BabyNames_generate_button.setVisible(false);
    		BabyNames_wordCloud_imageView.setVisible(false);
        	
        	BabyNames_information_label.setVisible(true);
        	BabyNames_information_label.setText(
        			"This algorithm works by finding a name which has the greatest similarity between "
        			+ "both of the parents.\n"
        			+ "It does this by using an algorithm called the 'Levenshtein distance', which calculates "
        			+ "how many characters\n"
        			+ "must be inserted, deleted, or substituted for two names to be the same.\n"
        			+ "To generate a baby's name, we firstly load all of the names from the vintage year that is specified. (The\n"
        			+ "vintage year represents the year that the baby is supposed to be born, so it is appopriate for us to\n"
        			+ "generate a baby name that is from the vintage year.)\n" + 
        			"Then, we create two lists; one will contain the list of names that are sorted by similarity to the father's\n"
        			+ "name, and one for similarity to the mother's name. The algorithm determines whether a baby name is similar\n"
        			+ "to a parent name by looking for names with a Levenshtein distance of approximately half the length of the\n"
        			+ "parent's name. This way, we can try and obtain names that are neither too similar, nor too different, to\n"
        			+ "the parent's name.\n"
        			+ "Along with this, we store the difference between the Levenshtein distance to the father's name, and the\n"
        			+ "Levenshtein distance to the mother's name - the lower this is, the better. For example, if the difference\n"
        			+ "in the Levenshtein distances is 0, then both the father and the mother have the same Levenshtein distance\n"
        			+ "to the baby's name, meaning there is an equal mix of both the father and mother's name in the baby name.\n"
        			+ "Now that we have a list of 'candidate' names from both the father and mother's name, it's time to combine\n"
        			+ "these lists. But this algorithm won't just combine them equally!"
        			+ "\nA parent with a year of birth closer to the baby's year of birth will likely have a more 'appropriate'\n"
        			+ "name for the baby's name to be similar to. This means, for example, if the mother were born 20 years before\n"
        			+ "the baby's year of birth, and the father were born 60 years before, then more names from the mother's\n"
        			+ "candidate names will be included in the final list of candidate names.\nFinally, we also consider whether\n"
        			+ "a baby boy or girl's name is being generated. The parent with the same gender as the baby name being\n"
        			+ "generated will likely have a more appropriate name for the baby's name to be similar to.");
        	BabyNames_information_button.setText("Back");
        } else {
        	BabyNames_title_label.setText("Here, you can generate baby names from a mother and father's information.");
    		BabyNames_inputGrid_gridpane.setVisible(true);
    		BabyNames_generate_button.setVisible(true);
    		BabyNames_wordCloud_imageView.setVisible(true);
        	
        	BabyNames_information_label.setVisible(false);
        	BabyNames_information_button.setText("How do I work?");
        }
    	informationShown = !informationShown;
	}
}
