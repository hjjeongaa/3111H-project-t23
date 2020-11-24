package comp3111.popnames.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
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

    //score elements
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
    private Arc T6_ld_Arc;

    @FXML
    private Text T6_ld_score_Text;
    
    @FXML
    private Arc T6_composite_Arc;

    @FXML
    private Text T6_composite_score_Text;

    //error elements
    @FXML
    private Text T6_user_name_error_Text;

    @FXML
    private Text T6_user_type_error_Text;

    @FXML
    private Text T6_country_type_error_Text;

    @FXML
    private Text T6_user_yob_error_Text;

    @FXML
    private Text T6_pref_range_error_Text;

    @FXML
    private Text T6_mate_name_error_Text;

    @FXML
    private Text T6_mate_type_error_Text;

    @FXML
    private Text T6_mate_country_error_Text;

    @FXML
    private Text T6_pref_null_error_Text;
 
    //infoboxs
    @FXML
    private TextArea T6_infobox_TextArea;
    
    @FXML
    private TextArea T6_composite_infobox_TextArea;

    @FXML
    private TextArea T6_parm_infobox_TextArea;

    @FXML
    private TextArea T6_pasrm_infobox_TextArea;

    @FXML
    private TextArea T6_ld_infobox_TextArea;
    //UI components
    public void initialize(){
    	//hide error message(s)
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
        T6_ranking_resolver_ComboBox.setValue("ld");
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
        T6_pref_range_error_Text.setVisible(false);
        ObservableList<String> preferenceOptions = FXCollections.observableArrayList();
        if (!T6_mate_country_ComboBox.getValue().isBlank()) {
        	//if T6_mate_country_ComboBox has been set then we can update the preference options
	        if (T6_user_yob_ComboBox.getValue()==null) {
	        	// if YOB is not set then give the users the full range of options from -5 to +5
	            //getting start year and end year of specified data set/type/country/
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
	            int defaultPref = (prefMin+prefMax)/2;
	          //set to value at the middle of range if -5<=defaultPref<=5 else sett it to "";
                if (defaultPref>=-5&&defaultPref<=5){
                    T6_preferences_ComboBox.setValue(Integer.toString(defaultPref));
                }else{
                    //display range error message
                    T6_pref_range_error_Text.setVisible(true);
                    //get user YOB ranges
    	            Pair<String,String> userValidRange = DatasetHandler.getValidRange(T6_user_type_ComboBox.getValue(),T6_user_country_ComboBox.getValue());
    	            int ustart = Integer.parseInt(userValidRange.getKey());
    	            int uend = Integer.parseInt(userValidRange.getValue());
    	            int validStart = Math.max(ustart, start-5);
    	            int validEnd = Math.min(uend, end+5);
    	            //update message
                    T6_pref_range_error_Text.setText(String.format("Your YOB must be between %s and %s for you to be able to have a preference", validStart,validEnd));
                    T6_preferences_ComboBox.setValue("");
                }
	    	}
        }else {
            //if T6_mate_country_ComboBox has not be set then do not offer options
        	T6_preferences_ComboBox.setValue("");
        }
        //update lists
        T6_preferences_ComboBox.setItems(preferenceOptions);
    }

    //Validation
    boolean validateUserInputs(String iName, String iGender, String yob, String country, String type){
        //At this point we assume that all input fields are either empty strings "" or valid. due tot he way the UI is set up

        //validation of input
        boolean valid = true;
        if(iName.isBlank()){
            //if iName is empty
            valid = false;
            T6_user_name_error_Text.setVisible(true);
        }else{
            //hide error message
            T6_user_name_error_Text.setVisible(false);
        }
        if(iGender.isBlank()){
            //if iGender is empty
            valid = false;
            System.out.println("Gender error");
        }
        if(yob.isBlank()){
            //if yob is empty
            valid = false;
            T6_user_yob_error_Text.setVisible(true);
        }else{//else assume YOB valid
            T6_user_yob_error_Text.setVisible(false);
        }
        if(country.isBlank()){
            //if country is empty
            valid = false;
            T6_country_type_error_Text.setVisible(true);
        }else{
            //hide error message
            T6_country_type_error_Text.setVisible(false);
        }
        if(type.isBlank()){
            //if type is empty
            valid = false;
            T6_user_type_error_Text.setVisible(true);
        }else{
            //hide error message
            T6_user_type_error_Text.setVisible(false);
        }
        return valid;
    }

    boolean validateMateInputs(String iNameMate, String iGenderMate, String preference, String countryMate, String typeMate){
        //At this point we assume that all input fields are either empty strings "" or valid. due tot he way the UI is set up

        //validate mate
        boolean valid = true;
        if(iNameMate.isBlank()){
            //if iNameMate is empty
            valid = false;
            System.out.println(iNameMate);
            T6_mate_name_error_Text.setVisible(true);
        }else{
            //hide error message
            T6_mate_name_error_Text.setVisible(false);
        }
        if(iGenderMate.isBlank()){
            //if iGenderMate is empty
            valid = false;
            System.out.println("Gender error");
        }
        if(preference.isBlank()){
            //if iNameMate is empty
            valid = false;
            T6_pref_null_error_Text.setVisible(true);
        }else{
            //hide error message
            T6_pref_null_error_Text.setVisible(false);
        }
        if(countryMate.isBlank()){
            //if countryMate is empty
            valid = false;
            System.out.println(countryMate);
            T6_mate_country_error_Text.setVisible(true);
        }else{
            //hide error message
            T6_mate_country_error_Text.setVisible(false);
        }
        if(typeMate.isBlank()){
            //if typeMate is empty
            valid = false;
            System.out.println(typeMate);
            T6_mate_type_error_Text.setVisible(true);
        }else{
            //hide error message
            T6_mate_type_error_Text.setVisible(false);
        }
        return valid;
    }
    private boolean validateRankingInputs(String rankingAlgo, String resolver){
        //At this point we assume that all input fields are either empty strings "" or valid. due tot he way the UI is set up
        //double check if rankingAlgo and resolver is from the supported options.
        boolean valid = true;
        // valid = false;
    	return valid;
    }
    //actual execution
    @FXML
    void compatibilityScore() {
    	//getting relevant variables
        //user variables
        String iName = T6_user_name_TextField.getText();
        String iGender = (T6_user_male_RadioButton.isSelected())?"M":"F";
        String yob = T6_user_yob_ComboBox.getValue();
        int iYOB;
        String type = T6_user_type_ComboBox.getValue();
        String country = T6_user_country_ComboBox.getValue();
        //mate variables
        String iNameMate = T6_mate_name_TextField.getText();
        String iGenderMate = (T6_mate_male_RadioButton.isSelected())?"M":"F";
        String preference = T6_preferences_ComboBox.getValue();
        int iPreference;
        String countryMate = T6_mate_country_ComboBox.getValue();
        String typeMate = T6_mate_type_ComboBox.getValue();
        //rank settings
        String rankingAlgo = T6_ranking_algorithm_ComboBox.getValue();
        String resolver = T6_ranking_resolver_ComboBox.getValue();

        if (validateUserInputs(iName, iGender, yob, country, type) &&
            validateMateInputs(iNameMate, iGenderMate, preference, countryMate, typeMate) &&
            validateRankingInputs(rankingAlgo, resolver)){
            //if the inputs are valid then proceed
            iPreference = Integer.parseInt(preference);
            iYOB = Integer.parseInt(T6_user_yob_ComboBox.getValue());
            //initializing and generating report
            PredictCompatibilityScore report = new PredictCompatibilityScore(iName, iGender, iYOB, country, type , iNameMate, iGenderMate, iPreference, countryMate, typeMate, rankingAlgo, resolver);
            //update relevant variable
            HashMap<String,Double> oScores = report.getoScore();
            //update graphics
            updateGraphics(oScores);
        }
    }

    //Helper functions
    private double toDegree(double oScore){
    	//rounding performed here
        return Math.round((double)oScore*360*100)/100;
    }
    /**
     * Updates the score graphics and presents the results of the predictions.
     * @param oScores
     */
    private void updateGraphics(HashMap<String,Double> oScores){
        double composite = toDegree(oScores.get("composite"));
        double parm = toDegree(oScores.get("parm"));
        double pasrm = toDegree(oScores.get("pasrm"));
        double ld = toDegree(oScores.get("ld"));

        //update composite graphic
        T6_composite_Arc.setLength(composite);
        T6_composite_score_Text.setText(Double.toString(composite));
        //update parm graphic
        T6_parm_Arc.setLength(parm);
        T6_parm_score_Text.setText(Double.toString(parm));
        //update pasrm graphic
        T6_pasrm_Arc.setLength(pasrm);
        T6_pasrm_score_Text.setText(Double.toString(pasrm));
        //update ld graphic
        T6_ld_Arc.setLength(ld);
        T6_ld_score_Text.setText(Double.toString(ld));


    }
    
    //infoBox
    @FXML 
    void showInfoBox(){
    	T6_infobox_TextArea.setVisible(true);
    }
    @FXML 
    void hideInfoBox(){
    	T6_infobox_TextArea.setVisible(false);
    }


    @FXML
    void showCompositeInfoBox(){
        T6_composite_infobox_TextArea.setVisible(true);
    }
    @FXML
    void hideCompositeInfoBox(){
        T6_composite_infobox_TextArea.setVisible(false);
    }
    @FXML
    void showPARMInfoBox(){
        T6_parm_infobox_TextArea.setVisible(true);
    }
    @FXML
    void hidePARMInfoBox(){
        T6_parm_infobox_TextArea.setVisible(false);
    }
    @FXML
    void showPASRMInfoBox(){
        T6_pasrm_infobox_TextArea.setVisible(true);
    }
    @FXML
    void hidePASRMInfoBox(){
        T6_pasrm_infobox_TextArea.setVisible(false);
    }
    @FXML
    void showLDInfoBox(){
        T6_ld_infobox_TextArea.setVisible(true);
    }
    @FXML
    void hideLDInfoBox(){
        T6_ld_infobox_TextArea.setVisible(false);
    }
}
