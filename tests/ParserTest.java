package rnee.monkey;

import rnee.monkey.Lexer;
import rnee.monkey.Parser;
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
        };

        for (TestResult testResult : tests) {
            System.out.println(testResult);
        }
    }

    TestResult sampleTest() {
        String input = "let x = 5;\nlet y = 10;\nlet foobar = add(5, 5);\nlet barfoo = 5 * 5 / 10 + 18 - add(5, 5) + multiply(123);\nlet anotherName = barfoo;";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new Statement(new Token(TokenType.Illegal, "")),
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
}