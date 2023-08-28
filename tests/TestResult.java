package rnee.monkey;

import java.lang.Math;

/**
* Provide methods to print and access test results.
* Line 1 success/fail with message for problemmatcher in vscode.
* Subsequent lines contain error message.
*
* @author	René van den Berg
* @version	1
*/
public class TestResult {
    Tester tester;
    boolean success;

    String message;
    String err;

    TestResult(Tester tester, boolean success, String message, String err) {
        this.tester = tester;
        this.success = success;
        this.message = message;
        this.err = err;
    }

    @Override
    public String toString() {
        if (success) {
            return message + ".";
        } else {
            // Tests and error formatting is too specific. Made for testing (object -> object) functions that have ordered outputs, works ok enough for now. 
            int before = 20;
            int after = 50;

            String expected = tester.expected.toString();
            String actual = tester.actual.toString();

            int limit = Math.min(expected.length(), actual.length());
            int difference = limit;

            for (int i = 0; i < limit; i++) {
                if (expected.charAt(i) != actual.charAt(i)) {
                    difference = i;
                    break;
                }
            }

            int offset = Math.max(0, difference-before);

            String exstr = expected.substring(offset, Math.min(offset+after, expected.length()));
            String acstr = actual.substring(offset, Math.min(offset+after, actual.length()));
            String caret = " ".repeat(10 + 3 + before + Math.min(difference-before, 0)) + "^";

            return "error: " + message + err + ".\n" + "--- in:\n" + tester.in + "\n\nExpected: ..." + exstr + "...\nActual  : ..." + acstr + "...\n" + caret;
        }
    }
}