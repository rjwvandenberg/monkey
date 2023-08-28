package rnee.monkey;

/**
* Tester Object to provide repeatable tests for monkey.
*
* @author	René van den Berg
* @version	1
*/
public class Tester<A,B> {
    public String name;
    public A in;
    public B expected;
    public B actual;

    Tester(String name, A in, B expected) {
        this.name = name;
        this.in = in;
        this.expected = expected;
    }

    TestResult equality(B actual) {
        this.actual = actual;
        boolean equal = expected.equals(actual);
        if (equal) {
            return success(name);
        } else {
            return fail(name, " not equal");
        }
    }

    TestResult success(String message) {
        return new TestResult(this, true, message, "");
    }

    TestResult fail(String message, String err) {
        return new TestResult(this, false, message, err);
    }

}