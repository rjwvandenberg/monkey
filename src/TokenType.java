package rnee.monkey;

/**
* Types of Tokens.
*
* @author	René van den Berg
* @version	1
*/
enum TokenType {
    // Helper
    Illegal,
    EOF,

    // Literals
    Identifier,
    Integer,
    
    // Operators
    Assign,
    Plus,
    
    // Delimiters
    Comma,
    Semicolon,
    LParen,
    RParen,
    LBrace,
    RBrace,

    // Keywords
    Function,
    Let;

    String asString() {
        switch(this) {
            case Assign: return "=";
            case Plus: return "+";
            case Comma: return ",";
            case Semicolon: return ";";
            case LParen: return "(";
            case RParen: return ")";
            case LBrace: return "{";
            case RBrace: return "}";
            case Function: return "fn";
            case Let: return "let";
            default: return "" + this + " is not implemented. Should not asString() helpers or literals.";
        } 
    }
}