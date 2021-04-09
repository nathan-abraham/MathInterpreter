package src.nodes;

import src.*;
import src.errors.*;

public class Node {
    public double value;

    public String toString() {
        if (this instanceof AddNode) {
            return ((AddNode) this).toString();
        } else if (this instanceof SubNode) {
            return ((SubNode) this).toString();
        } else if (this instanceof MulNode) {
            return ((MulNode) this).toString();
        } else if (this instanceof DivNode) {
            return ((DivNode) this).toString();
        } else if (this instanceof NumberNode) {
            return ((NumberNode) this).toString();
        } else if (this instanceof PowerNode) {
            return ((PowerNode) this).toString();
        } else if (this instanceof VarAccessNode) {
            return ((VarAccessNode) this).toString();
        } else if (this instanceof VarAssignNode) {
            return ((VarAssignNode) this).toString();
        }
        return "fix this";
    }

    public double getValue() {
        return value;
    }
}
