package src.nodes;

import src.*;
import src.errors.*;

public class LogOpNode extends Node {
    public Node nodeA;
    public Node nodeB;
    public Token opTok;

    public LogOpNode(Node a, Token ot, Node b) {
        nodeA = a;
        opTok = ot;
        nodeB = b;
    }

    public String toString() {
        if (nodeA != null && nodeB != null)
            return "(" + nodeA.toString() + " " + opTok.toString() + " " + nodeB.toString() + ")";
        return "";
    }
}
