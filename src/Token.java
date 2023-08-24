package com.rnee.monkey;

/**
* Token contains the type and text of a piece of source code.
*
* @author	René van den Berg
* @version	1
*/
class Token {
    TokenType type;
    String literal;

    Token(TokenType type, String literal) {
        this.type = type;
        this.literal = literal;
    }

    @Override
    public String toString() {
        return "" + this.type + "[\"" + this.literal + "\"]";
    }
}