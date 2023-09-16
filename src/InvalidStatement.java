package rnee.monkey;

/**
* InvalidStatement Syntax Tree Node.
*
* @author	René van den Berg
* @version	1
*/
class InvalidStatement extends Statement {
    InvalidStatement(Token t) {
        super(t);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof InvalidStatement)) {
            return false;
        }
        InvalidStatement i = (InvalidStatement)other;
        return token.equals(i.token);
    }

    @Override
    public String toString() {
        return "<Invalid Statement>";
    }
}