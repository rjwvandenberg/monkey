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

        try {
            while (peekToken().type != TokenType.EOF && iter<tokens.size()) {
                Statement s = parseStatement();
                l.add(s);
                iter++;
            }
        } catch (ParseException p) {
            l.add(new InvalidStatement(new Token(TokenType.Illegal, "InvalidStatement due to Parsing error at token position " + position + ":\n" + p.getMessage())));
        }

        return l;
    }

    Statement parseStatement() throws ParseException {
        Token first = peekToken();
        switch(first.type) {
            case Let: return parseLet();
            case Return: return parseReturn();
            case If: return parseIf();
            default: {
                nextToken();
                throw new ParseException("Invalid statement token: " + first.toString());
            }
        }
    }

    LetNode parseLet() throws ParseException{
        Token literal = nextToken();
        IdNode id = parseIdentifier();
        checkToken(TokenType.Assign);
        Expression expr = parseExpression();
        checkToken(TokenType.Semicolon);

        return new LetNode(literal, id, expr);
    }

    ReturnNode parseReturn() throws ParseException {
        Token literal = nextToken();
        Expression expr = parseExpression();
        checkToken(TokenType.Semicolon);

        return new ReturnNode(literal, expr);
    }

    ArrayList<Statement> parseBlock() throws ParseException {
        checkToken(TokenType.LBrace);
        ArrayList<Statement> l = new ArrayList();
        
        while (peekToken().type != TokenType.RBrace) {
            l.add(parseStatement());
        }

        checkToken(TokenType.RBrace);
        return l;
    }

    IfNode parseIf() throws ParseException {
        Token literal = nextToken();
        Expression condition = parseExpression();
        ArrayList<Statement> block = parseBlock();
        return new IfNode(literal, condition, block);
    }

    Expression parseExpression() throws ParseException {
        // For now only accept simple integer/identifier, needs to be expanded with parser logic for multitoken expr.
        
        switch(peekToken().type) {
            case Integer: return parseNumber();
            case Identifier: return parseIdentifier();
            default: throw new ParseException("Invalid expression token: " + nextToken().toString());
        }
    }

    IdNode parseIdentifier() throws ParseException {
        Token id = nextToken();
        if (id.type != TokenType.Identifier) {
            throw new ParseException("Invalid id token: " + id.toString());
        }
        return new IdNode(id);
    }

    NumberNode parseNumber() throws ParseException {
        Token number = nextToken();
        if (number.type != TokenType.Integer) {
            throw new ParseException("Invalid integer token: " + number.toString());
        }
        return new NumberNode(number);
    }

    Token nextToken() throws ParseException {
        Token t = peekToken();
        position += 1;
        return t;
    }

    Token peekToken() throws ParseException {
        return peekToken(position);
    }

    Token peekToken(int index) throws ParseException {
        if (index >= tokens.size()) {
            throw new ParseException("Out of token index bounds exception");
        }
        return tokens.get(position);
    }

    void checkToken(TokenType type) throws ParseException {
        Token t = nextToken();
        if (t.type != type) {
            throw new ParseException("Invalid " + type.toString() + " token: " + t.toString());
        }
    }

    String missingToken(TokenType missing, Token t) {
        return "Missing " + missing + " token. Got: " + t;
    }
}