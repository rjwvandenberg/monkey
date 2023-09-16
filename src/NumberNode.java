package rnee.monkey;
class NumberNode extends Expression {
    NumberNode(Token literal) {
        super(literal);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof NumberNode)) {
            return false;
        }
        NumberNode n = (NumberNode)other;
        return token.equals(n.token);
    }
}