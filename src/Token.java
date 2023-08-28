package rnee.monkey;

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
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof Token)) {
            return false;
        }
        Token t = (Token)other;
        return this.type == t.type;
    }

    @Override
    public String toString() {
        return "" + this.type + "[\"" + this.literal + "\"]";
    }
}