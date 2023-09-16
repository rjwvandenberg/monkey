package rnee.monkey;

import rnee.monkey.Token;

/**
* Expression Syntax Tree Node.
*
* @author	René van den Berg
* @version	1
*/
abstract class Expression extends Node {
    Expression(Token token) {   
        super(token);
    }

    public abstract boolean equals(Object other);

    public abstract String toString();
}