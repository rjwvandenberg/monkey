package rnee.monkey;

/**
* BinaryExpression contains an Token refering to the operator and a left and right side expression the operator is applied to.
*
* @author	René van den Berg
* @version	1
*/
class BinaryExpression extends Expression {
    Expression left;
    Expression right;

    BinaryExpression(Token t, Expression left, Expression right) {
        super(t);
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof BinaryExpression)) {
            return false;
        }
        BinaryExpression b = (BinaryExpression)other;
        return tokenType().equals(b.tokenType()) && left.equals(b.left) && right.equals(b.right);
    }

    @Override
    public String toString() {
        return "(" + left + " " + tokenLiteral() + " " + right + ")";
    }

}