package rnee.monkey;

import java.util.ArrayList;

/**
* Create syntax trees by parsing Token strings.
* The whole, insert Illegal nodes seems flawed, as Nodes will not check token.type? Maybe throw or different way to unwind.
*
* @author	René van den Berg
* @version	1
*/
class Parser {
    ArrayList<Token> tokens;
    int position;

    Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    ArrayList<Statement> parse() {
        ArrayList<Statement> l = new ArrayList();
        int iter = 0;

        while (peekToken().type != TokenType.EOF && iter<tokens.size()) {
            Statement s = parseStatement();
            l.add(s);
            iter++;
        }
        return l;
    }

    Statement parseStatement() {
        Token first = peekToken();
        switch(first.type) {
            case Let: return parseLet();
            case Return: return parseReturn();
            default: return new Statement(new Token(TokenType.Illegal, "Missing statement token. Got: " + first.toString()));
        }
    }

    Statement parseLet() {
        ArrayList<String> errors = new ArrayList();
        Token literal = nextToken();
        IdNode id = parseIdentifier();
        Token assign = nextToken();
        Expression expr = parseExpression();
        Token semicolon = nextToken();

        if (assign.type != TokenType.Assign) {
            errors.add(missingToken(TokenType.Assign, assign));
        }
        if (semicolon.type != TokenType.Semicolon) {
            errors.add(missingToken(TokenType.Semicolon, semicolon));
        }

        if (errors.size() > 0) {
            return new Statement(new Token(TokenType.Illegal, String.join("\n", errors)));
        } else {
            return new LetNode(literal, id, expr);
        }
    }

    Statement parseReturn() {
        return new Statement(new Token(TokenType.Illegal, "not implemented"));
    }

    Expression parseExpression() {
        // consume until ; ?

        // for now only identifier and number are legal.
        switch(peekToken().type) {
            case Integer: break;
            case Identifier: break;
        }
        position+=100;

        return new Expression(new Token(TokenType.Illegal, "not implemented"));
    }

    IdNode parseIdentifier() {
        return new IdNode(new Token(TokenType.Illegal, "not implemented"));
    }

    NumberNode parseNumber() {

        return new NumberNode(new Token(TokenType.Illegal, "not implemented"));
    }

    Token nextToken() {
        Token t = peekToken();
        position += 1;
        return t;
    }

    Token peekToken() {
        return peekToken(position);
    }

    Token peekToken(int index) {
        if (index >= tokens.size()) {
            return new Token(TokenType.Illegal, "Out of bounds index exception");
        }
        return tokens.get(position);
    }

    String missingToken(TokenType missing, Token t) {
        return "Missing " + missing + " token. Got: " + t;
    }
}