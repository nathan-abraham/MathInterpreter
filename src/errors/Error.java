package src.errors;

import src.*;
import src.nodes.*;

public class Error {
    public Position posStart;
    public Position posEnd;
    public String errorName;
    public String details;

    public Error(Position posStart, Position posEnd, String errorName, String details) {
        this.posStart = posStart;
        this.posEnd = posEnd;
        this.errorName = errorName;
        this.details = details;
    }

    public String toString() {
        String result = errorName + ": " + details;
        result += ", File " + posStart.filename + ", line " + (posStart.line + 1);
        return result;
    }
}
