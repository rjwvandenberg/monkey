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
    Minus,
    Multiply,
    Divide,
    Lesser,
    Greater,

    Equals,
    NotEquals,
    Not,
    
    // Delimiters
    Comma,
    Semicolon,
    LParen,
    RParen,
    LBrace,
    RBrace,

    // Keywords
    Function,
    Let,

    True,
    False,

    If,
    Else,
    Return;

    /**
    * Return token string for non-helper and non-literals. (Could do polymorphism witch asString() impl for each enum field above and abstract method defined on TokenType)
    * TODO: Exception out non keywords as there won't be a string representation available for them.
    * 
    * @return	tokens as strings, helpers and literals as error message (this makes error messages valid code, should probably just do exceptions instead)
    */
    String asString() {
        switch(this) {
            case Assign: return "=";
            case Plus: return "+";
            case Minus: return "-";
            case Multiply: return "*";
            case Divide: return "/";
            case Lesser: return "<";
            case Greater: return ">";

            case Equals: return "==";
            case NotEquals: return "!=";
            case Not: return "!";

            case Comma: return ",";
            case Semicolon: return ";";
            case LParen: return "(";
            case RParen: return ")";
            case LBrace: return "{";
            case RBrace: return "}";

            case Function: return "fn";
            case Let: return "let";

            case True: return "true";
            case False: return "false";

            case If: return "if";
            case Else: return "else";
            case Return: return "return";
            default: return "" + this + " is not implemented. Should not asString() helpers or literals.";
        } 
    }

    static TokenType lookupKeyword(String token) {
        if (token.equals(Function.asString())) { return Function; }
        else if (token.equals(Let.asString())) { return Let; }
        return null;
    }
}