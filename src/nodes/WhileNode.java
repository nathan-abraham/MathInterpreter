package src.nodes;

import src.*;
import src.errors.*;

public class WhileNode extends Node {
    public Node condition;
    public Node body;

    public WhileNode(Node c, Node b) {
        condition = c;
        body = b;
    }

    public String toString() {
        return "";
    }
}
