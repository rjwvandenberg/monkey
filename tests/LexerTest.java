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
    void runTests() {
        TestResult[] tests = new TestResult[] {
            sampleTest(),
            sample2Test(),
            sample3Test(),
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
            new Token(TokenType.LBrace, "{"),
            new Token(TokenType.RBrace, "}"),
            new Token(TokenType.Comma, ","),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.EOF, ""),
        };
        ArrayList expected = new ArrayList<Token>(Arrays.asList(tokens));
        ArrayList actual = LexerTest.readN(new Lexer(input), 9);

        Tester t = new Tester<String, ArrayList<Token>>(input, expected);
        return t.equality(actual);
    }

    TestResult sample2Test() {
        String input = "let five = 5;\nlet ten = 10;\n\nlet add = fn(x,y) {\n\tx + y;\n};\n\nlet result = add(five, ten);";
        Token[] tokens = new Token[] {
            new Token(TokenType.Let, "let"),
            new Token(TokenType.Identifier, "five"),
            new Token(TokenType.Assign, "="),
            new Token(TokenType.Integer, "5"),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.Let, "let"),
            new Token(TokenType.Identifier, "ten"),
            new Token(TokenType.Assign, "="),
            new Token(TokenType.Integer, "10"),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.Let, "let"),
            new Token(TokenType.Identifier, "add"),
            new Token(TokenType.Assign, "="),
            new Token(TokenType.Function, "fn"),
            new Token(TokenType.LParen, "("),
            new Token(TokenType.Identifier, "x"),
            new Token(TokenType.Comma, ","),
            new Token(TokenType.Identifier, "y"),
            new Token(TokenType.RParen, ")"),
            new Token(TokenType.LBrace, "{"),
            new Token(TokenType.Identifier, "x"),
            new Token(TokenType.Plus, "+"),
            new Token(TokenType.Identifier, "y"),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.RBrace, "}"),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.Let, "let"),
            new Token(TokenType.Identifier, "result"),
            new Token(TokenType.Assign, "="),
            new Token(TokenType.Identifier, "add"),
            new Token(TokenType.LParen, "("),
            new Token(TokenType.Identifier, "five"),
            new Token(TokenType.Comma, ","),
            new Token(TokenType.Identifier, "ten"),
            new Token(TokenType.RParen, ")"),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.EOF, ""),
        };
        ArrayList expected = new ArrayList<Token>(Arrays.asList(tokens));
        ArrayList actual = LexerTest.readN(new Lexer(input), tokens.length);
        Tester t = new Tester<String, ArrayList<Token>>(input, expected);
        return t.equality(actual);
    }

    TestResult sample3Test() {
        String input = "!-/*5;\n5 < 10 > 5;";
        Token[] tokens = new Token[] {
            new Token(TokenType.Not, "!"),
            new Token(TokenType.Minus, "-"),
            new Token(TokenType.Divide, "/"),
            new Token(TokenType.Multiply, "*"),
            new Token(TokenType.Integer, "5"),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.Integer, "5"),
            new Token(TokenType.Lesser, "<"),
            new Token(TokenType.Integer, "10"),
            new Token(TokenType.Greater, ">"),
            new Token(TokenType.Integer, "5"),
            new Token(TokenType.Semicolon, ";"),
            new Token(TokenType.EOF, ""),
        };
        ArrayList expected = new ArrayList<Token>(Arrays.asList(tokens));
        ArrayList actual = LexerTest.readN(new Lexer(input), tokens.length);
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