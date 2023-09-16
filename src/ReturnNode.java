package rnee.monkey;

/**
* Return Syntax Tree Node.
*
* @author	René van den Berg
* @version	1
*/
class ReturnNode extends Statement {
    Expression expr;

    ReturnNode(Token t, Expression expr) {
        super(t);
        this.expr = expr;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof ReturnNode)) {
            return false;
        } 
        ReturnNode r = (ReturnNode)other;
        return expr.equals(r.expr);
    }

    @Override
    public String toString() {
        return "return " + expr + ";";
    }
}