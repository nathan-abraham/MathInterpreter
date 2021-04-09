package src.nodes;

import src.*;
import src.errors.*;

public class NotNode extends Node {
    public Node node;

    public NotNode(Node a) {
        node = a;
    }

    public String toString() {
        if (node != null)
            return "(not" + node.toString() + ")";
        return "";
    }
}
