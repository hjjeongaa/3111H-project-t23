/**
 * ReportHistory
 * Singleton class that maintains a list of all generated reports, and has functionality to export them all to PDF format.
 * @author Hyun Joon Jeong
 */

package comp3111.export;

import comp3111.popnames.ReportLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;


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
		reportHoldersList.add(0,reportHolderToAdd);
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
	 * Method that, given a File object to write to, converts each HTML string from all selected reports to individual PDFs and merges all of them together.
	 * @param outputFile File object which is the destination of the PDF file.
	 * @throws FileNotFoundException when something is wrong with the filepath.
	 * @throws IOException when something goes wrong whilst writing to the filepath.
	 */
	public static void exportSelected(File outputFile) throws FileNotFoundException, IOException {
		System.out.println("Begin PDF merge & export.");
		
		PdfDocument outputPdf = new PdfDocument(new PdfWriter(outputFile));
		PdfMerger merger = new PdfMerger(outputPdf);
		
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			String reportHtml = reportHoldersList.get(i).getHTML();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			//wrap in html tag
			HtmlConverter.convertToPdf("<!DOCTYPE html><html>" + reportHtml + "</html>", outputStream);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			//create new pdf document from the converted html
			PdfDocument pdfToMerge = new PdfDocument(new PdfReader(inputStream));
			//merge all pages in the pdf to the output file
			merger.merge(pdfToMerge, 1, pdfToMerge.getNumberOfPages());
			pdfToMerge.close();
		}
		//merging finished, close it!
		outputPdf.close();
		System.out.println("PDF merge & export finished.");
	}
}
 