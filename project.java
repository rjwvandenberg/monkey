import com.rnee.monkey.Lexer;

class Project {
    public static void main(String[] args) {
        System.out.println("Hi " + String.join(" ", args));

        boolean lexed = Lexer.analyse("Sourcecode");

        System.out.println("Lexed succesfully: " + lexed);
    }
}