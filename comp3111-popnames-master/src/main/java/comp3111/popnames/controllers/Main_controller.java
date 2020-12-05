/**
* Main_controller.java
* This is the main interface which acts as a root of all other interfaces. There is a panel on the left in which you can click to go to different pages. More information is on Ryder's supplementary document.
* 
* @author Ryder Khoi Daniel
* @version 1.0
*/

package comp3111.popnames.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for the host interface.
 * @author Ryder Khoi Daniel
 * v1.0
 */
public class Main_controller {
	
	/*
	 *	 ELEMENTS
	 */

	@FXML
    private Button Main_home_Button;

    @FXML
    private Button Main_dataReporting_Button;

    @FXML
    private Button Main_topNames_Button;

    @FXML
    private Button Main_namePopularity_Button;

    @FXML
    private Button Main_trendingNames_Button;

    @FXML
    private Button Main_babyNames_Button;

    @FXML
    private Button Main_soulmate_Button;

    @FXML
    private Button Main_compatibility_Button;

    @FXML
    private Button Main_export_Button;

    @FXML
    private Button Main_settings_Button;

    @FXML
    private TabPane Main_pageOutput_TabPane;

    @FXML
    private Label Main_pageName_Label;
    
    /*
     * 	METHODS
     */
    
    @FXML
    void Main_showHomePage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(0);
    	Main_pageName_Label.setText("Welcome!");
    }
    
    @FXML
    void Main_showDataReportPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(1);
    	Main_pageName_Label.setText("Data Reporting");
    }
    
    @FXML
    void Main_showTopNamesPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(2);
    	Main_pageName_Label.setText("Top Names");
    }
    
    @FXML
    void Main_showNamesPopularityPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(3);
    	Main_pageName_Label.setText("Name Popularity");
    }
    
    @FXML
    void Main_showTrendingNamesPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(4);
    	Main_pageName_Label.setText("Name Trends");
    }
    
    @FXML
    void Main_showBabyNamesPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(5);
    	Main_pageName_Label.setText("Baby Name Generator");
    }
    
    @FXML
    void Main_showSoulmateNamesPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(6);
    	Main_pageName_Label.setText("Soulmate Name");
    }
    
    @FXML
    void Main_showNamesCompatibilityPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(7);
    	Main_pageName_Label.setText("Name Compatibility Score");
    }
    
    @FXML
    void Main_showExportPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(8);
    	Main_pageName_Label.setText("Export Results");
    }
    
    @FXML
    void Main_showSettingsPage(){
    	Main_pageOutput_TabPane.getSelectionModel().select(9);
    	Main_pageName_Label.setText("Settings");
    }
}
