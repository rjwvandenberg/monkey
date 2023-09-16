package rnee.monkey;

import java.util.ArrayList;

/**
* Else Syntax Tree Node
*
* @author	René van den Berg
* @version	1
*/
class ElseNode extends Expression {
    ArrayList<Statement> block;
    ElseNode(Token t, ArrayList<Statement> block) {
        super(t);
        this.block = block;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof ElseNode)) {
            return false;
        }
        ElseNode e = (ElseNode)other;
        return block.equals(e.block);
    }

    @Override
    public String toString() {
        return "else { " + block.stream().map(s->s.toString()).reduce("", (l,s)->l+s) + " }";
    }
}