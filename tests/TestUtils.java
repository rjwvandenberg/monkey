package rnee.monkey;

import rnee.monkey.LexerTest;
import rnee.monkey.ParserTest;

class TestUtils {
    public static void main(String[] args) {
        System.out.println("Running Monkey tests...");

        new LexerTest().runTests();
        new ParserTest().runTests();

        System.out.println("Done testing.");
    }
}