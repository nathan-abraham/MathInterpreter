package src.nodes;

import src.*;
import src.errors.*;

public class SubNode extends Node {
    public Node nodeA;
    public Node nodeB;

    public SubNode(Node a, Node b) {
        nodeA = a;
        nodeB = b;
    }

    public String toString() {
        if (nodeA != null && nodeB != null)
            return "(" + nodeA.toString() + "-" + nodeB.toString() + ")";
        return "";
    }
}
