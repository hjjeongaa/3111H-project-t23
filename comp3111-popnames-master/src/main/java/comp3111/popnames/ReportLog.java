/** ReportLog
 * A class that Reports and tasks 4,5,6 inherits.
 * Requires all subclasses to implement four basic methods which are used to keep a record log of all reports generated, and to export them all if the user wishes.
 * @author Hyun Joon Jeong 
 */

package comp3111.popnames;

import java.time.LocalDateTime;
import java.util.regex.*;

public abstract class ReportLog{
	/**
	 * A simple static method usable by all UI controllers for input name sanitization (no whitespace inside the name).
	 * @param name The name to be validated.
	 * @return An empty string if the name has whitespace within it. If the name has no whitespace inside but has leading and trailing whitespace, they will be removed and the result returned. If there is no whitespace in the name at all, the original input will be returned.
	 */
	public static String validateName(String name) {
		Matcher validName = Pattern.compile("^\\s*\\S+\\s*$").matcher(name);
		//Trim name if there are no spaces in the name.
		return (validName.find()? name.trim() : "");
	}
	public abstract String getoReport();
	public abstract String getTask();
	public abstract LocalDateTime getTime();
	public abstract String getHTML();
}