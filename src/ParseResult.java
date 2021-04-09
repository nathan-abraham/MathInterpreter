package src;

import src.nodes.*;
import src.errors.*;
import src.errors.Error;

public class ParseResult {
    public Error error;
    public Node node;

    public int advanceCount = 0;

    public void registerAdvance() {
        advanceCount++;
    }

    public ParseResult register(ParseResult res) {
        advanceCount += res.advanceCount;
        if (res.error != null) {
            error = res.error;
        }
        return res;
    }

    public ParseResult success(Node node) {
        this.node = node;
        return this;
    }

    public ParseResult failure(Error e) {
        if (error == null || advanceCount == 0) {
            error = e;
        }
        return this;
    }
}
