package src.nodes;

import src.*;
import src.errors.*;

public class PowerNode extends Node {
    public Node nodeA;
    public Node nodeB;

    public PowerNode(Node a, Node b) {
        nodeA = a;
        nodeB = b;
    }

    public String toString() {
        return "(" + nodeA.toString() + "^" + nodeB.toString() + ")";
    }
}
