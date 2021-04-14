package src.nodes;

import src.*;
import src.errors.*;

public class ForNode extends Node {
    public Token varName;
    public Node startValue;
    public Node endValue;
    public Node stepValue;
    public Node body;

    public ForNode(Token vn, Node start, Node end, Node step, Node b) {
        varName = vn;
        startValue = start;
        endValue = end;
        stepValue = step;
        body = b;
    }

    public String toString() {
        return "";
    }
}
