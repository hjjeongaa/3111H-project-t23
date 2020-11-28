package comp3111.popnames.controllers;

import comp3111.popnames.DatasetHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Settings_controller {
	
    @FXML
    private ComboBox<String> Settings_countryChoice_ComboBox;

    @FXML
    private Button Settings_applySettings_Button;

    @FXML
	void initialize() {
		ObservableList<String> countries = FXCollections.observableArrayList(DatasetHandler.getCountries("human"));
		Settings_countryChoice_ComboBox.setItems(countries);
		Settings_countryChoice_ComboBox.setValue("usa");
	}
    
    @FXML
    void updateSettings() {

    }

}
