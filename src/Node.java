package rnee.monkey;

import rnee.monkey.Token;

/**
* Generic Syntax Tree node containing it's corresponding token.
*
* @author	René van den Berg
* @version	1
*/
abstract class Node {
    Token token;
    Node(Token token) {
        this.token = token;
    }

    String tokenLiteral() {
        return token.literal;
    }
}