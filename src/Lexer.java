package com.rnee.monkey;

/**
* The lexer converts sourcecode into an array of tokens.
*
* @author	René van den Berg
* @version	1
*/
class Lexer {
    String source;

    /**
    * Construct a lexer to tokenize sourcecode.
    *
    * @param	sourcecode	a string of monkey sourcecode
    */
    Lexer(String sourcecode) {
        this.source = sourcecode;
    }

    /**
    * Parse the next token in the source code. WIll return tokens 
    * until it reaches end-of-file. After end-of-file the Lexer will keep 
    * returning EOF tokens.
    *
    * @return		next token in sourcecode
    */
    public Token nextToken() {
        if (this.source.startsWith("let")) {
            return new Token(TokenType.Let, "let");
        } else {
            return new Token(TokenType.Illegal, "It truly was nonsense.");
        }
    }
}



