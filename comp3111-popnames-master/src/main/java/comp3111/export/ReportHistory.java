/**
 * ReportHistory
 * Singleton class that maintains a list of all generated reports, and has functionality to export them all to PDF format.
 * @author Hyun Joon Jeong
 */

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
	/**
	 * Constructor for ReportHistory
	 * Just initialize the reportHoldersList list.
	 */
	private ReportHistory() {
		reportHoldersList = FXCollections.<ReportHolder>observableArrayList();
	}
	public static ReportHistory getInstance() {
		return instance;
	}
	/**
	 * Used to fill the tableView in the export UI.
	 * @return
	 */
	public static ObservableList<ReportHolder> getReportHoldersList() {
		return reportHoldersList;
	}
	/**
	 * Method used to add reports/tasks to the history of reports generated. Should be called after all the results have been generated.
	 * @param reportLogToAdd the task object.
	 */
	public static void addReportLog(ReportLog reportLogToAdd) {
		ReportHolder reportHolderToAdd = new ReportHolder(reportLogToAdd);
		reportHoldersList.add(reportHolderToAdd);
	}
	/**
	 * UI method to make all reports selected
	 */
	public static void selectAll() {
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			reportHoldersList.get(i).setSelected(true);
		}
	}
	/**
	 * UI method to unselect all reports
	 */
	public static void selectNone() {
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			reportHoldersList.get(i).setSelected(false);
		}
	}
	/**
	 * UI method to invert the selection of reports
	 */
	public static void invertSelection() {
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			Boolean isSelected = reportHoldersList.get(i).getSelected();
			reportHoldersList.get(i).setSelected(!isSelected);
		}
	}
	/**
	 * UI method to get the time of the latest generated selected report in the list. Used to create a suggested filename for outputting to PDF. Returns an empty string if the list is empty.
	 * @return the latest report's generated time, in hh:mm:ss format
	 */
	public static String getLatestSelectedReportDate() {
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			if (reportHoldersList.get(i).getSelected()) {
				return (reportHoldersList.get(i).getDate());
			}
		}
		return "";
	}
	/**
	 * Method that, given a File object to write to, collates all the HTML from all selected reports and converts the combined HTML document into a PDF.
	 * @param outputFile File object which is the destination of the PDF file.
	 * @throws FileNotFoundException when something goes wrong with the filepath.
	 * @throws IOException when something goes wrong whilst writing to the filepath.
	 */
	public static void exportSelected(File outputFile) throws FileNotFoundException, IOException {
		//debug
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
				//add a header with basic information on the report
				combinedHTML += "<h1>" + thisReportHolder.getTask() + " report generated at " + thisReportHolder.getDate() + " (" + thisReportHolder.getSummary() + ")</h1>";
				combinedHTML += thisReportHolder.getHTML();
			}
		}
		//wrap it up in the outermost html tag
		combinedHTML = "<!DOCTYPE html><html>" + combinedHTML + "</html>";
		HtmlConverter.convertToPdf(combinedHTML, new FileOutputStream(outputFile));
	}
}
 