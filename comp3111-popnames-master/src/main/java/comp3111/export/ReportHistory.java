package comp3111.export;

import comp3111.popnames.ReportLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
		System.out.println(reportHoldersList);
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
	public static void exportSelected() {
		/*
		for (int i = 0; i < reportHoldersList.size(); ++i) {
			ReportHolder thisReportHolder = reportHoldersList.get(i);
			thisReportHolder.getHTML();
		}
		*/
	}
}
 