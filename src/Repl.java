package rnee.monkey;

import java.util.ArrayList;
import java.io.Console;
import rnee.monkey.Lexer;

/**
* Provides REPL for Monkey.
*
* @author	René van den Berg
* @version	1
*/
class Repl {
    static String BR = "-".repeat(80)+"\n";
    static String FIRSTLINE = "> ";
    static String OTHERLINES = "| ";
    static String EOF = "EOF";

    ArrayList<ArrayList<String>> contexts;
    ArrayList<String> c;
    int contextId;
    int selectedLine;
    Console v;

    boolean running;

    Repl(){ 
        contextId = 0;
        selectedLine = 0;
        running = true;

        v = System.console();

        contexts = new ArrayList();
        c = createContext();
        contexts.add(c);
    }

    void start() {
        v.printf("%s\nMonkey REPL - type EOF to evaluate previous lines. \n%s", BR, BR);
        while(running) {
            if (selectedLine==0) {
                printContext();
            }

            readLine();

            if (contextClosed()) {
                evaluateContext();
                addContext();
                nextContext();
            }
        }
    }

    private void addContext() {
        contexts.add(createContext());
    }

    private void nextContext() {
        contextId += 1;
        c = contexts.get(contextId);
        selectedLine = c.size();
    }

    private boolean contextClosed() {
        return c.get(c.size()-1).trim().equals(EOF);
    }

    private void evaluateContext() {
        String source = String.join("\n", c.subList(0,c.size()-1));
        Lexer l = new Lexer(source);
        
        ArrayList<Token> tl = new ArrayList();
        Token t = new Token(TokenType.Illegal, "");
        while(t.type != TokenType.EOF) {
            t = l.nextToken();
            tl.add(t);
        }
        
        Parser p = new Parser(tl);
        ArrayList<Statement> sl = p.parse();
        for (Statement s : sl) {
            System.out.println(s);
        }

    }

    private void printContext() {
        v.printf("Selected context %d out of %d.\n", contextId+1, contexts.size());
        for (int line = 0; line < c.size(); line++) {
            String prefix = OTHERLINES;
            if (line==selectedLine) {
                prefix = FIRSTLINE;
            }
            v.printf("%d %s %s\n", contextId+1, prefix, c.get(line));
        }
    }

    private void readLine() {
        String str = v.readLine("%d %s", contextId+1, FIRSTLINE);
        addLine(str);
        nextLine();
    }

    private ArrayList<String> createContext() {
        ArrayList<String> l = new ArrayList();
        return l;
    }

    private void nextLine() {
        selectedLine += 1;
    }

    private void addLine(String s) {
        c.add(s);
    }
    
}