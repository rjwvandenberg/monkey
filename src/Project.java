package com.rnee.monkey;

import com.rnee.monkey.Lexer;

/**
* Temp entry point
*
* @author	René van den Berg
* @version	1
*/
class Project {
    public static void main(String[] args) {
        System.out.println("Hi " + String.join(" ", args));

        boolean lexed = Lexer.analyse("Sourcecode");

        for (int i = 0; i<4; i++) {
            System.out.println(i);
        }

        System.out.println("Lexed succesfully: " + lexed);
    }
}
