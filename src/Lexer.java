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
        //Lets make whitespace illegal for now
        //skipWhitespace();
        
        if (position >= source.length()) {
            return new Token(TokenType.EOF, "");
        }

        Token t;        
        if ((t = startsWith(TokenType.Let)) != null) {}
        else if ((t = startsWith(TokenType.Assign)) != null) {}
        else if ((t = startsWith(TokenType.Plus)) != null) {}
        else if ((t = startsWith(TokenType.LParen)) != null) {}
        else if ((t = startsWith(TokenType.RParen)) != null) {}
        else if ((t = startsWith(TokenType.LBrace)) != null) {}
        else if ((t = startsWith(TokenType.RBrace)) != null) {}
        else if ((t = startsWith(TokenType.Semicolon)) != null) {}
        else if ((t = startsWith(TokenType.Comma)) != null) {}
        else {
            t = new Token(TokenType.Illegal, source.substring(position, position+1));
            position += 1;
        }
        return t;
    }

    Token startsWith(TokenType t) {
        if (source.startsWith(t.asString(), position)) {
            return makeToken(t);
        } else {
            return null;
        }
    }

    Token makeToken(TokenType t) {
        int start = position;
        position += t.asString().length();
        return new Token(t, source.substring(start, position));
    }

    void skipWhitespace() {
        while (position < source.length() && Character.isWhitespace(source.charAt(position))) {
            position++;
        }
    }
}



