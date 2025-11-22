package rs.raf.m_stojanovic.pp.sentencec.semantic;

import rs.raf.m_stojanovic.pp.sentencec.token.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {

    private static final Map<String, Token> FLAT_DECLARATIONS = new HashMap<>();

    private final List<Map<String, Token>> staticDeclarations = new ArrayList<>();

    public SymbolTable() {
        this.staticDeclarations.add(new HashMap<>());
    }

    public Token declare(String name, Token token) {
        if (this.getCurrentScope().get(name) != null)
            throw new RuntimeException("Duplicate declaration of '" + name + "'");
        this.getCurrentScope().put(name, token);
        System.out.println("Name '" + name + "' declared");
        return token;
    }

    public Token lookup(String name) {
        for (int i = this.staticDeclarations.size() - 1; i >= 0; i--) {
            Map<String, Token> map = this.staticDeclarations.get(i);
            if (map.containsKey(name)) {
                System.out.println("Name '" + name + "' found in lexical/static scope");
                return map.get(name);
            }
        }
        if (FLAT_DECLARATIONS.containsKey(name)) {
            System.out.println("Name '" + name + "' found in flat scope");
            return FLAT_DECLARATIONS.get(name);
        }
        throw new RuntimeException("No declaration named '" + name + "'");
    }

    public Map<String, Token> enterScope() {
        this.staticDeclarations.add(new HashMap<>());
        return this.getCurrentScope();
    }

    public Map<String, Token> exitScope() {
        this.staticDeclarations.remove(this.staticDeclarations.size() - 1);
        return this.getCurrentScope();
    }

    public Map<String, Token> getCurrentScope() {
        return this.staticDeclarations.get(staticDeclarations.size() - 1);
    }

    public void destroy() {
        FLAT_DECLARATIONS.putAll(this.staticDeclarations.get(0));
    }

}
