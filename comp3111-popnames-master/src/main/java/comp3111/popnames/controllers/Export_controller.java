package comp3111.popnames.controllers;

import comp3111.export.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;

public class Export_controller {
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
    void initialize() {
    	Export_tableView_selected_tableColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
    	Export_tableView_selected_tableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(Export_tableView_selected_tableColumn));
    	
    	Export_tableView_time_tableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    	Export_tableView_task_tableColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
    	Export_tableView_summary_tableColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
    	
    	Export_tableView.setItems(ReportHistory.getReportHoldersList());
    	Export_tableView.setPlaceholder(new Label("You haven't generated any reports yet."));
    }
    
    @FXML
    void Export_selectAllButtonPressed() {
    	ReportHistory.selectAll();
    }

    @FXML
    void Export_selectNoneButtonPressed() {
    	ReportHistory.selectNone();
    }

    @FXML
    void Export_invertSelectionButtonPressed() {
    	ReportHistory.invertSelection();
    }

    @FXML
    void Export_exportSelectedButtonPressed() {
    	ReportHistory.exportSelected();
    }
    
}
