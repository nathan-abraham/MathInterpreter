package src.nodes;

import src.*;
import src.errors.*;

public class CompNode extends Node {
    public Node nodeA;
    public Node nodeB;
    public Token opTok;

    public CompNode(Node a, Token opTok, Node b) {
        nodeA = a;
        this.opTok = opTok;
        nodeB = b;
    }

    public String toString() {
        if (nodeA != null && nodeB != null)
            return "(" + nodeA.toString() + " " + opTok.toString() + " " + nodeB.toString() + ")";
        return "";
    }
}
