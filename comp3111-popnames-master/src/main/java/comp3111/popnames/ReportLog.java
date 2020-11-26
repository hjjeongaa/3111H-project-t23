/** ReportLog
 * A class that Reports and tasks 4,5,6 inherits.
 * Requires all subclasses to implement four basic methods which are used to keep a record log of all reports generated, and to export them all if the user wishes.
 * @author Hyun Joon Jeong 
 */

package comp3111.popnames;

import java.time.LocalDateTime; 

public abstract class ReportLog{
	public abstract String getoReport();
	public abstract String getTask();
	public abstract LocalDateTime getTime();
	public abstract String getHTML();
}