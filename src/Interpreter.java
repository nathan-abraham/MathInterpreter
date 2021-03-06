package src;

import src.nodes.*;
import src.errors.*;

public class Interpreter {

    public SymbolTable st;

    public Interpreter(SymbolTable st) {
        this.st = st;
    }

    public MyNumber visit(Node n) {
        if (n instanceof NumberNode) {
            return visitNumberNode((NumberNode) n);
        } else if (n instanceof AddNode) {
            return visitAddNode((AddNode) n);
        } else if (n instanceof SubNode) {
            return visitSubNode((SubNode) n);
        } else if (n instanceof MulNode) {
            return visitMulNode((MulNode) n);
        } else if (n instanceof DivNode) {
            return visitDivNode((DivNode) n);
        } else if (n instanceof PlusNode) {
            return visitPlusNode((PlusNode) n);
        } else if (n instanceof MinusNode) {
            return visitMinusNode((MinusNode) n);
        } else if (n instanceof PowerNode) {
            return visitPowerNode((PowerNode) n);
        } else if (n instanceof VarAssignNode) {
            return visitVarAssignNode((VarAssignNode) n);
        } else if (n instanceof VarAccessNode) {
            return visitVarAccessNode((VarAccessNode) n);
        } else if (n instanceof CompNode) {
            return visitCompNode((CompNode) n);
        } else if (n instanceof LogOpNode) {
            return visitLogOpNode((LogOpNode) n);
        } else if (n instanceof NotNode) {
            return visitNotNode((NotNode) n);
        } else if (n instanceof IfNode) {
            return visitIfNode((IfNode) n);
        } else if (n instanceof ForNode) {
            return visitForNode((ForNode) n);
        } else if (n instanceof WhileNode) {
            return visitWhileNode((WhileNode) n);
        }
        return null;
    }

    public MyNumber visitNumberNode(NumberNode n) {
        return new MyNumber(n.value);
    }

    public MyNumber visitAddNode(AddNode n) {
        return new MyNumber(visit(n.nodeA).value + visit(n.nodeB).value);
    }

    public MyNumber visitSubNode(SubNode n) {
        return new MyNumber(visit(n.nodeA).value - visit(n.nodeB).value);
    }

    public MyNumber visitMulNode(MulNode n) {
        return new MyNumber(visit(n.nodeA).value * visit(n.nodeB).value);
    }

    public MyNumber visitDivNode(DivNode n) {
        return new MyNumber(visit(n.nodeA).value / visit(n.nodeB).value);
    }

    public MyNumber visitPlusNode(PlusNode n) {
        return visit(n.node);
    }

    public MyNumber visitMinusNode(MinusNode n) {
        return new MyNumber(-1 * visit(n.node).value);
    }

    public MyNumber visitPowerNode(PowerNode n) {
        return new MyNumber(Math.pow(visit(n.nodeA).value, visit(n.nodeB).value));
    }

    public MyNumber visitVarAssignNode(VarAssignNode n) {
        String varName = n.varName.name;
        MyNumber value = visit(n.node);
        st.set(varName, value);
        return value;
    }

    public MyNumber visitVarAccessNode(VarAccessNode n) {
        String varName = n.varName.name;
        MyNumber value = st.get(varName);
        return value;
    }

    public MyNumber visitCompNode(CompNode n) {
        if (n.opTok.type.equals(Lexer.TT_EE)) {
            int val = visit(n.nodeA).value == visit(n.nodeB).value ? 1 : 0;
            return new MyNumber(val);
        } else if (n.opTok.type.equals(Lexer.TT_NE)) {
            int val = visit(n.nodeA).value != visit(n.nodeB).value ? 1 : 0;
            return new MyNumber(val);
        } else if (n.opTok.type.equals(Lexer.TT_LT)) {
            int val = visit(n.nodeA).value < visit(n.nodeB).value ? 1 : 0;
            return new MyNumber(val);
        } else if (n.opTok.type.equals(Lexer.TT_LTE)) {
            int val = visit(n.nodeA).value <= visit(n.nodeB).value ? 1 : 0;
            return new MyNumber(val);
        } else if (n.opTok.type.equals(Lexer.TT_GT)) {
            int val = visit(n.nodeA).value > visit(n.nodeB).value ? 1 : 0;
            return new MyNumber(val);
        } else if (n.opTok.type.equals(Lexer.TT_GTE)) {
            int val = visit(n.nodeA).value >= visit(n.nodeB).value ? 1 : 0;
            return new MyNumber(val);
        }
        return null;
    }

    public MyNumber visitNotNode(NotNode n) {
        int val = visit(n.node).value == 1 ? 0 : 1;
        return new MyNumber(val);
    }

    public MyNumber visitLogOpNode(LogOpNode n) {
        if (n.opTok.matches(Lexer.TT_KEYWORD, "and")) {
            int val = intToBoolean(visit(n.nodeA).value) && intToBoolean(visit(n.nodeB).value) ? 1 : 0;
            return new MyNumber(val);
        } else if (n.opTok.matches(Lexer.TT_KEYWORD, "or")) {
            int val = intToBoolean(visit(n.nodeA).value) || intToBoolean(visit(n.nodeB).value) ? 1 : 0;
            return new MyNumber(val);
        }
        return null;
    }

    public boolean intToBoolean(double a) {
        if (a != 0)
            return true;
        return false;
    }

    public MyNumber visitIfNode(IfNode n) {
        for (int i = 0; i < n.conditions.size(); i++) {
            MyNumber conditionValue = visit(n.conditions.get(i));
            if (conditionValue.value != 0) {
                MyNumber exprValue = visit(n.expressions.get(i));
                return exprValue;
            }
        }
        if (n.elseCase != null) {
            MyNumber elseValue = visit(n.elseCase.node);
            return elseValue;
        }
        return null;
    }

    public MyNumber visitForNode(ForNode n) {
        MyNumber startValue = visit(n.startValue);
        MyNumber endValue = visit(n.endValue);
        MyNumber stepValue;
        if (n.stepValue != null) {
            stepValue = visit(n.stepValue);
        } else {
            stepValue = new MyNumber(1);
        }

        double i = startValue.value;
        if (stepValue.value >= 0) {
            while (i < endValue.value) {
                st.set(n.varName.name, new MyNumber(i));
                i += stepValue.value;
                visit(n.body);
            }
        } else {
            while (i > endValue.value) {
                st.set(n.varName.name, new MyNumber(i));
                i += stepValue.value;
                visit(n.body);
            }
        }
        return null;
    }

    public MyNumber visitWhileNode(WhileNode n) {
        MyNumber condition;
        while (true) {
            condition = visit(n.condition);
            if (condition.value == 0)
                break;
            visit(n.body);
        }
        return null;
    }
}
