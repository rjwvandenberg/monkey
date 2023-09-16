package rnee.monkey;

/**
* Single expression statement Syntax Tree Node.
* Effectively works as a shorthand for return statement without context to return to?
*
* @author	René van den Berg
* @version	1
*/
class ExpressionStatement extends Statement {
    Expression expr;

    ExpressionStatement(Token t, Expression expr) {
        super(t);
        this.expr = expr;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof ExpressionStatement)) {
            return false;
        }
        ExpressionStatement e = (ExpressionStatement)other;
        return expr.equals(e.expr);
    }

    @Override
    public String toString() {
        return expr.toString() + ";";
    }
}