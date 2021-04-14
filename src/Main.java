package src;

import src.nodes.*;
import src.errors.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        SymbolTable st = new SymbolTable();
        st.symbols.put("true", new MyNumber(1));
        st.symbols.put("false", new MyNumber(0));
        while (true) {
            System.out.print("math > ");
            String text = scan.nextLine();

            if (text.equals("quit") || text.equals("exit")) {
                break;
            }

            MyNumber value = run("<stdin>", text, st);
            if (value == null) {
                continue;
            }
            System.out.println(value);
        }
        scan.close();
    }

    public static MyNumber run(String filename, String text, SymbolTable st) {
        Lexer lexer = new Lexer(filename, text);
        TokenCollection tkc = lexer.makeTokens();
        if (tkc.error != null) {
            System.out.println(tkc.error);
            return null;
        }

        Parser parser = new Parser(tkc);
        ParseResult tree = parser.parse();
        if (tree.error != null) {
            System.out.println(tree.error);
            return null;
        }
        if (tree == null || tree.toString() == "") {
            return null;
        }
        Interpreter interpreter = new Interpreter(st);
        MyNumber value = interpreter.visit(tree.node);
        return value;
    }
}
