package com.rnee.monkey;

public class Lexer {
    public static boolean analyse(String source) {
        if (source.equals("Sourcecode")) {
            return true;
        } else {
            return false;
        }
    }
}
