package rs.raf.m_stojanovic.pp.sentencec.semantic;

import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Atom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {

    private static final Map<String, Atom> GLOBALS = new HashMap<>();

    public static Atom declareGlobal(String name, Atom atom) {
        Atom previous = GLOBALS.get(name);
        if (previous != null)
            throw new RuntimeException("Already declared atom " + name);
        GLOBALS.put(name, atom);
        return atom;
    }

    private final List<Map<String, Atom>> declarations = new ArrayList<>();

    public SymbolTable() {
        this.declarations.add(new HashMap<>());
    }

    public Map<String, Atom> enterScope() {
        this.declarations.add(0, new HashMap<>());
        return this.getCurrentScope();
    }

    public Atom declare(String name, Atom atom) {
        Atom previous = this.declarations.get(0).get(name);
        if (previous != null)
            throw new RuntimeException("Already declared atom " + name);
        previous = GLOBALS.get(name);
        if (previous != null)
            throw new RuntimeException("Already declared atom " + name);
        this.declarations.get(0).put(name, atom);
        return atom;
    }

    public Atom lookup(String name) {
        if (this.getCurrentScope().containsKey(name))
            return this.getCurrentScope().get(name);
        if (GLOBALS.containsKey(name))
            return GLOBALS.get(name);
        throw new RuntimeException("Symbol table has no entry with name " + name);
    }

    public Map<String, Atom> exitScope() {
        this.declarations.remove(0);
        return this.getCurrentScope();
    }

    private Map<String, Atom> getCurrentScope() {
        Map<String, Atom> currentScope = new HashMap<>();
        for (Map<String, Atom> d : this.declarations) {
            for (String s : d.keySet())
                currentScope.put(s, d.get(s));
        }
        for (String key : GLOBALS.keySet())
            currentScope.putIfAbsent(key, GLOBALS.get(key));
        return currentScope;
    }

}
