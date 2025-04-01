// Navya Jain 
// 11/21/2024
// CSE 122 
// P3: Program Linting
// TA: Caleb Hsu

import java.util.*;

// This class implements the Check interface and detects if a given line of code has a length of 
// 100 characters or greater, flagging each occurrence of this long line length as an error.
public class LongLineCheck implements Check {
	public static final int ERROR_CODE = 1;

	// Behavior:
	//   - This method analyzes a line of code to determine if it is 100 characters or greater in 
	//     length. If it meets this length, it returns an Optional containing a Error with details 
	//     about the issue. Otherwise, it returns an empty Optional.
	// Parameters:
	//   - line: the line (String) of code to analyze.
	//   - lineNumber: the line number (int) of the code being analyzed, for error reporting purposes.
	// Returns:
	//   - Optional<Error>: If the line has 100 or more characters, an Optional containing the Error 
	//                      (with an error code, the line number, and a message about the error) will
	//                      be returned; otherwise, an empty Optional will be returned.
	// Exceptions: none		
	public Optional<Error> lint(String line, int lineNumber) {
		if (line.length() >= 100) {
			return Optional.of(new Error(ERROR_CODE, lineNumber, 
				   "Line contains 100 or more characters!"));
		}
		return Optional.empty();
	}
}
