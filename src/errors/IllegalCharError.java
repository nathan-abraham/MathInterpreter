package src.errors;

import src.*;
import src.nodes.*;

public class IllegalCharError extends Error {
    public IllegalCharError(Position posStart, Position posEnd, String details) {
        super(posStart, posEnd, "Illegal Character", details);
    }
}
