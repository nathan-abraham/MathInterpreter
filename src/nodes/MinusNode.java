package src.nodes;

import src.*;
import src.errors.*;

public class MinusNode extends Node {
    public Node node;

    public MinusNode(Node a) {
        node = a;
    }

    public String toString() {
        if (node != null)
            return "(-" + node.toString() + ")";
        return "";
    }
}
