package rnee.monkey;

/**
* Statement Syntax Tree Node
*
* @author	René van den Berg
* @version	1
*/
abstract class Statement extends Node {
    Statement(Token token) {
        super(token);
    }

    public abstract boolean equals(Object other);

    public abstract String toString();
}