package rnee.monkey;

import java.util.Optional;

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

        Optional<Token> t = readOperator();
        if (t.isEmpty()) {
            t = readInteger();
        }
        if (t.isEmpty()) {
            t = readAlphabetic();
        }
        
        if(t.isEmpty()) {
            return makeIllegalToken();
        } else {
            return t.get();
        }
    }

    /**
    * Detect operator or delimitor.
    * 
    * @return		Optional of detected token type, empty otherwise.
    */
    private Optional<Token> readOperator() {
        TokenType tt = null;
        // Two character operators (before one character to prevent collision, e.g. == and =)
        if (startsWith(TokenType.Equals)) { tt = TokenType.Equals; }
        else if (startsWith(TokenType.NotEquals)) { tt = TokenType.NotEquals; }
        // One character operators and delimiters
        else if (startsWith(TokenType.Assign)) { tt = TokenType.Assign; }
        else if (startsWith(TokenType.Plus)) { tt = TokenType.Plus; }
        else if (startsWith(TokenType.Minus)) { tt = TokenType.Minus; }
        else if (startsWith(TokenType.Multiply)) { tt = TokenType.Multiply; }
        else if (startsWith(TokenType.Divide)) { tt = TokenType.Divide; }
        else if (startsWith(TokenType.Lesser)) { tt = TokenType.Lesser; }
        else if (startsWith(TokenType.Greater)) { tt = TokenType.Greater; }
        else if (startsWith(TokenType.Not)) {tt = TokenType.Not; }
        else if (startsWith(TokenType.LParen)) { tt = TokenType.LParen; }
        else if (startsWith(TokenType.RParen)) { tt = TokenType.RParen; }
        else if (startsWith(TokenType.LBrace)) {tt = TokenType.LBrace; }
        else if (startsWith(TokenType.RBrace)) { tt = TokenType.RBrace; }
        else if (startsWith(TokenType.Semicolon)) { tt = TokenType.Semicolon; }
        else if (startsWith(TokenType.Comma)) { tt = TokenType.Comma; }

        Token t = null;
        if (tt != null) {
            t = makeToken(tt);
        }
        return Optional.ofNullable(t);
    }

    private Optional<Token> readInteger() {
        int start = position;
        skipDigits();

        Token t = null;
        if (position > start) {
            t = new Token(TokenType.Integer, source.substring(start, position));
        }
        return Optional.ofNullable(t);
    }

    /**
    * Read an alphabetic string and return it's token. The token can be a keyword (Let or Function) 
    * or an Identifier.
    *
    * @return		token Let, Function or Identifier
    */
    private Optional<Token> readAlphabetic() {
        int start = position;
        skipAlphabetic();
        
        Token t = null;
        if (position > start) {
            String literal = source.substring(start, position);

            Optional<TokenType> tt = TokenType.lookupKeyword(literal);
            if (tt.isEmpty()) {
                t = new Token(TokenType.Identifier, literal);
            } else {
                t = new Token(tt.get(), literal);
            }
        }
        return Optional.ofNullable(t);        
    }

    private boolean startsWith(TokenType t) {
        return source.startsWith(t.asString(), position);
    }

    /**
    * Only use with operators, keywords. Do not use with literals.
    * Makes token and advances position.
    *
    * @param	t	non-literal tokentypes
    * @return		token with tokentype t
    */
    private Token makeToken(TokenType t) {
        int start = position;
        position += t.asString().length();
        return new Token(t, source.substring(start, position));
    }

    private Token makeIllegalToken() {
        Token token = new Token(TokenType.Illegal, source.substring(position, position+1));
        position += 1;
        return token;
    }

    private void skipDigits() {
        while (position < source.length() && Character.isDigit(source.charAt(position))) {
            position += 1;
        }
    }

    private void skipAlphabetic() {
        while (position < source.length() && Character.isAlphabetic(source.charAt(position))) {
            position += 1;
        }
    }

    private void skipWhitespace() {
        while (position < source.length() && Character.isWhitespace(source.charAt(position))) {
            position += 1;
        }
    }
}



