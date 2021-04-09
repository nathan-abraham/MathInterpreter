package src.nodes;

import src.*;
import src.errors.*;

public class VarAssignNode extends Node {
    public Token varName;
    public Node node;
    public Position posStart;
    public Position posEnd;

    public VarAssignNode(Token vn, Node b) {
        varName = vn;
        node = b;
        posStart = varName.posStart;
        posEnd = varName.posEnd;
    }

    public String toString() {
        return "(" + varName.toString() + ", " + node.toString() + ")";
    }
}
