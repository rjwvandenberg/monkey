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
    Let
}