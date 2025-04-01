// Navya Jain 
// 11/21/2024
// CSE 122 
// P3: Program Linting
// TA: Caleb Hsu

import java.util.*;

// This class implements a check for detecting empty "System.out.println" statements 
// in a given line of code. It implements the Check interface and returns an 
// Error (with the error code, the line number, and a message) if an empty "println" is detected.
public class BlankPrintlnCheck implements Check {
	public static final int ERROR_CODE = 3;
	
	// Behavior:
	//   - This method analyzes a line of code to determine if it contains an empty 
	//     System.out.println("") statement. If detected, it returns an Optional containing an 
	//     Error with details about the issue. Otherwise, it returns an empty Optional.
	// Parameters:
	//   - line: the line (String) of code to analyze.
	//   - lineNumber: the line number (int) of the code being analyzed, for error reporting purposes.
	// Returns:
	//   - Optional<Error>: If an empty System.out.println is found, an Optional containing the Error 
	//                      (with an error code, the line number, and a message about the error) will
	//                      be returned; otherwise, an empty Optional will be returned.
	// Exceptions: none	
	public Optional<Error> lint(String line, int lineNumber) {
		if (line.contains("System.out.println(\"\")")) {
			return Optional.of(new Error(ERROR_CODE, lineNumber, 
				   "Line has an empty println"));
		}
		return Optional.empty();
	}
}
