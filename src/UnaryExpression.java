package rnee.monkey;

class UnaryExpression extends Expression {
    Expression right;
    UnaryExpression(Token t, Expression right) {
        super(t);
        this.right = right;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof UnaryExpression)) {
            return false;
        }
        UnaryExpression u = (UnaryExpression)other;
        return tokenType().equals(u.tokenType()) && right.equals(u.right);
    }

    @Override
    public String toString() {
        return "(" + tokenLiteral() + right + ")";
    }
}