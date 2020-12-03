package comp3111.popnames.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import comp3111.popnames.GlobalSettings;
import comp3111.popnames.JourneyThroughTime;
import comp3111.popnames.SoulmateName;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SoulmateName_controller {
	/* Stuff for tables and buttons */
	private ObservableList<SoulmateDataModel> nkList;
	private ObservableList<SoulmateDataModel> closestNameList;
	private ObservableList<SoulmateDataModel> pycList;
	private ObservableList<SoulmateDataModel> chanceList;
	
	private ObservableList<SoulmateDataModel> JTTList;
	
	//Data model for each row of the TableView; each row has two columns, rank and name.
	public class SoulmateDataModel {
		private final SimpleStringProperty name;
		
		public SoulmateDataModel(String Name) {
			this.name = new SimpleStringProperty(Name);
		}
		//Each table column will use this method to retrieve the values of each cell.
		public String getName() {return this.name.get();}
	}
	
	public class JTTDataModel {
		private final SimpleStringProperty name;
		
		public JTTDataModel(String Name) {
			this.name = new SimpleStringProperty(Name);
		}
		//Each table column will use this method to retrieve the values of each cell.
		public String getName() {return this.name.get();}
	}
	
	/* Elements */
	
	@FXML
    private StackPane Soulmate_host_StackPane;
	
	@FXML
    private AnchorPane Soulmate_vanilla_AnchorPane;
	
	@FXML
    private RadioButton Soulmate_inputIsMale_RadioButton;

    @FXML
    private ToggleGroup Soulmate_yourGender;

    @FXML
    private RadioButton Soulmate_inputisFemale_RadioButton;

    @FXML
    private RadioButton Soulmate_preferenceIsMale_Button;

    @FXML
    private ToggleGroup Soulmate_preference;

    @FXML
    private RadioButton Soulmate_preferenceIsFemale_Button;

    @FXML
    private RadioButton Soulmate_isYounger_Button;

    @FXML
    private ToggleGroup Soulmate_agepref;

    @FXML
    private RadioButton Soulmate_isSame_Button;

    @FXML
    private RadioButton Soulmate_isOlder_Button;

    @FXML
    private TextField Soulmate_inputName_TextField;

    @FXML
    private TextField Soulmate_YOB_TextField;

    @FXML
    private Label Soulmate_errorName_Label;

    @FXML
    private Label Soulmate_errorYear_Label;

    @FXML
    private Button Soulmate_findSoulmate_Button;

    @FXML
    private Button Soulmate_JTT_Button;
    
    @FXML
    private Label Soulmate_JTTMessage_Label;

    @FXML
    private TableView<SoulmateDataModel> Soulmate_JTTNameSelection_TableView;

    @FXML
    private TableColumn<SoulmateDataModel, String> Soulmate_JTTNameCol_TableColumn;

    @FXML
    private ComboBox<String> Soulmate_nkAlgo_ComboBox;

    @FXML
    private TableView<SoulmateDataModel> Soulmate_NKout_TableView;

    @FXML
    private TableColumn<SoulmateDataModel, String> Soulmate_NKcol_TableColumn;

    @FXML
    private TableView<SoulmateDataModel> Soulmate_closestName_TableView;

    @FXML
    private TableColumn<SoulmateDataModel, String> Soulmate_closestNamecol_TableColumn;

    @FXML
    private TableView<SoulmateDataModel> Soulmate_pyc_TableView;

    @FXML
    private TableColumn<SoulmateDataModel, String> Soulmate_pycCol_TableColumn;

    @FXML
    private TableView<SoulmateDataModel> Soulmate_chance_TableView;

    @FXML
    private TableColumn<SoulmateDataModel, String> Soulmate_chanceCol_TableColumn;
    
    @FXML
    void initialize() {
    	//Link each table column with the correct variable inside the data model for the tableview.
    	Soulmate_NKcol_TableColumn.setCellValueFactory(new PropertyValueFactory<SoulmateDataModel,String>("name"));
    	Soulmate_closestNamecol_TableColumn.setCellValueFactory(new PropertyValueFactory<SoulmateDataModel,String>("name"));
    	Soulmate_pycCol_TableColumn.setCellValueFactory(new PropertyValueFactory<SoulmateDataModel,String>("name"));
    	Soulmate_chanceCol_TableColumn.setCellValueFactory(new PropertyValueFactory<SoulmateDataModel,String>("name"));
    	Soulmate_JTTNameCol_TableColumn.setCellValueFactory(new PropertyValueFactory<SoulmateDataModel,String>("name"));
    	
    	//Create an array of data models and link this array with the table view.
    	this.nkList = FXCollections.<SoulmateDataModel>observableArrayList();
    	this.closestNameList = FXCollections.<SoulmateDataModel>observableArrayList();
    	this.pycList = FXCollections.<SoulmateDataModel>observableArrayList();
    	this.chanceList = FXCollections.<SoulmateDataModel>observableArrayList();
    	this.JTTList = FXCollections.<SoulmateDataModel>observableArrayList();
    	
    	Soulmate_NKout_TableView.setItems(this.nkList);
    	Soulmate_closestName_TableView.setItems(this.closestNameList);
    	Soulmate_pyc_TableView.setItems(this.pycList);
    	Soulmate_chance_TableView.setItems(this.chanceList);
    	Soulmate_JTTNameSelection_TableView.setItems(this.JTTList);
    	
    	Soulmate_NKout_TableView.setSelectionModel(null);
    	Soulmate_closestName_TableView.setSelectionModel(null);
    	Soulmate_pyc_TableView.setSelectionModel(null);
    	Soulmate_chance_TableView.setSelectionModel(null);
    	
    	// Dealing with the ComboBox
    	String algos[] = {"Ordinal", "Standard Competition", "Dense", "Modified Competition"};
    	ObservableList<String> algoTypes = FXCollections.observableArrayList(algos);
    	Soulmate_nkAlgo_ComboBox.setItems(algoTypes);
    	Soulmate_nkAlgo_ComboBox.setValue("Ordinal");
    }
    
    private void clearScreen() {
    	nkList.clear();
    	closestNameList.clear();
    	pycList.clear();
    	chanceList.clear();
    	JTTList.clear();
    	
    	Soulmate_errorName_Label.setVisible(false);
    	Soulmate_errorYear_Label.setVisible(false);
    	
    	Soulmate_JTTMessage_Label.setText("Select a name from below when available, and press 'Journey Through Time' for a brief history of your two names.");
    	Soulmate_JTTMessage_Label.setTextFill(Color.BLACK);
    	Soulmate_JTT_Button.setDisable(true);
    }
    
    private int getCleanedYear(int lowerBound, int upperBound) {
    	int res = -1;
    	try {
    		res = Integer.parseInt(Soulmate_YOB_TextField.getText());
    	} catch(NumberFormatException e) {
    		// the year is not a valid integer.
    		Soulmate_errorYear_Label.setVisible(true);
    	}
    	if(res < lowerBound || res > upperBound ) {
    		Soulmate_errorYear_Label.setText(String.format("Valid Years: %d-%d", lowerBound, upperBound));
    		Soulmate_errorYear_Label.setVisible(true);
    		res = -1;
    	} 
    	return res;
    }
    
    private String getCleanedName() {
    	String res = Soulmate_inputName_TextField.getText();
    	if( res.length() == 0) Soulmate_errorName_Label.setVisible(true);
    	return res;
    }
    
    @FXML
    void findSoulmate() {
    	clearScreen();
    	/* Get Variables */
    	String name = getCleanedName();
    	int YOB = getCleanedYear(GlobalSettings.getLowerBound(), GlobalSettings.getUpperBound());
    	if(YOB == -1 || name.length() == 0) return;
    	String inputGender = (Soulmate_inputIsMale_RadioButton.isSelected())?"M":"F";
    	String preferenceGender = (Soulmate_preferenceIsMale_Button.isSelected())?"M":"F";
    	int agePreference;
    	if(Soulmate_isYounger_Button.isSelected()) agePreference = -1;
    	else if(Soulmate_isSame_Button.isSelected()) agePreference = 0;
    	else agePreference = 1;
    	
    	HashMap<String, String> algoMap = new HashMap<String, String>();
    	algoMap.put("Standard Competition", "scr");
    	algoMap.put("Ordinal", "or");
    	algoMap.put("Dense", "dr");
    	algoMap.put("Modified Competition", "mcr");
    	String nkAlgo = algoMap.get(Soulmate_nkAlgo_ComboBox.getValue());
    	
    	// Now that all inputs are good, time to calculate and render.
    	SoulmateName source = new SoulmateName(name, inputGender, YOB, preferenceGender, agePreference, nkAlgo, "usa", "human");
    	
    	// Now to populate the tables.
    	// NK-T5:
    	for( String soulmateName : source.getSoulmateNames("nkt5")){
    		SoulmateDataModel Entry = new SoulmateDataModel(soulmateName);
    		nkList.add(Entry);
    	}
    	
    	// Name Similarity:
    	for( String soulmateName : source.getSoulmateNames("ld")){
    		SoulmateDataModel Entry = new SoulmateDataModel(soulmateName);
    		closestNameList.add(Entry);
    	}
    	
    	// Probably Your Classmate:
    	for( String soulmateName : source.getSoulmateNames("pyc")){
    		SoulmateDataModel Entry = new SoulmateDataModel(soulmateName);
    		pycList.add(Entry);
    	}
    	
    	// Chance Encounter:
    	for( String soulmateName : source.getSoulmateNames("chance")){
    		SoulmateDataModel Entry = new SoulmateDataModel(soulmateName);
    		chanceList.add(Entry);
    	}
    	
    	// Writing to the JTT Table and enabling the button.
    	for(String finalName : source.getFinalNames()) {
    		SoulmateDataModel Entry = new SoulmateDataModel(finalName);
    		JTTList.add(Entry);
    	}
    	Soulmate_JTT_Button.setDisable(false);
    }
    
    private String getCleanedJTTName() {
    	if(Soulmate_JTTNameSelection_TableView.getSelectionModel().isEmpty()) {
	    	Soulmate_JTTMessage_Label.setText("Please select one of the names from below.");
	    	Soulmate_JTTMessage_Label.setTextFill(Color.RED);
	    	return "";
    	}
    	return Soulmate_JTTNameSelection_TableView.getSelectionModel().getSelectedItem().getName();
    }
    
    @FXML
    void JTT() {
    	Soulmate_JTTMessage_Label.setText("Select a name from below when available, and press 'Journey Through Time' for a brief history of your two names.");
    	Soulmate_JTTMessage_Label.setTextFill(Color.BLACK);
    	String inputName = getCleanedName();
    	String soulmateName = getCleanedJTTName();
    	if(inputName.length() == 0 || soulmateName.length() == 0) return;
    	String inputGender = (Soulmate_inputIsMale_RadioButton.isSelected())?"M":"F";
    	String soulmateGender = (Soulmate_preferenceIsMale_Button.isSelected())?"M":"F";
    	int YOB = getCleanedYear(GlobalSettings.getLowerBound(), GlobalSettings.getUpperBound());
    	if(YOB == -1) return;
    	// Now that we have the two names, we have to create the journeyThroughTime object to retrieve relevant information, and then do things here to cast that
    	// information in an aesthetic manner.
    	
    	JourneyThroughTime.setValues(inputName, soulmateName, inputGender, soulmateGender, YOB, GlobalSettings.getCountry(), "human");
    	
    	// Now to begin the transition into the next scene
    	
    	Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/interfaces/Scene1_interface.fxml"));
		} catch (IOException e) {
			System.out.println("I FAILED...");
			return;
		}
    	
    	Scene scene = Soulmate_JTT_Button.getScene();
    	
    	root.translateYProperty().set(scene.getHeight());
    	Soulmate_host_StackPane.getChildren().add(root);
    	
    	Timeline timeline = new Timeline();
    	KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
    	KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
    	timeline.getKeyFrames().add(kf);
    	timeline.setOnFinished(event->{
    		Soulmate_host_StackPane.getChildren().remove(Soulmate_vanilla_AnchorPane);
    	});
    	
    	timeline.play();
    }
}
