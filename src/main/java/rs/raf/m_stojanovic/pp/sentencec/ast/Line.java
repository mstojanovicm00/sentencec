package rs.raf.m_stojanovic.pp.sentencec.ast;

import rs.raf.m_stojanovic.pp.sentencec.ast.printer.PrintVisitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.stmt.Statement;

import java.io.PrintStream;
import java.util.List;

public class Line {

    public final String content;
    public final List<Statement> statements;

    public Line(String content, List<Statement> statements) {
        this.content = content;
        this.statements = statements;
    }

    public void print(PrintStream out) {
        this.accept(new PrintVisitor()).print(0, out);
    }

    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitLine(this);
    }
}
