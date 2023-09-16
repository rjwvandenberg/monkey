package rnee.monkey;

class LetNode extends Statement {
    IdNode name;
    Expression value;
    LetNode(Token literal, IdNode name, Expression value) {
        super(literal);
        this.name = name;
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
        if (!(other instanceof LetNode)) {
            return false;
        }
        LetNode l = (LetNode)other;
        return name.equals(l.name) && value.equals(l.value);
    }

    @Override
    public String toString() {
        return "Letnode[" + tokenLiteral() + "," + name + "," + value + "]";
    }
}