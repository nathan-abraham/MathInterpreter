package src.nodes;

import src.*;
import src.errors.*;

public class DivNode extends Node {
    public Node nodeA;
    public Node nodeB;

    public DivNode(Node a, Node b) {
        nodeA = a;
        nodeB = b;
    }

    public String toString() {
        if (nodeA != null && nodeB != null)
            return "(" + nodeA.toString() + "/" + nodeB.toString() + ")";
        return "";
    }
}
