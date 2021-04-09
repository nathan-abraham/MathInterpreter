package src;

import src.nodes.*;
import src.errors.*;
import java.util.HashMap;

public class SymbolTable {
    HashMap<String, MyNumber> symbols;
    SymbolTable parent;

    public SymbolTable() {
        symbols = new HashMap<String, MyNumber>();
    }

    public MyNumber get(String name) {
        MyNumber value = symbols.get(name);
        if (value == null && parent != null)
            return parent.get(name);
        return value;
    }

    public void set(String name, MyNumber value) {
        symbols.put(name, value);
    }

    public void remove(String name) {
        symbols.remove(name);
    }
}
