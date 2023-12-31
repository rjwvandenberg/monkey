package rnee.monkey;

import java.lang.Math;
import java.lang.StringBuilder;

/**
* Provide methods to print and access test results.
* Line 1 success/fail with message for problemmatcher in vscode.
* Subsequent lines contain error message.
*
* @author	René van den Berg
* @version	1
*/
public class TestResult {
    static String errorPrefix = "!!! ";

    Tester tester;
    boolean success;
    String err;

    TestResult(Tester tester, boolean success, String err) {
        this.tester = tester;
        this.success = success;
        this.err = err;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // File and line information
        sb.append(tester.file).append(':').append(tester.method).append(':').append(tester.line).append(':');

        if (success) {
            printSuccess(sb);
        } else if (!success && tester.error.isPresent()) {
            printError(sb);
        } else {
            printFail(sb);
        }

        return sb.toString();
    }

    private void printSuccess(StringBuilder sb) {
        sb.append(" pass");
    }

    private void printFail(StringBuilder sb) {
        sb.append(" error: ").append(err).append('\n');

        // Tests and error formatting is too specific. Made for testing (object -> object) functions that have ordered outputs, works ok enough for now. 
        int before = 20;
        int after = 400;

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
        String caret = " ".repeat(4 + 10 + before + Math.min(difference-before, 0)) + "\\/";

        sb.append(caret).append('\n');
        sb.append("    Expected: ").append(exstr).append('\n');
        sb.append("    Actual  : ").append(acstr).append('\n');
    }

    private void printError(StringBuilder sb) {
        sb.append(" error: ").append(err).append('\n');
        sb.append(errorPrefix).append("EXCEPTION IN TEST: ").append(err).append('\n');
        sb.append(errorPrefix).append(tester.error.get().toString()).append('\n').append('\n');
    }

}