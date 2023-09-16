package rnee.monkey;

import rnee.monkey.Lexer;
import rnee.monkey.TestResult;
import rnee.monkey.Token;
import rnee.monkey.TokenType;
import java.util.ArrayList;
import java.util.Arrays;

/**
* Tests for Parser.
*
* @author	René van den Berg
* @version	1
*/
class ParserTest {
    void runTests() {
        TestResult[] tests = new TestResult[] {
            sampleTest(),
            letTest(),
        };

        for (TestResult testResult : tests) {
            System.out.println(testResult);
        }
    }

    TestResult sampleTest() {
        String input = "let foobar = add(5, 5);\nlet barfoo = 5 * 5 / 10 + 18 - add(5, 5) + multiply(123);\nlet anotherName = barfoo;";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new LetNode(k("let"),new IdNode(id("foobar")),new Expression(id("add")))
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));

        ArrayList<Statement> actual = new Parser(tokens).parse();

        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult letTest() {
        String input = "let x = 5;\nlet y = 10;\n";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new LetNode(k("let"), new IdNode(id("x")), new NumberNode(i("5"))),
            new LetNode(k("let"), new IdNode(id("y")), new NumberNode(i("10"))),
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));

        ArrayList<Statement> actual = new Parser(tokens).parse();

        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    static ArrayList<Token> getTokens(String input) {
        ArrayList tokens = new ArrayList<Token>();
        Lexer l = new Lexer(input);

        Token t = new Token(TokenType.Illegal, "");
        while (t.type != TokenType.EOF) {
            t = l.nextToken();
            tokens.add(t);
        }

        return tokens;
    }

    static Token k(String s) {
        return new Token(TokenType.lookupKeyword(s).get(), s);
    }
    static Token id(String s) {
        return new Token(TokenType.Identifier, s);
    }
    static Token i(String s) {
        return new Token(TokenType.Integer, s);
    }
}