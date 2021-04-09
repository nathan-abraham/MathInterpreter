package src;

import src.nodes.*;
import src.errors.*;

public class Token {
    public String type;
    public double value = 0;
    public Position posStart;
    public Position posEnd;
    public String name;

    public Token(String type, Position posStart) {
        this.type = type;
        this.posStart = posStart.copy();
        posEnd = posStart.copy();
        posEnd.advance(Lexer.NULL_STRING);
    }

    public Token(String type, String value, Position posStart, Position posEnd) {
        this.type = type;
        this.name = value;
        this.posStart = posStart.copy();
        posEnd = posStart.copy();
        posEnd.advance(Lexer.NULL_STRING);
        posEnd = posEnd.copy();
    }

    public Token(String type, double value, Position posStart) {
        this.type = type;
        this.value = value;
        this.posStart = posStart.copy();
        posEnd = posStart.copy();
        posEnd.advance(Lexer.NULL_STRING);
    }

    public Token(String type, Position posStart, Position posEnd) {
        this.type = type;
        this.posStart = posStart.copy();
        posEnd = posStart.copy();
        posEnd.advance(Lexer.NULL_STRING);
        posEnd = posEnd.copy();
    }

    public boolean matches(String tokType, String keyword) {
        return type.equals(tokType) && name.equals(keyword);
    }

    public String toString() {
        if (type.equals(Lexer.TT_FLOAT) || type.equals(Lexer.TT_INT)) {
            if (value != 0) {
                return type + ":" + value;
            }
        } else if (type.equals(Lexer.TT_KEYWORD) || type.equals(Lexer.TT_IDENTIFIER)) {
            return type + ":" + name;
        }
        return type;
    }
}
