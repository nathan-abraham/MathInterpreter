package src.nodes;

import src.*;
import src.errors.*;

public class AddNode extends Node {
    public Node nodeA;
    public Node nodeB;

    public AddNode(Node a, Node b) {
        nodeA = a;
        nodeB = b;
    }

    public String toString() {
        if (nodeA != null && nodeB != null)
            return "(" + nodeA.toString() + "+" + nodeB.toString() + ")";
        return "";
    }
}
