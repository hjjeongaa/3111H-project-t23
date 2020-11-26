/**
 * ReportHolder: A data model for the export  table view.
 * Creates properties from all the attributes of the ReportLog class,
 * and adds a 'selected' property to allow user to select which reports
 * they want to be exported.
 */

package comp3111.export;

import java.time.format.DateTimeFormatter;

import comp3111.popnames.ReportLog;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/* Sharing/Exporting */
public class ReportHolder {
	private SimpleBooleanProperty selected;
	private final SimpleStringProperty date;
	private final SimpleStringProperty task;
	private final SimpleStringProperty summary;
	private String html;
	
	public ReportHolder(ReportLog reportLogToAdd) {
		this.selected = new SimpleBooleanProperty(true);
		this.date = new SimpleStringProperty(reportLogToAdd.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		this.task = new SimpleStringProperty(reportLogToAdd.getTask());
		this.summary = new SimpleStringProperty(reportLogToAdd.getoReport());
		this.html = reportLogToAdd.getHTML();
	}
	
	public Boolean getSelected() {return this.selected.get();}
	public void setSelected(Boolean selected) {this.selected.set(selected);}
	public SimpleBooleanProperty selectedProperty() {return this.selected;}
	
	public String getDate() {return this.date.get();}
	public String getTask() {return this.task.get();}
	public String getSummary() {return this.summary.get();}
	public String getHTML() {return this.html;}
}