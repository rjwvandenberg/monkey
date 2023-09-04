package rnee.monkey;

/**
* The lexer converts sourcecode into an array of tokens.
*
* @author	René van den Berg
* @version	1
*/
public class Lexer {
    String source;
    int position;

    /**
    * Construct a lexer to tokenize sourcecode.
    *
    * @param	sourcecode	a string of monkey sourcecode
    * @param    position    start index of next token
    */
    Lexer(String sourcecode) {
        source = sourcecode;
        position = 0;
    }

    /**
    * Parse the next token in the source code. WIll return tokens 
    * until it reaches end-of-file. After end-of-file the Lexer will keep 
    * returning EOF tokens.
    *
    * @return		next token in sourcecode
    */
    Token nextToken() {
        skipWhitespace();
        
        if (position >= source.length()) {
            return new Token(TokenType.EOF, "");
        }

        Token t;        
        // One character operators and delimiters
        if ((t = startsWith(TokenType.Assign)) != null) {}
        else if ((t = startsWith(TokenType.Plus)) != null) {}
        else if ((t = startsWith(TokenType.LParen)) != null) {}
        else if ((t = startsWith(TokenType.RParen)) != null) {}
        else if ((t = startsWith(TokenType.LBrace)) != null) {}
        else if ((t = startsWith(TokenType.RBrace)) != null) {}
        else if ((t = startsWith(TokenType.Semicolon)) != null) {}
        else if ((t = startsWith(TokenType.Comma)) != null) {}
        // Keywords and literals
        else if ((t = readInteger()) != null) {}
        else if ((t = readAlphabetic()) != null) {}

        // Seperated null check, incase logic above is incorrect
        if (t == null) {
            t = new Token(TokenType.Illegal, source.substring(position, position+1));
            position += 1;
        }
        return t;
    }

    private Token readInteger() {
        int endPosition = position;
        while (endPosition < source.length() && Character.isDigit(source.charAt(endPosition))) {
            endPosition += 1;
        }
        if (endPosition > position ) {
            Token t = new Token(TokenType.Integer, source.substring(position, endPosition));
            position = endPosition;
            return t;
        } else {
            return null;
        }
    }

    /**
    * Read an alphabetic string and return it's token. The token can be a keyword (Let or Function) 
    * or an Identifier.
    *
    * @return		token Let, Function or Identifier
    */
    private Token readAlphabetic() {
        int endPosition = position;
        while (endPosition < source.length() && Character.isAlphabetic(source.charAt(endPosition))) {
            endPosition += 1;
        }
        String literal = source.substring(position, endPosition);
        position = endPosition;
        TokenType tt = TokenType.lookupKeyword(literal);
        if (tt == null) {
            tt = TokenType.Identifier;
        }
        return new Token(tt, literal);
    }

    private Token startsWith(TokenType t) {
        if (source.startsWith(t.asString(), position)) {
            return makeToken(t);
        } else {
            return null;
        }
    }

    private Token makeToken(TokenType t) {
        int start = position;
        position += t.asString().length();
        return new Token(t, source.substring(start, position));
    }

    private void skipWhitespace() {
        while (position < source.length() && Character.isWhitespace(source.charAt(position))) {
            position++;
        }
    }
}



