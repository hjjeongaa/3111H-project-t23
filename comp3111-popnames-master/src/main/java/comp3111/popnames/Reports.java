/**
* Reports.java - A super class that all reports Task 1-6 should extend
* from.
* @author Yuxi Sun
* @version 1.0
*/

package comp3111.popnames;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

public class Reports{
	//class formatter for formatting time, subclasses should use superclass variable to format to save space.
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	//records the time of creation or last modification
	private LocalDateTime time;  
	private String name;    //name of person in question
	private String gender;    //gender of person in question
	private String country; //country the dataset is from
	private String type;    //human or pet dataset
	private String oReport; //output of report, output should be set in subclass
	//accessors
	public LocalDateTime getTime(){return time;};
	public String getoReport(){return oReport;};
	public String getname(){return name;};
	public String getgender(){return gender;};
	public String getcountry(){return country;};
	public String gettype(){return type;};


	//mutators
	public void modify(String gender, String country, String type){
		this.time = LocalDateTime.now();
		this.country = country;
		this.type = type;
		this.gender = gender;
	}
	public void setoReport(String report){
		this.oReport = report;
	}

	//Constructor
	//Note: data should be validated before being passed to a constructor/ mutator
	public Reports(String name, String gender, String country, String type){
		this.time = LocalDateTime.now();
		this.name = name;
		this.gender = gender;
		this.country = country;
		this.type = type;
		//analysis function should be called in subclass
	}
}