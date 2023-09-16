package rnee.monkey;

import java.util.Optional;
import java.lang.Thread;
import java.lang.StackTraceElement;

/**
* Tester Object to provide repeatable tests for monkey.
*
* @author	René van den Berg
* @version	1
*/
public class Tester<A,B> {
    public String method;
    public int line;
    public String file;
    public String classname;

    public String name;
    public A in;
    public B expected;
    public B actual;
    public Optional<Exception> error;

    Tester(A in, B expected) {
        this.in = in;
        this.expected = expected;
        this.error = Optional.empty();

        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        method = caller.getMethodName();
        file = caller.getFileName();
        line = caller.getLineNumber();
        classname = caller.getClassName();
    }

    TestResult equality(B actual) {
        this.actual = actual;
        boolean equal = expected.equals(actual);
        if (equal) {
            return success();
        } else {
            return fail("value not equal to expected");
        }
    }

    TestResult error(Exception e) {
        this.error = Optional.ofNullable(e);
        return fail("Encountered error during test execution: ");
    }

    TestResult success() {
        return new TestResult(this, true, "");
    }

    TestResult fail(String err) {
        return new TestResult(this, false, err);
    }

}