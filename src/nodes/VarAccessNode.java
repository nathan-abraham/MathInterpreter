package src.nodes;

import src.*;
import src.errors.*;

public class VarAccessNode extends Node {
    public Token varName;
    public Position posStart;
    public Position posEnd;

    public VarAccessNode(Token vn) {
        varName = vn;
        posStart = varName.posStart;
        posEnd = varName.posEnd;
    }

    public String toString() {
        return "(" + varName.name + ")";
    }
}
