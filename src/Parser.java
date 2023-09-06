package rnee.monkey;

import java.util.ArrayList;

/**
* Create syntax trees by parsing Token strings.
*
* @author	René van den Berg
* @version	1
*/
class Parser {
    ArrayList<Token> tokens;
    Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    ArrayList<Statement> parse() {
        ArrayList<Statement> p = new ArrayList();

        return p;
    }
}