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
 * v2.0
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

    //combobox variables
    ObservableList<String> userTypesList;
    ObservableList<String> userCountriesList;
    ObservableList<String> userYOBList;

    ObservableList<String> mateTypesList;
    ObservableList<String> mateCountries;
    ObservableList<String> preferenceList;

    ObservableList<String> rankingAlgorithmsList;
    ObservableList<String> rankResolversList;
    /**
     * Initializes relevant UI components on system load.
     * @author Yuxi Sun
     */
    public void initialize(){
    	//hide error message(s)
        //User initializers
        //initialize and link lists
        userTypesList = FXCollections.observableArrayList(DatasetHandler.getTypes());
        T6_user_type_ComboBox.setItems(userTypesList);
        userCountriesList = FXCollections.observableArrayList();
        T6_user_country_ComboBox.setItems(userCountriesList);
        userYOBList = FXCollections.observableArrayList();
        
        T6_user_yob_ComboBox.setItems(userYOBList);
        //set user type options
        T6_user_type_ComboBox.setValue("human");
        selectUserType();

        //set use country options based on the default of usa
        T6_user_country_ComboBox.setValue("usa");
        selectUserCountry();
        //update YOB list according to default
        T6_user_yob_ComboBox.setValue("1941");

        //Mate initializers
        //initialize and link lists
        mateTypesList = FXCollections.observableArrayList(DatasetHandler.getTypes());
        T6_mate_type_ComboBox.setItems(mateTypesList);
        T6_mate_type_ComboBox.setValue("human");
        
        mateCountries = FXCollections.observableArrayList();
        T6_mate_country_ComboBox.setItems(mateCountries);
        preferenceList = FXCollections.observableArrayList();
        
        //set country options based on the default of usa
        selectMateType();
        T6_mate_country_ComboBox.setValue("usa");

        //update preference options
        preferenceList = FXCollections.observableArrayList();
        T6_preferences_ComboBox.setItems(preferenceList);
        updatePreferences();
        
        //initialize ranking settings
        //initialize ranking Algorithm
        rankingAlgorithmsList = FXCollections.observableArrayList(RankingAlgorithmFactory.getNonIterativeMethods());
        T6_ranking_algorithm_ComboBox.setItems(rankingAlgorithmsList);
        T6_ranking_algorithm_ComboBox.setValue("scr");
        //initialize rank Resolver
        rankResolversList = FXCollections.observableArrayList(rankResolver.getResolutionMethods());
        T6_ranking_resolver_ComboBox.setItems(rankResolversList);
        T6_ranking_resolver_ComboBox.setValue("ld");
    }
    //User UI components
    @FXML
    /**
     * Update country list on change of user type
     */
    private void selectUserType() {
        hideAllErrors();
        userCountriesList.clear();
        for (String country: DatasetHandler.getCountries(T6_user_type_ComboBox.getValue())){
            userCountriesList.add(country);
        }
        //clearing and resetting userCountry and userYOB;
        T6_user_country_ComboBox.setValue("");
        T6_user_yob_ComboBox.setValue("");
    }
    
    @FXML
    /**
     * Update userYOB on change of user country
     */
    private void selectUserCountry() {
        hideAllErrors();
    	//Initializing empty list
        userYOBList.clear();
        //getting start year and end year of specified dataset/type/country/
        String type = T6_user_type_ComboBox.getValue();
        String country = T6_user_country_ComboBox.getValue();
        if (!isComboBoxEmpty(type) && !isComboBoxEmpty(country)){
            //only runs this if both start year and end year are valid.
            Pair<String,String> userValidRange = DatasetHandler.getValidRange(type, country);
            int start = Integer.parseInt(userValidRange.getKey());
            int end = Integer.parseInt(userValidRange.getValue());
            for(int i = start; i <= end; ++i){
                //appending all valid years to the list to be displayed in 
                userYOBList.add(Integer.toString(i));
            }
            T6_user_yob_ComboBox.setValue(Integer.toString((start+end)/2));//setting the default YOB to the median year of said range.
        }
    }
    
    @FXML
    /**
     * Update preferences
     */
    private void selectUserYOB() {
        //update preference list
        updatePreferences();
    }
    //Mate UI components
    @FXML
        /**
     * Update country list on change of mate type
     */
    private void selectMateType() {
        hideAllErrors();
        mateCountries.clear();
        for (String country: DatasetHandler.getCountries(T6_mate_type_ComboBox.getValue()))
            mateCountries.add(country);
        //clearing and resetting mateCountry and preference;
        T6_mate_country_ComboBox.setValue("");
        updatePreferences();
    }
    @FXML
    private void selectMateCountry() {
        hideAllErrors();
    	updatePreferences();
    }
    @FXML
    private void updatePreferences() {
//    	T6_pref_error_Text.setVisible(false);
        // clear all errors();
        hideAllErrors();
        preferenceList.clear();
        T6_preferences_ComboBox.setValue("");
    	//list of preferences [-5,5];
        ObservableList<String> preferenceOptions = FXCollections.observableArrayList();
            
        if (validateUpdatePreference()){
            //if all inputs are valid then update everything
            //setting up useful variables
            //getting start year and end year of specified dataset/type/country/
            Pair<String,String> mateValidRange = DatasetHandler.getValidRange(T6_mate_type_ComboBox.getValue(),T6_mate_country_ComboBox.getValue());
            int start = Integer.parseInt(mateValidRange.getKey());
            int end = Integer.parseInt(mateValidRange.getValue());
            int userYob = Integer.parseInt(T6_user_yob_ComboBox.getValue());
            int prefMin = Math.max(-5,start -userYob); //either -5 or the earliest valid Mate data set
            int prefMax = Math.min(5,end -userYob); //either 5 or the latest valid Mate data set
            for (int i = prefMin; i <= prefMax;++i) {
                preferenceList.add(Integer.toString(i));
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
        }else{
            //else restrict the options where applicable.
            if (!T6_mate_country_ComboBox.getValue().isBlank()|| T6_mate_country_ComboBox.getValue()!= null) {
                //if T6_mate_country_ComboBox has been set then we can update the preference options
                if (T6_user_yob_ComboBox.getValue()==null) {
                    // if YOB is not set then give the users the full range of options from -5 to +5
                    //getting start year and end year of specified data set/type/country/
                    for (int i = -5; i <= 5; ++i) {
                        preferenceOptions.add(Integer.toString(i));
                    }
                    T6_preferences_ComboBox.setValue("0");
                }
            }
        }
    }
    
    private boolean validateUpdatePreference() {
        //hide all previous error messages
        //load users variables
        String iName = T6_user_name_TextField.getText();
        String yob = T6_user_yob_ComboBox.getValue();
        String type = T6_user_type_ComboBox.getValue();
        String country = T6_user_country_ComboBox.getValue();
        
        //At this point we assume that all input fields are either empty strings "" or valid. due tot he way the UI is set up
        //validation of input
        boolean valid = true;
        if(isComboBoxEmpty(iName)){
            //if iName is empty
            valid = false;
        }
        if(isComboBoxEmpty(yob)){
            //if yob is empty
            valid = false;
        }
        if(isComboBoxEmpty(country)){
            //if country is empty
            valid = false;
        }
//      //Will not occur with current UI setup
//        if(isComboBoxEmpty(type)){
//            //if type is empty
//            valid = false;
//        }
        //load all variables
        String iNameMate = T6_mate_name_TextField.getText();
        String countryMate = T6_mate_country_ComboBox.getValue();
        String typeMate = T6_mate_type_ComboBox.getValue();
        //At this point we assume that all input fields are either empty strings "" or valid. due to the way the UI is set up
        //validate mate
        if(isComboBoxEmpty(iNameMate)){
            //if iNameMate is empty
            valid = false;
        }
        if(isComboBoxEmpty(countryMate)){
            //if countryMate is empty
            valid = false;
//            T6_mate_country_error_Text.setVisible(true);
        }
//      //Will not occur with current UI setup
//        if(isComboBoxEmpty(typeMate)){
//            //if typeMate is empty
//            valid = false;
////            T6_mate_type_error_Text.setVisible(true);
//        }
        return valid;
    	
    }
    private void hideAllErrors(){
        //user error messages
        T6_user_name_error_Text.setVisible(false);
        T6_country_type_error_Text.setVisible(false);
        T6_user_type_error_Text.setVisible(false);
        T6_user_yob_error_Text.setVisible(false);
        //mate error messages
        T6_mate_name_error_Text.setVisible(false);
        T6_pref_null_error_Text.setVisible(false);
        T6_mate_country_error_Text.setVisible(false);
        T6_mate_type_error_Text.setVisible(false);
    }
    //Validation
    boolean validateUserInputs(){
        //load all variables
        String iName = T6_user_name_TextField.getText();
        String iGender = (T6_user_male_RadioButton.isSelected())?"M":"F";
        String yob = T6_user_yob_ComboBox.getValue();
        String type = T6_user_type_ComboBox.getValue();
        String country = T6_user_country_ComboBox.getValue();
        
        //At this point we assume that all input fields are either empty strings "" or valid. due tot he way the UI is set up
        //validation of input
        boolean valid = true;
        if(isComboBoxEmpty(iName)){
            //if iName is empty
            valid = false;
            T6_user_name_error_Text.setVisible(true);
        }
//      //Will not occur with current UI setup
//        if(isComboBoxEmpty(iGender)){
//            //if iGender is empty or null (should never happen unless UI fails)
//            valid = false;
//            System.out.println("Gender error");
//        }
        if(isComboBoxEmpty(yob)){
            //if yob is empty
            valid = false;
            T6_user_yob_error_Text.setVisible(true);
        }
        if(isComboBoxEmpty(country)){
            //if country is empty
            valid = false;
            T6_country_type_error_Text.setVisible(true);
        }
//      //Will not occur with current UI setup
//        if(isComboBoxEmpty(type)){
//            //if type is empty
//            valid = false;
//            T6_user_type_error_Text.setVisible(true);
//        }
        return valid;
    }

    boolean validateMateInputs(){
        //load all variables
        String iNameMate = T6_mate_name_TextField.getText();
        String iGenderMate = (T6_mate_male_RadioButton.isSelected())?"M":"F";
        String preference = T6_preferences_ComboBox.getValue();
        String countryMate = T6_mate_country_ComboBox.getValue();
        String typeMate = T6_mate_type_ComboBox.getValue();
        //At this point we assume that all input fields are either empty strings "" or valid. due tot he way the UI is set up
        //validate mate
        boolean valid = true;
        if(isComboBoxEmpty(iNameMate)){
            //if iNameMate is empty
            valid = false;
            T6_mate_name_error_Text.setVisible(true);
        }
//        //Will not occur with current UI setup
//        if(isComboBoxEmpty(iGenderMate)){
//            //if iGenderMate is empty
//            valid = false;
//            System.out.println("Gender error");
//        }
        if(isComboBoxEmpty(preference)){
            //if iNameMate is empty
            valid = false;
            T6_pref_null_error_Text.setVisible(true);
        }
        if(isComboBoxEmpty(countryMate)){
            //if countryMate is empty
            valid = false;
            T6_mate_country_error_Text.setVisible(true);
        }
//      //Will not occur with current UI setup
//        if(isComboBoxEmpty(typeMate)){
//            //if typeMate is empty
//            valid = false;
//            T6_mate_type_error_Text.setVisible(true);
//        }
        return valid;
    }
    
    
//    private boolean validateRankingInputs(){
//        //At this point we assume that all input fields are either empty strings "" or valid. due to the way the UI is set up
//        //double check if rankingAlgo and resolver is from the supported options.
//        boolean valid = true;
//        // valid = false;
//    	return valid;
//    }
    //actual execution
    @FXML
    void compatibilityScore() {
        //hide all previous error messages
        hideAllErrors();
        boolean vMate = validateUserInputs();
        boolean vUser = validateMateInputs();
        if (vMate && vUser){
        	//if the inputs are valid then proceed
        	//getting relevant variables
            //user variables
            String iName = T6_user_name_TextField.getText();
            String iGender = (T6_user_male_RadioButton.isSelected())?"M":"F";
            int iYOB = Integer.parseInt(T6_user_yob_ComboBox.getValue());
            String type = T6_user_type_ComboBox.getValue();
            String country = T6_user_country_ComboBox.getValue();
            //mate variables
            String iNameMate = T6_mate_name_TextField.getText();
            String iGenderMate = (T6_mate_male_RadioButton.isSelected())?"M":"F";
            int iPreference = Integer.parseInt(T6_preferences_ComboBox.getValue());
            String countryMate = T6_mate_country_ComboBox.getValue();
            String typeMate = T6_mate_type_ComboBox.getValue();
            //rank settings
            String rankingAlgo = T6_ranking_algorithm_ComboBox.getValue();
            String resolver = T6_ranking_resolver_ComboBox.getValue();
            
            //initializing and generating report
            PredictCompatibilityScore report = new PredictCompatibilityScore(iName, iGender, iYOB, country, type , iNameMate, iGenderMate, iPreference, countryMate, typeMate, rankingAlgo, resolver);
            //update relevant variable
            HashMap<String,Double> oScores = report.getoScore();
            //update graphics
            updateGraphics(oScores);
        }
    }

    //Helper functions
    /**
     * checks if a ComboBox is empty
     */
    private boolean isComboBoxEmpty(String entry){
        return entry == null || entry.isBlank();
    }
    /**
     *  Converts a [0,1] score to a [0,360] score
     * @param oScore double between [0,1]
     * @return double between [0,0,360]
     */
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
    /**
     * Show the feature Information Box
     */
    void showInfoBox(){
    	T6_infobox_TextArea.setVisible(true);
    }
    @FXML 
    /**
     * Hide the feature Information Box
     */
    void hideInfoBox(){
    	T6_infobox_TextArea.setVisible(false);
    }


    @FXML
    /**
     * Hide the composite Information Box
     */
    void showCompositeInfoBox(){
        T6_composite_infobox_TextArea.setVisible(true);
    }
    @FXML
    /**
     * Hide the composite Information Box
     */
    void hideCompositeInfoBox(){
        T6_composite_infobox_TextArea.setVisible(false);
    }
    @FXML
    /**
     * Show the PARM Information Box
     */
    void showPARMInfoBox(){
        T6_parm_infobox_TextArea.setVisible(true);
    }
    @FXML
    /**
     * Hide the PARM Information Box
     */
    void hidePARMInfoBox(){
        T6_parm_infobox_TextArea.setVisible(false);
    }
    @FXML
    /**
     * Show the PASRM Information Box
     */
    void showPASRMInfoBox(){
        T6_pasrm_infobox_TextArea.setVisible(true);
    }
    @FXML
    /**
     * Hide the PASRM Information Box
     */
    void hidePASRMInfoBox(){
        T6_pasrm_infobox_TextArea.setVisible(false);
    }
    @FXML
    /**
     * Show the LD Information Box
     */
    void showLDInfoBox(){
        T6_ld_infobox_TextArea.setVisible(true);
    }
    @FXML
    /**
     * Hide the LD Information Box
     */
    void hideLDInfoBox(){
        T6_ld_infobox_TextArea.setVisible(false);
    }

}
