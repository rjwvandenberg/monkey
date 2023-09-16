package rnee.monkey;

class InvalidStatement extends Statement {
    InvalidStatement(Token t) {
        super(t);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof InvalidStatement)) {
            return false;
        }
        InvalidStatement i = (InvalidStatement)other;
        return token.equals(i.token);
    }
}