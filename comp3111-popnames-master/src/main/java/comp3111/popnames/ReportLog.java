package comp3111.popnames;

import java.time.LocalDateTime; 

public abstract class ReportLog{
	public abstract String getoReport();
	public abstract String getTask();
	public abstract LocalDateTime getTime();
	public abstract String getHTML();
}