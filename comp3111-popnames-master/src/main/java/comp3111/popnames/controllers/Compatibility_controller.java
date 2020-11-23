package comp3111.popnames.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import comp3111.popnames.DatasetHandler;
import comp3111.popnames.PredictCompatibilityScore;
import comp3111.rankingAlgo.RankingAlgorithmFactory;
import comp3111.rankingAlgo.rankResolver;
import java.util.*;
import javafx.util.Pair;
/**
 * Controller for T6 Predict Compatibility Score
 * @author Yuxi Sun
 * v1.0
 */
public class Compatibility_controller {

    @FXML
    private RadioButton T6_user_male_RadioButton;

    @FXML
    private RadioButton T6_user_female_RadioButton;

    @FXML
    private TextField T6_user_name_TextField;

    @FXML
    private ComboBox<String> T6_user_type_ComboBox;

    @FXML
    private ComboBox<String> T6_user_country_ComboBox;

    @FXML
    private ComboBox<String> T6_user_yob_ComboBox;

    @FXML
    private RadioButton T6_mate_male_RadioButton;

    @FXML
    private RadioButton T6_mate_female_RadioButton;

    @FXML
    private TextField T6_mate_name_TextField;

    @FXML
    private ComboBox<String> T6_mate_type_ComboBox;

    @FXML
    private ComboBox<String> T6_mate_country_ComboBox;

    @FXML
    private ComboBox<String> T6_preferences_ComboBox;

    @FXML
    private ComboBox<String> T6_ranking_algorithm_ComboBox;

    @FXML
    private ComboBox<String> T6_ranking_resolver_ComboBox;

    @FXML
    private Button T6_generate_Button;

    @FXML
    private Arc T6_nkt6_Arc;

    @FXML
    private Circle nkt;

    @FXML
    private Text T6_nkt6_score_Text;

    @FXML
    private Arc T6_parm_Arc;

    @FXML
    private Text T6_parm_score_Text;

    @FXML
    private Arc T6_pasrm_Arc;

    @FXML
    private Text T6_pasrm_score_Text;

    @FXML
    private Arc T6_dld_Arc;

    @FXML
    private Text T6_dld_score_Text;

    @FXML 
    private Text T6_pref_error_Text;
    
    //UI components
    public void initialize(){
    	//hide error message(s)
    	T6_pref_error_Text.setVisible(false);
        //User initializers
        //set type options
        ObservableList<String> userType = FXCollections.observableArrayList(DatasetHandler.getTypes());
        T6_user_type_ComboBox.setItems(userType);
        T6_user_type_ComboBox.setValue("human");
        
        //set country options based on the default of usa
        selectUserType();
        T6_user_country_ComboBox.setValue("usa");
        
        //update YOB list according to default
        selectUserCountry();
        T6_user_yob_ComboBox.setValue("1941");

        //Mate initializers
        ObservableList<String> mateType = FXCollections.observableArrayList(DatasetHandler.getTypes());
        T6_mate_type_ComboBox.setItems(mateType);
        T6_mate_type_ComboBox.setValue("human");
        
        //set country options based on the default of usa
        selectMateType();
        T6_mate_country_ComboBox.setValue("usa");
        
        updatePreferences();
        
        
        //initialize ranking settings
        //initialize ranking Algorithm
        ObservableList<String> rankingAlgorithms = FXCollections.observableArrayList(RankingAlgorithmFactory.getNonIterativeMethods());
        T6_ranking_algorithm_ComboBox.setItems(rankingAlgorithms);
        T6_ranking_algorithm_ComboBox.setValue("scr");
        //initialize rank Resolver
        ObservableList<String> rankResolvers = FXCollections.observableArrayList(rankResolver.getResolutionMethods());
        T6_ranking_resolver_ComboBox.setItems(rankResolvers);
        T6_ranking_resolver_ComboBox.setValue("dld");
    }
    //User UI components
    @FXML
    private void selectUserType() {
        ObservableList<String> userCountries = FXCollections.observableArrayList(DatasetHandler.getCountries(T6_user_type_ComboBox.getValue()));
        T6_user_country_ComboBox.setItems(userCountries);
        //clearing and resetting userCountry and userYOB;
        T6_user_country_ComboBox.setValue("");
        T6_user_yob_ComboBox.setItems(FXCollections.observableArrayList());
        T6_user_yob_ComboBox.setValue("");
    }
    
    @FXML
    private void selectUserCountry() {
    	//Initializing empty list
        ObservableList<String> userYob = FXCollections.observableArrayList();
        //getting start year and end year of specified dataset/type/country/
        Pair<String,String> userValidRange = DatasetHandler.getValidRange(T6_user_type_ComboBox.getValue(),T6_user_country_ComboBox.getValue());
        int start = Integer.parseInt(userValidRange.getKey());
        int end = Integer.parseInt(userValidRange.getValue());
        for(int i = start; i <= end; ++i){
        	//appending all valid years to the list to be displayed in 
            userYob.add(Integer.toString(i));
        }
        T6_user_yob_ComboBox.setItems(userYob);
        T6_user_yob_ComboBox.setValue(Integer.toString((start+end)/2));//setting the default YOB to the median year of said range.
    }
    
    @FXML
    private void selectUserYOB() {
    	//update preference list
    	updatePreferences();
    }
    //Mate UI components
    @FXML
    private void selectMateType() {
        ObservableList<String> mateCountries = FXCollections.observableArrayList(DatasetHandler.getCountries(T6_mate_type_ComboBox.getValue()));
        T6_mate_country_ComboBox.setItems(mateCountries);
        //clearing and resetting mateCountry and preference;
        T6_mate_country_ComboBox.setValue("");
        T6_preferences_ComboBox.setItems(FXCollections.observableArrayList());
        T6_preferences_ComboBox.setValue("");
    }
    @FXML
    private void updatePreferences() {
//    	T6_pref_error_Text.setVisible(false);
    	//list of preferences [-5,5];
        ObservableList<String> preferenceOptions = FXCollections.observableArrayList();
        if (!T6_mate_country_ComboBox.getValue().strip().equals("")) {
        	//if T6_mate_country_ComboBox has been set then we can update the preference options
	        if (T6_user_yob_ComboBox.getValue().strip().equals("")) {
	        	// if YOB is not set then give the users the full range of options from -5 to +5
	            //getting start year and end year of specified dataset/type/country/
	    		for (int i = -5; i <= 5; ++i) {
	    			preferenceOptions.add(Integer.toString(i));
	    		}
	    		T6_preferences_ComboBox.setValue("0");
	    	}
	    	else {
	        	//else restrict the options where applicable.
	    		
	    		//setting up useful variables
	            //getting start year and end year of specified dataset/type/country/
	            Pair<String,String> mateValidRange = DatasetHandler.getValidRange(T6_mate_type_ComboBox.getValue(),T6_mate_country_ComboBox.getValue());
	            int start = Integer.parseInt(mateValidRange.getKey());
	            int end = Integer.parseInt(mateValidRange.getValue());
	            int userYOB = Integer.parseInt(T6_user_yob_ComboBox.getValue());
	            int prefMin = Math.max(-5,start -userYOB); //either -5 or the earliest valid Mate data set
	            int prefMax = Math.min(5,end -userYOB); //either 5 or the latest valid Mate data set
	            for (int i = prefMin; i <= prefMax;++i) {
	            	preferenceOptions.add(Integer.toString(i));
	            }
		        T6_preferences_ComboBox.setValue(Integer.toString((prefMin+prefMax)/2));//set to value at the middle of range
	    	}
        }else {
            //if T6_mate_country_ComboBox has not be set then do not offer options
        	T6_preferences_ComboBox.setValue("");
        }
        //update lists
        T6_preferences_ComboBox.setItems(preferenceOptions);
    }
    //actual execution
    @FXML
    void compatibilityScore() {

    }

}
