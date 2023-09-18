package rnee.monkey;

/**
* Number Syntax Tree Node.
*
* @author	René van den Berg
* @version	1
*/
class Number extends Expression {
    int value;
    Number(Token literal, int value) {
        super(literal);
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof Number)) {
            return false;
        }
        Number n = (Number)other;
        return token.equals(n.token);
    }

    @Override
    public String toString() {
        return "" + value;
    }
}