package rnee.monkey;

class LetNode extends Statement {
    IdNode name;
    Expression value;
    LetNode(Token literal, IdNode name, Expression value) {
        super(literal);
        this.name = name;
        this.value = value;
    }
}