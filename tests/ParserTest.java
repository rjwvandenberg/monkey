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
            // sampleTest(),
            emptyTest(),
            letTest(),
            returnTest(),
            blockTest(),
            numberTest(),
            identifierTest(),
            plusTest(),
            // ifTest(),
            // elseTest(),
        };

        for (TestResult testResult : tests) {
            System.out.println(testResult);
        }
    }

    TestResult sampleTest() {
        String input = "let foobar = add(5, 5);\nlet barfoo = 5 * 5 / 10 + 18 - add(5, 5) + multiply(123);\nlet anotherName = barfoo;";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));

        ArrayList<Statement> actual = new Parser(tokens).parse();

        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult emptyTest() {
        String input = "";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {};
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));

        ArrayList<Statement> actual = new Parser(tokens).parse();

        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult letTest() {
        String input = "let x = 5;\nlet y = 10;\n";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new LetNode(k("let"), new IdNode(id("x")), new Number(i("5"), 5)),
            new LetNode(k("let"), new IdNode(id("y")), new Number(i("10"), 10)),
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));

        ArrayList<Statement> actual = new Parser(tokens).parse();

        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult returnTest() {
        String input = "return twelve; return five;";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new ReturnNode(k("return"), new IdNode(id("twelve"))),
            new ReturnNode(k("return"), new IdNode(id("five"))),
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));

        ArrayList<Statement> actual = new Parser(tokens).parse();

        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult blockTest() {
        String input = "{ let id=15; return 90; }";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new LetNode(k("let"), new IdNode(id("id")), new Number(i("15"),15)),
            new ReturnNode(k("return"), new Number(i("90"),90)),
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));

        Tester t = new Tester(tokens, expected);
        try {
            ArrayList<Statement> actual = new Parser(tokens).parseBlock();
            return t.equality(actual);
        } catch (ParseException p) {
            return t.error(p);
        }
    }

    TestResult numberTest() {
        String input = "3;";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new ExpressionStatement(k("return"), new Number(i("3"), 3))
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));
        ArrayList<Statement> actual = new Parser(tokens).parse();
        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult identifierTest() {
        String input = "andy;";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new ExpressionStatement(k("return"), new IdNode(id("andy")))
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));
        ArrayList<Statement> actual = new Parser(tokens).parse();
        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult plusTest() {
        String input = "5+3;";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new ExpressionStatement(k("return"),new BinaryExpression(new Token(TokenType.Plus,"+"), new Number(i("5"),5), new Number(i("3"),3)))
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));
        ArrayList<Statement> actual = new Parser(tokens).parse();
        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult ifTest() {
        String input = "return if expr {}; return if var { let id=15; return 90; };";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new ReturnNode(k("return"), new IfNode(k("if"), new IdNode(id("expr")), new ArrayList<Statement>())),
            new ReturnNode(k("return"), new IfNode(k("if"), new IdNode(id("var")), new ArrayList<Statement>(Arrays.asList(new Statement[]{
                new LetNode(k("let"), new IdNode(id("id")), new Number(i("15"), 15)),
                new ReturnNode(k("return"), new Number(i("90"), 90))
            }))))
        };
        ArrayList<Statement> expected = new ArrayList(Arrays.asList(statements));
        ArrayList<Statement> actual = new Parser(tokens).parse();
        Tester t = new Tester(tokens, expected);
        return t.equality(actual);
    }

    TestResult elseTest() {
        String input = "return else { let id=15; return 90; };";
        ArrayList<Token> tokens = getTokens(input);
        Statement[] statements = new Statement[] {
            new ReturnNode(k("return"), new ElseNode(k("else"), new ArrayList<Statement>(Arrays.asList(new Statement[]{
                new LetNode(k("let"), new IdNode(id("id")), new Number(i("15"),15)),
                new ReturnNode(k("return"), new Number(i("90"),90))
            }))))
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