package rnee.monkey;

/**
* Bool Syntax Tree Node
*
* @author	René van den Berg
* @version	1
*/
class Bool extends Expression {
    boolean value;
    Bool(Token t, boolean value){
        super(t);
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof Bool)) {
            return false;
        }
        Bool b = (Bool) other;
        return value == b.value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}