package comp3111.popnames.controllers;

import comp3111.popnames.RecommendBabyName;
import java.util.*;
import org.apache.commons.lang3.tuple.Triple;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BabyNames_controller {
	//will hold data model of each row in the table view
	private ObservableList<BabyNamesTableDataModel> tableViewList;
	//data model for table view's rows
	public class BabyNamesTableDataModel {
		private final SimpleStringProperty name;
		private final SimpleStringProperty gender;
		private final SimpleStringProperty percentageScore;
		
		public BabyNamesTableDataModel(String name, String gender, String percentageScore) {
			this.name = new SimpleStringProperty(name);
			this.gender = new SimpleStringProperty(gender);
			this.percentageScore = new SimpleStringProperty(percentageScore);
		}
		public String getName() {return this.name.get();}
		public String getGender() {return this.gender.get();}
		public String getPercentageScore() {return this.percentageScore.get();}
	}
	
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
    private RadioButton BabyNames_male_radioButton;

    @FXML
    private RadioButton BabyNames_female_radioButton;

    @FXML
    private Button BabyNames_generate_button;

    @FXML
    private TableView<BabyNamesTableDataModel> BabyNames_tableView;

    @FXML
    private TableColumn<BabyNamesTableDataModel, String> BabyNames_tableView_name_tableColumn;

    @FXML
    private TableColumn<BabyNamesTableDataModel, String> BabyNames_tableView_percentageScore_tableColumn;
    
    @FXML
    void initialize() {
    	BabyNames_tableView_name_tableColumn.setCellValueFactory(new PropertyValueFactory<BabyNamesTableDataModel,String>("name"));
    	BabyNames_tableView_percentageScore_tableColumn.setCellValueFactory(new PropertyValueFactory<BabyNamesTableDataModel,String>("percentageScore"));
    	this.tableViewList = FXCollections.<BabyNamesTableDataModel>observableArrayList();
    	BabyNames_tableView.setItems(this.tableViewList);
    	BabyNames_tableView.setPlaceholder(new Label("You haven't generated anything yet."));
    }

    private void errorEffect(TextField errorField, boolean on) {
        DropShadow errorShadow = (DropShadow) (errorField.getEffect());
        errorShadow.setColor(Color.color(1,0,0,(on? 1 : 0)));
        errorField.setEffect(errorShadow);
    }

    @FXML
    void generateBabyNames() {
        /* Variables */
        String fatherName = BabyNames_fatherName_textField.getText();
        String motherName = BabyNames_motherName_textField.getText();
        int fatherYear = -1;
        int motherYear = -1;
        int vintageYear = -1;
        final int MAX_YEAR = 2019;
        final int MIN_YEAR = 1880;
        
        //Handle empty name
        boolean fatherNameError = fatherName.isBlank();
        boolean motherNameError = motherName.isBlank();
        errorEffect(BabyNames_fatherName_textField, fatherNameError);
        errorEffect(BabyNames_motherName_textField, motherNameError);
        BabyNames_fatherNameError_label.setText((fatherNameError)? "Please input a name." : "");
        BabyNames_motherNameError_label.setText((motherNameError)? "Please input a name." : "");
        
        //Handle start year
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
        		System.out.println("blank");
        		vintageYearError = true;
                BabyNames_vintageYearError_label.setText("Vintage year is invalid.");
        	} else {
        		BabyNames_vintageYearError_label.setText("");
        		vintageYear = 2019;
        	}
        }

        errorEffect(BabyNames_fatherYear_textField, fatherYearError);
        errorEffect(BabyNames_motherYear_textField, motherYearError);
        errorEffect(BabyNames_vintageYear_textField, vintageYearError);
        
        if(!(fatherNameError || motherNameError || fatherYearError || motherYearError || vintageYearError)) {
            this.tableViewList.clear();
            String gender = (BabyNames_male_radioButton.isSelected()? "M" : "F");
            RecommendBabyName babyNames = new RecommendBabyName(fatherName, motherName, fatherYear, motherYear, vintageYear, gender,"usa", "human");
            List<Triple<String,Integer,Integer>> babyNamesList = babyNames.getBabyNamesList();
            for (int i = 0; i < babyNamesList.size(); ++i) {
                Triple<String,Integer,Integer> babyName = babyNamesList.get(i);
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedPercentageScore = df.format(babyNamesList.get(i).getMiddle()*babyNamesList.get(i).getRight());//df.format(babyName.getRight())+"%";
                
                BabyNamesTableDataModel row = new BabyNamesTableDataModel(babyName.getLeft(), Integer.toString(babyName.getMiddle()), formattedPercentageScore);
                this.tableViewList.add(row);
            }
        }
    }
}
