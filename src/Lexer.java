package src;

import src.nodes.*;
import src.errors.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.Arrays;

public class Lexer {

    public String filename;
    public String text;
    public Position pos;
    public String currentChar;

    public static final String DIGITS = "0123456789";
    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String TT_INT = "INT";
    public static String TT_FLOAT = "FLOAT";
    public static String TT_PLUS = "PLUS";
    public static String TT_MINUS = "MINUS";
    public static String TT_MUL = "MUL";
    public static String TT_DIV = "DIV";
    public static String TT_LPAREN = "LPAREN";
    public static String TT_RPAREN = "RPAREN";
    public static String TT_EOF = "EOF";
    public static String TT_POWER = "POWER";
    public static String TT_KEYWORD = "KEYWORD";
    public static String TT_IDENTIFIER = "IDENTIFIER";
    public static String TT_EQ = "EQ";
    public static String TT_EE = "EE";
    public static String TT_NE = "NE";
    public static String TT_LT = "LT";
    public static String TT_GT = "GT";
    public static String TT_LTE = "LTE";
    public static String TT_GTE = "GTE";
    public static final String NULL_STRING = cts((char) 0);
    public static final String[] KEYWORDS = { "var", "and", "or", "not" };
    public static final String[] compExprOps = { TT_EE, TT_NE, TT_LT, TT_GT, TT_LTE, TT_GTE };

    public int tempCount = 0;

    public Lexer(String filename, String text) {
        this.filename = filename;
        this.text = text;
        pos = new Position(-1, 0, -1, filename, text);
        currentChar = NULL_STRING;
        advance();
    }

    public void advance() {
        pos.advance(currentChar);
        currentChar = pos.index < text.length() ? text.substring(pos.index, pos.index + 1) : NULL_STRING;
    }

    public static String cts(char c) {
        return String.valueOf(c);
    }

    public TokenCollection makeTokens() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        while (!currentChar.equals(NULL_STRING)) {
            if (currentChar.equals(" ") || currentChar.equals("\t")) {
                advance();
            } else if (DIGITS.contains(currentChar)) {
                tokens.add(makeNumber());
            } else if (LETTERS.contains(currentChar.toUpperCase())) {
                tokens.add(makeIdentifier());
            } else if (currentChar.equals("+")) {
                tokens.add(new Token(TT_PLUS, pos));
                advance();
            } else if (currentChar.equals("-")) {
                tokens.add(new Token(TT_MINUS, pos));
                advance();
            } else if (currentChar.equals("*")) {
                tokens.add(new Token(TT_MUL, pos));
                advance();
            } else if (currentChar.equals("/")) {
                tokens.add(new Token(TT_DIV, pos));
                advance();
            } else if (currentChar.equals("^")) {
                tokens.add(new Token(TT_POWER, pos));
                advance();
            } else if (currentChar.equals("(")) {
                tokens.add(new Token(TT_LPAREN, pos));
                advance();
            } else if (currentChar.equals(")")) {
                tokens.add(new Token(TT_RPAREN, pos));
                advance();
            } else if (currentChar.equals("!")) {
                TokenCollection temp = makeNotEquals();
                if (temp.error != null)
                    return new TokenCollection(new ArrayList<Token>(), temp.error);
                tokens.add(temp.tokens.get(0));
            } else if (currentChar.equals("=")) {
                tokens.add(makeEquals());
            } else if (currentChar.equals("<")) {
                tokens.add(makeLessThan());
            } else if (currentChar.equals(">")) {
                tokens.add(makeGreaterThan());
            } else {
                Position posStart = pos.copy();
                String c = currentChar;
                advance();
                return new TokenCollection(tokens, new IllegalCharError(posStart, pos, "\"" + c + "\""));
            }
        }
        tokens.add(new Token(TT_EOF, pos));
        return new TokenCollection(tokens);
    }

    public Token makeNumber() {
        String numStr = "";
        int dotCount = 0;

        while (!currentChar.equals(NULL_STRING) && (DIGITS + ".").contains(currentChar)) {
            if (currentChar.equals(".")) {
                if (dotCount == 1)
                    break;
                dotCount++;
                numStr += ".";
            } else {
                numStr += currentChar;
            }
            advance();
        }

        if (dotCount == 0) {
            return new Token(TT_INT, Integer.parseInt(numStr), pos);
        } else {
            return new Token(TT_FLOAT, Double.parseDouble(numStr), pos);
        }
    }

    public Token makeIdentifier() {
        String result = "";
        Position localPosStart = pos.copy();

        while (!currentChar.equals(NULL_STRING) && (LETTERS + DIGITS + "_").contains(currentChar.toUpperCase())) {
            result += currentChar;
            advance();
        }
        String tokType = Arrays.asList(KEYWORDS).contains(result) ? TT_KEYWORD : TT_IDENTIFIER;
        return new Token(tokType, result, localPosStart, pos);
    }

    public TokenCollection makeNotEquals() {
        Position localPosStart = pos.copy();
        advance();

        if (currentChar.equals("=")) {
            advance();
            return new TokenCollection(new Token(TT_NE, localPosStart, pos));
        }
        advance();
        return new TokenCollection(new ExpectedCharError(localPosStart, pos, "\'=\' after \'!\'"));
    }

    public Token makeEquals() {
        String tokType = TT_EQ;
        Position localPosStart = pos.copy();
        advance();

        if (currentChar.equals("=")) {
            advance();
            tokType = TT_EE;
        }
        return new Token(tokType, localPosStart, pos);
    }

    public Token makeLessThan() {
        String tokType = TT_LT;
        Position localPosStart = pos.copy();
        advance();

        if (currentChar.equals("=")) {
            advance();
            tokType = TT_LTE;
        }
        return new Token(tokType, localPosStart, pos);
    }

    public Token makeGreaterThan() {
        String tokType = TT_GT;
        Position localPosStart = pos.copy();
        advance();

        if (currentChar.equals("=")) {
            advance();
            tokType = TT_GTE;
        }
        return new Token(tokType, localPosStart, pos);
    }

    public static String arrows(String text, Position posStart, Position posEnd) {
        String result = "";

        int idxStart = Math.max(text.substring(0, posStart.index).lastIndexOf("\n"), 0);
        int idxEnd = text.substring(idxStart + 1).indexOf("\n");
        if (idxEnd < 0)
            idxEnd = text.length();

        int lineCount = posEnd.line - posStart.line + 1;
        for (int i = 0; i < lineCount; i++) {
            String line = text.substring(idxStart, idxEnd);
            int colStart = i == 0 ? posStart.col : 0;
            int colEnd = i == lineCount - 1 ? posEnd.col : line.length() - 1;

            result = result + (line + "\n");
            result += " " + new String(new char[colStart]).replace("\0", " ")
                    + new String(new char[colEnd - colStart]).replace("\0", "^");

            idxStart = idxEnd - 1;
            idxEnd = text.substring(idxStart + 1).indexOf("\n");
            if (idxEnd < 0)
                idxEnd = text.length();
        }
        return result.replace("\t", "");
    }
}
