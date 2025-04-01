// Navya Jain 
// 11/21/2024
// CSE 122 
// P3: Program Linting
// TA: Caleb Hsu

// This class represents an Error in code with details about its error code, the line the error
// is associated with, and a message about what this error means. Users can retrieve each of
// these attributes and a formatted representation of the error.
public class Error {
    private int code;
    private int lineNumber;
    private String message;

    // Behavior:
    //   - This method constructs an Error instance with the specified error code, 
    //     line number, and related message specifying what the error is.
    // Parameters:
    //   - code: the numeric code (integer) representing the type of error.
    //   - lineNumber: the line number (integer) in the source where the error occurred.
    //   - message: a message (String) providing details about the error.
    // Return: none
    // Exceptions: none
    public Error(int code, int lineNumber, String message) {
        this.code = code;
        this.lineNumber = lineNumber;
        this.message = message;
    }

    // Behavior:
    //   - This method returns a formatted string representation of the error, including the 
    //     line number, error code, and message details.
    // Parameters: none
    // Returns:
    //   - String: the string representation of the error
    // Exceptions: none    
    public String toString() {
        return "(Line: " + lineNumber + ") has error code " + code + "\n" + message;
    }

    // Behavior:
    //   - This method gets and returns the line number where the error occurred in an Error.
    // Parameters: none
    // Returns:
    //   - int: the line number where the error occurred.
    // Exceptions: none
    public int getLineNumber() {
        return lineNumber;
    }

    // Behavior:
    //   - This method gets and return the error code associated with the error. 
    // Parameters: none
    // Returns:
    //   - int: the numeric code representing the type of error.    
    public int getCode() {
        return code;
    }

    // Behavior:
    //   - This method gets and returns the message associated with the error.
    // Parameters: none
    // Returns:
    //   - String: the message providing details about the error.
    // Exceptions: none    
    public String getMessage() {
        return message;
    }
}
