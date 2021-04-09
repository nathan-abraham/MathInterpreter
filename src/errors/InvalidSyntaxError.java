package src.errors;

import src.*;
import src.nodes.*;

public class InvalidSyntaxError extends Error {
    public InvalidSyntaxError(Position posStart, Position posEnd, String details) {
        super(posStart, posEnd, "Invalid Syntax", details);
    }
}
