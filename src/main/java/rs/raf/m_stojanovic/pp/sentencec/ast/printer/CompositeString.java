package rs.raf.m_stojanovic.pp.sentencec.ast.printer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class CompositeString {

    private final String root;
    private final List<CompositeString> children = new ArrayList<>();

    public CompositeString(String root) {
        this.root = root;
    }

    public void addChild(CompositeString child) {
        this.children.add(child);
    }

    public void print(int indent, PrintStream out) {
        out.print(" ".repeat(indent));
        out.println(root);
        for (CompositeString c : this.children)
            c.print(indent + 1, out);
    }
}
