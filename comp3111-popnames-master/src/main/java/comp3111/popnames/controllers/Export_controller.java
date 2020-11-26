/**
 * Export_controller
 * UI controller for exporting reports, and showing a TableView of all generated reports so far.
 * @author Hyun Joon Jeong
 */

package comp3111.popnames.controllers;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import comp3111.export.*;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Export_controller {
	//ReportHolder has to be defined separately because it is uesd within ReportHistory as well.
	@FXML
    private TableView<ReportHolder> Export_tableView;

    @FXML
    private TableColumn<ReportHolder, Boolean> Export_tableView_selected_tableColumn;

    @FXML
    private TableColumn<ReportHolder, String> Export_tableView_time_tableColumn;

    @FXML
    private TableColumn<ReportHolder, String> Export_tableView_task_tableColumn;

    @FXML
    private TableColumn<ReportHolder, String> Export_tableView_summary_tableColumn;

    @FXML
    private Button Export_selectAll_button;

    @FXML
    private Button Export_selectNone_button;

    @FXML
    private Button Export_invertSelection_button;

    @FXML
    private Button Export_exportSelected_button;
    
    @FXML
    private Label Export_nothingSelectedError_label;
    
    /**
     * initialize: called once when the UI is initialized
     * Bind the table view's columns to the data model, and link tableViewList with the table view.
     */
    @FXML
    void initialize() {
    	Export_tableView_selected_tableColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
    	Export_tableView_selected_tableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(Export_tableView_selected_tableColumn));
    	
    	Export_tableView_time_tableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    	Export_tableView_task_tableColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
    	Export_tableView_summary_tableColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
    	
    	Export_tableView.setItems(ReportHistory.getReportHoldersList());
    	Export_tableView.setPlaceholder(new Label("You haven't generated any reports yet."));
    }
    /**
     * Mark all items in tableview as selected
     */
    @FXML
    void Export_selectAllButtonPressed() {
    	ReportHistory.selectAll();
    }
    /**
     * Mark all items in tableview as unseleced
     */
    @FXML
    void Export_selectNoneButtonPressed() {
    	ReportHistory.selectNone();
    }
    /**
     * Invert selection of all items in tableview
     */
    @FXML
    void Export_invertSelectionButtonPressed() {
    	ReportHistory.invertSelection();
    }
    /**
     * Ask the user for a location to save a PDF file to, and call ReportHistory to export all selected reports as a PDF.
     * Also handles cases where the location is invalid, in which case an appropriate error message will appear.
     */
    @FXML
    void Export_exportSelectedButtonPressed() {
    	String initialFileName = ReportHistory.getLatestSelectedReportDate();
    	if (!initialFileName.isBlank()) {
    		Export_nothingSelectedError_label.setText("");
    		FileChooser fc = new FileChooser();
        	fc.setTitle("Save PDF to where?");
        	fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files", "*.pdf"));
        	fc.setInitialFileName("Report_"+initialFileName.replace(":", "")+".pdf");
        	File outputFile = fc.showSaveDialog(Export_exportSelected_button.getScene().getWindow());
        	
        	try {
				ReportHistory.exportSelected(outputFile);
			} catch (FileNotFoundException e) {
				Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error");
	    		alert.setHeaderText("I/O Error");
	    		alert.setContentText("An unexpected error occurred while writing to the specified file path.");
	    		alert.showAndWait();
				e.printStackTrace();
				
				e.printStackTrace();
			} catch (IOException e) {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error");
	    		alert.setHeaderText("I/O Error");
	    		alert.setContentText("An unexpected error occurred while writing to the specified file path.");
	    		alert.showAndWait();
				e.printStackTrace();
			}
    	} else {
    		Export_nothingSelectedError_label.setText("Nothing selected.");
    	}
    }
    
}
