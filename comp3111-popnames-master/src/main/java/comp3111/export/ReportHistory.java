package comp3111.export;

import comp3111.popnames.ReportLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.itextpdf.html2pdf.HtmlConverter;



public class ReportHistory {
	private static final ReportHistory instance = new ReportHistory();
	private static ObservableList<ReportHolder> reportHoldersList;
	private ReportHistory() {
		reportHoldersList = FXCollections.<ReportHolder>observableArrayList();
	}
	public static ReportHistory getInstance() {
		return instance;
	}
	public static ObservableList<ReportHolder> getReportHoldersList() {
		return reportHoldersList;
	}
	public static void addReportLog(ReportLog reportLogToAdd) {
		ReportHolder reportHolderToAdd = new ReportHolder(reportLogToAdd);
		reportHoldersList.add(reportHolderToAdd);
	}
	public static void selectAll() {
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			reportHoldersList.get(i).setSelected(true);
		}
	}
	public static void selectNone() {
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			reportHoldersList.get(i).setSelected(false);
		}
	}
	public static void invertSelection() {
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			Boolean isSelected = reportHoldersList.get(i).getSelected();
			reportHoldersList.get(i).setSelected(!isSelected);
		}
	}
	public static String getLatestSelectedReportDate() {
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			if (reportHoldersList.get(i).getSelected()) {
				return (reportHoldersList.get(i).getDate());
			}
		}
		return "";
	}
	public static void exportSelected(File outputFile) throws FileNotFoundException, IOException {
		System.out.println("The following HTML code will be exported");
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			ReportHolder thisReportHolder = reportHoldersList.get(i);
			if (thisReportHolder.getSelected()) {
				System.out.println(thisReportHolder.getHTML());
			}
		}
		String combinedHTML = "";
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			ReportHolder thisReportHolder = reportHoldersList.get(i);
			if (thisReportHolder.getSelected()) {
				combinedHTML += "<h1>" + thisReportHolder.getTask() + " report generated at " + thisReportHolder.getDate() + " (" + thisReportHolder.getSummary() + ")</h1>";
				combinedHTML += thisReportHolder.getHTML();
			}
		}
		combinedHTML = "<!DOCTYPE html><html>" + combinedHTML + "</html>";
		HtmlConverter.convertToPdf(combinedHTML, new FileOutputStream(outputFile));
	}
}
 