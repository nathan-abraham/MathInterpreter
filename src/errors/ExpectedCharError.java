package src.errors;

import src.*;
import src.nodes.*;

public class ExpectedCharError extends Error {
    public ExpectedCharError(Position posStart, Position posEnd, String details) {
        super(posStart, posEnd, "Expected Character", details);
    }
}
