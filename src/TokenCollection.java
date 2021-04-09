package src;

import src.nodes.*;
import src.errors.*;
import src.errors.Error;
import java.util.ArrayList;

public class TokenCollection {
    public ArrayList<Token> tokens = new ArrayList<Token>();
    public Error error;

    public TokenCollection(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public TokenCollection(Token token) {
        tokens.add(token);
    }

    public TokenCollection(Error error) {
        this.error = error;
    }

    public TokenCollection(ArrayList<Token> tokens, Error error) {
        this.tokens = tokens;
        this.error = error;
    }
}
