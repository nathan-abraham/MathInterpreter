package src.nodes;

import src.*;
import src.errors.*;

public class PlusNode extends Node {
    public Node node;

    public PlusNode(Node a) {
        node = a;
    }

    public String toString() {
        if (node != null)
            return "(+" + node.toString() + ")";
        return "";
    }
}
