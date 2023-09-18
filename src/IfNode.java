package rnee.monkey;

import java.util.ArrayList;

/**
* If Syntax Tree Node.
*
* @author	René van den Berg
* @version	1
*/
class IfNode extends Expression {
    Expression condition;
    ArrayList<Statement> block;
    
    IfNode(Token t, Expression condition, ArrayList<Statement> block) {
        super(t);
        this.condition = condition;
        this.block = block;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof IfNode)) {
            return false;
        } 
        IfNode i = (IfNode)other;
        return condition.equals(i.condition) && block.equals(i.block);
    }

    @Override
    public String toString() {
        return "if " + condition + " { " + block.stream().map(s->s.toString()).reduce("", (l,s)->l+s+" ") + " }";
    }
}