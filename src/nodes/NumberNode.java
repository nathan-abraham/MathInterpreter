package src.nodes;

import src.*;
import src.errors.*;

public class NumberNode extends Node {
    public double value;

    public NumberNode(double val) {
        value = val;
    }

    public String toString() {
        return "" + value;
    }

    public double getValue() {
        return value;
    }
}
