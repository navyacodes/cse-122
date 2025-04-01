// Navya Jain 
// 11/21/2024
// CSE 122 
// P3: Program Linting
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class represents a linter, which checks a file for code-quality issues based on a list of
// provided checks. The linter processes each line of the file and applies each check in the 
// user-inputted list, collecting any detected errors for later reporting.
public class Linter {

    private List<Check> checks;

    // Behavior:
    //   - This method makes a Linter given a list of checks. 
    // Parameters:
    //   - checks: a list of Checks that the linter should run through when linting.
    // Returns: none
    // Exceptions: none    
    public Linter(List<Check> checks) {
        this.checks = checks;
    }

    // Behavior:
    //   - This method reads the specified file line by line and applies all checks in the given 
    //     list to each line. Each error is put into a list, which is returned to the user. 
    // Parameters:
    //   - fileName: the name (String) of the file to lint.
    // Returns:
    //   - List<Error>: a list of Errors containing the issues detected in the file.
    // Exceptions: none  
    public List<Error> lint(String fileName) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File (fileName));
        List<Error> programErrors = new ArrayList<>();
        int lineNumber = 0;
        while (fileScanner.hasNextLine()) {
            lineNumber++;
            String line = fileScanner.nextLine();
            for (Check check : checks) {
                Optional<Error> error = check.lint(line, lineNumber);
                if (error.isPresent()) {
                    programErrors.add(error.get());
                }
            }
        }
        return programErrors;
    }
}
