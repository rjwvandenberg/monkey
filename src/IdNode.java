package rnee.monkey;

/**
* Identifier Syntax Tree Node.
*
* @author	René van den Berg
* @version	1
*/
class IdNode extends Expression {
    IdNode(Token literal) {
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
        if (!(other instanceof IdNode)) {
            return false;
        }
        IdNode i = (IdNode)other;
        return token.equals(i.token);
    }

    @Override
    public String toString() {
        return tokenLiteral();
    }
}