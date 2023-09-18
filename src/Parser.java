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
        // operation(node,nextoperation)
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
            // Unary number expressions
            // Number prefixes replace with  (0-5) , illegal until parentheses are implemented
            // Need to parse peekToken(pos+1) or combine this token with the next one
            // Or handle it in parseNumber? But then -(5*3) is not allowed?
            case Plus:
            case Minus: {
                throw new ParseException("Expression PREFIX - and + are illegal until parentheses are implemented.");
                // break;
            }
            // Boolean expressions
            case False: 
            case True: {
                throw new ParseException("Boolean expressions not implemented: " + nextToken());
                // break;
            }
            // Unary boolean expression
            case Not: {
                throw new ParseException("Unary boolean Not ! not implemented.");
                // break;
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
            case Identifier: {
                throw new ParseException("Unary operators not implemented for Identifier INFIX");
            }
            case Integer: {
                throw new ParseException("Unary operators not implemented for Number INFIX");
            }
            // binary expressions
            case Multiply:
            case Divide:
            case Lesser:
            case Greater:
            case Equals:
            case NotEquals:
            // Unary/binary as infixes number expressions
            // -+5 
            case Plus:
            case Minus: {
                // NumberExpression and expect postfix to add to this expression.
                throw new ParseException("YAP");
            }
            // Boolean expressions
            case False: 
            case True: {
                throw new ParseException("Boolean expressions not implemented: " + nextToken());
                // break;
            }
            // Unary boolean expression
            case Not: {
                throw new ParseException("Unary boolean Not ! not implemented.");
                // break;
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
            case Else: {
                if (e.token.type == TokenType.If) {
                    throw new ParseException("Expression else not implemented");
                } else {
                    throw new ParseException("Expression else only permitted after expression if");
                }
            }
            // If expression
            case If: {
                throw new ParseException("If not impelemnted");
                // break;
            }
            // End statement
            case Semicolon:{
                // do not read semicolon token as it is part of the syntax surrounding this expression.
                return e;
            }
            case RParen:
            case RBrace: 
            // Illegal INFIXes
            case Assign:
            case Comma:
                throw new ParseException("Expected expression, found illegal INFIX token " + nextToken());
            case Let:
            case Return:
                throw new ParseException("Expected expression, found statement token " + nextToken());
            case EOF: {
                return e;
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