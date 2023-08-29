package rnee.monkey;

import rnee.monkey.Lexer;
import rnee.monkey.Tester;
import rnee.monkey.Token;
import rnee.monkey.TokenType;
import java.util.ArrayList;
import java.util.Arrays;

/**
* Tests for Lexer.
*
* @author	René van den Berg
* @version	1
*/
public class LexerTest {
    String location;
    LexerTest() {
        location = "LexerTest.java";
    }

    void runTests() {
        TestResult[] tests = new TestResult[] {
            sampleTest(),
            successTest(),
        };

        for (TestResult testResult : tests) {
            System.out.println(testResult);
        }
    }

    TestResult successTest() {
        Tester t = new Tester<String, String>("yep", "s");
        return t.equality("s");
    }

    TestResult sampleTest() {
        String input = "=+(){},;";
        Token[] tokens = new Token[] {
            new Token(TokenType.Assign, "="),
            new Token(TokenType.Plus, "+"),
            new Token(TokenType.LParen, "("),
            new Token(TokenType.RParen, ")"),
            new Token(TokenType.LBrace, "["),
            new Token(TokenType.RBrace, "]"),
            new Token(TokenType.Comma, ","),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.EOF, ""),
        };
        ArrayList expected = new ArrayList<Token>(Arrays.asList(tokens));
        ArrayList actual = LexerTest.readN(new Lexer(input), 9);

        Tester t = new Tester<String, ArrayList<Token>>(input, expected);
        return t.equality(actual);
    }

    

    static ArrayList<Token> readN(Lexer lexer, int n) {
        ArrayList l = new ArrayList<Token>();
        for (int i = 0; i < n; i++) {
            l.add
            (lexer.nextToken());
        }
        return l;
    }
}