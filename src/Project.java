package com.rnee.monkey;

import com.rnee.monkey.Lexer;
import com.rnee.monkey.Token;
import com.rnee.monkey.TokenType;

/**
* Temp entry point
*
* @author	René van den Berg
* @version	1
*/
class Project {
    public static void main(String[] args) {
        System.out.println("Hi " + String.join(" ", args));

        String example = "let five = 5;\nlet ten = 10;\nlet add = fn(x, y) {\nx + y;\n};\n\nlet result = add(five, ten);";
        Lexer lexer = new Lexer(example);
        Token t = lexer.nextToken();

        System.out.println("Lexed succesfully: " + t);
    }
}