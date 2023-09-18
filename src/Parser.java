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
            default: return parseExpressionStatement();
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

    ExpressionStatement parseExpressionStatement() throws ParseException {
        Expression expr = parseExpression();
        checkToken(TokenType.Semicolon);
        return new ExpressionStatement(new Token(TokenType.Return,""), expr);
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

    ElseNode parseElse() throws ParseException {
        Token literal = nextToken();
        ArrayList<Statement> block = parseBlock();
        return new ElseNode(literal, block);
    }

    Expression parseExpression() throws ParseException {
        //prefix e is an expression, or else this function should not have been called.
        //infix decides to keep parsing or not depending on the token encountered
        Expression e = parsePrefix();
        e = parseInfix(e);
        // swap precedence
        return e;
    }

    /**
    * Read token and process it as is
    */
    Expression parsePrefix() throws ParseException {
        switch(peekToken().type) {
            case Identifier: {
                return parseIdentifier();
            }
            case Integer: {
                return parseNumber();
            }
            // Unary Operators
            case Not:
            case Plus:
            case Minus: {
                return new UnaryExpression(nextToken(), parseExpression());
            }
            // Boolean expressions
            case False: 
            case True: {
                return parseBoolean();
            }
            // ManualPrecedence expression
            case LParen: {
                throw new ParseException("Implement ( precedence");
                // break;
            }
            // block prefix expression
            case LBrace: {
                throw new ParseException("expression blocks not supported?");
                // break;
            }
            // Function expression
            case Function: {
                throw new ParseException("Function not implemented, parseFunction");
                // break;
            }
            // If expression
            case If: {
                throw new ParseException("If not impelemnted");
                // break;
            }
            
            // Illegal PREFIXes
            case Semicolon:
            case Assign:
            case Multiply:
            case Divide:
            case Lesser:
            case Greater:
            case Equals:
            case NotEquals:
            case Comma:
            case RParen:
            case RBrace: 
                throw new ParseException("Found closing character at start of expression: " + nextToken());
            // Illegal Statements
            case Let:
            case Return:
                throw new ParseException("Expected expression PREFIX, found statement: " + nextToken());
            // Else
            case Else:
                throw new ParseException("Cannot start expression with PREFIX token " + nextToken());
            case EOF: {
                throw new ParseException("Encountered EOF, expected Expression");
            }
            default: throw new ParseException("Expression PREFIX token " + nextToken() + " not implemented.");
        }
    }

    /**
    * Reading token with directly preceding expression in mind.
    */
    Expression parseInfix(Expression e) throws ParseException {
        switch(peekToken().type) {
            // binary expressions
            case Multiply:
            case Divide:
            case Lesser:
            case Greater:
            case Equals:
            case NotEquals:
            case Plus:
            case Minus: {
                return new BinaryExpression(nextToken(), e, parseExpression());
            }
            case Semicolon:{
                // do not read semicolon token as it is part of the preceding statement
                return e;
            }
            // Illegal INFIXes
            case RParen:
            case RBrace:
            case Not:
            case False: 
            case True:
            case Assign:
            case Comma:
            case Identifier:
            case Integer:
            case LParen:
            case LBrace: 
            case Function:
            case Else:
            case If: 
                throw new ParseException("Expected expression, found illegal INFIX token " + nextToken());
            case Let:
            case Return:
                throw new ParseException("Expected expression, found statement token " + nextToken());
            case EOF: {
                throw new ParseException("Encountered EOF, expected INFIX token: " + nextToken());
                //return e;
            }
            default: throw new ParseException("Expression INFIX token " + nextToken() + " not implemented.");
        }
        
    }

    IdNode parseIdentifier() throws ParseException {
        Token id = nextToken();
        if (id.type != TokenType.Identifier) {
            throw new ParseException("Invalid id token: " + id.toString());
        }
        return new IdNode(id);
    }

    Number parseNumber() throws ParseException {
        Token number = nextToken();
        if (number.type != TokenType.Integer) {
            throw new ParseException("Invalid integer token: " + number.toString());
        }
        try {
            int value = Integer.parseInt(number.literal);
            return new Number(number, value);
        } catch(NumberFormatException e) {
            throw new ParseException("Cannot parse value " + number.literal + " as integer.");
        }
    }

    Bool parseBoolean() throws ParseException {
        Token b = nextToken();
        if (b.type != TokenType.True && b.type != TokenType.False) {
            throw new ParseException("Invalid bool token: " + b.toString());
        }
        return new Bool(b, b.type==TokenType.True);
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
        return tokens.get(index);
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