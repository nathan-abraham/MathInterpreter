package src.nodes;

import java.util.ArrayList;

import src.*;
import src.errors.*;

public class IfNode extends Node {
    public ArrayList<Node> conditions;
    public ArrayList<Node> expressions;
    public ParseResult elseCase;
    public Position posStart;
    public Position posEnd;

    public IfNode(ArrayList<Node> c, ArrayList<Node> e, ParseResult elseCase) {
        conditions = c;
        expressions = e;
        this.elseCase = elseCase;
    }

    public String toString() {
        String result = "if (" + conditions.get(0).toString() + ") ";
        for (int i = 1; i < conditions.size(); i++) {
            result += "else if (" + conditions.get(i).toString() + ") ";
        }
        return result;
    }
}
