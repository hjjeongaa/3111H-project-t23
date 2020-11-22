/**
 * ReportHolder: A data model for the export reports table view.
 * Creates properties from all the attributes of the Reports class,
 * and adds a 'selected' property to allow user to select which reports
 * they want to be exported.
 */

package comp3111.export;

import java.time.format.DateTimeFormatter;

import comp3111.popnames.PopularityOfName;
import comp3111.popnames.Reports;
import comp3111.popnames.TopNNames;
import comp3111.popnames.TrendInPopularity2;
import comp3111.popnames.RecommendBabyName;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/* Sharing/Exporting */
public class ReportHolder {
	private SimpleBooleanProperty selected;
	private final SimpleStringProperty date;
	private final SimpleStringProperty reportType;
	private final SimpleStringProperty name;
	private final SimpleStringProperty gender;
	//private final SimpleStringProperty years;
	private final SimpleStringProperty dataType;
	private final SimpleStringProperty country;
	
	public ReportHolder(Reports reportToAdd) {
		this.selected = new SimpleBooleanProperty(true);
		this.name = new SimpleStringProperty(reportToAdd.getname());
		this.date = new SimpleStringProperty(reportToAdd.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		if (reportToAdd instanceof PopularityOfName) {
			this.reportType = new SimpleStringProperty("NamePop.");
		}
		else if (reportToAdd instanceof TopNNames){
			this.reportType = new SimpleStringProperty("TopNames");
		} else if (reportToAdd instanceof TrendInPopularity2) {
			this.reportType = new SimpleStringProperty("Trend");
		} else if (reportToAdd instanceof RecommendBabyName) {
			this.reportType = new SimpleStringProperty("BabyNames");
		} else {
			this.reportType = new SimpleStringProperty("Task456");
		}
		this.gender = new SimpleStringProperty(reportToAdd.getgender());
		//this.years = new SimpleStringProperty(reportToAdd.getyears());
		this.dataType = new SimpleStringProperty(reportToAdd.gettype());
		this.country = new SimpleStringProperty(reportToAdd.getcountry());
	}
	
	public Boolean getSelected() {return this.selected.get();}
	public void setSelected(Boolean selected) {this.selected.set(selected);}
	public SimpleBooleanProperty selectedProperty() {return this.selected;}
	
	public String getDate() {return this.date.get();}
	public String getReportType() {return this.reportType.get();}
	public String getName() {return this.name.get();}
	public String getGender() {return this.gender.get();}
	//public String getYears() {return this.years.get();}
	public String getDataType() {return this.dataType.get();}
	public String getCountry() {return this.country.get();}
}