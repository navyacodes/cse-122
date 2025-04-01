// Navya Jain 
// 11/21/2024
// CSE 122 
// P3: Program Linting
// TA: Caleb Hsu

import java.util.*;

// This class implements the Check interface and detects the use of the break statement in a given
// line of code. It flags any occurrence of "break" outside of a single-line comment as an error.
public class BreakCheck implements Check {
	public static final int ERROR_CODE = 2; 

    // Behavior:
	//   - This method analyzes a line of code to determine if it contains a break statement 
	//     outside of single-line comments. If detected, it returns an Optional containing an 
	//     Error with details about the issue. Otherwise, it returns an empty Optional.
	// Parameters:
	//   - line: the line (String) of code to analyze.
	//   - lineNumber: the line number (int) of the code being analyzed, for error reporting purposes.
	// Returns:
	//   - Optional<Error>: If a break statement is found (outside of single-line comments), an 
    //                      Optional containing the Error (with an error code, the line number, and
    //                      a message about the error) will be returned; otherwise, an empty 
    //                      Optional will be returned.
	// Exceptions: none	
    public Optional<Error> lint(String line, int lineNumber) {
        Scanner lineScanner = new Scanner(line);
        while (lineScanner.hasNext()) {
            String token = lineScanner.next();
            if (token.contains("//")) { 
                int commentStart = token.indexOf('/');
                String beforeComment = token.substring(0, commentStart);
                if (!beforeComment.contains("break")) {
                    return Optional.empty();
                }
            }
            if (token.contains("break")) {
			    return Optional.of(new Error(ERROR_CODE, lineNumber, 
				       "Line implements the forbidden feature break!"));
            }
        }
        return Optional.empty();
	}
}
