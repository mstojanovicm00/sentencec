package rs.raf.m_stojanovic.pp.sentencec.ast.stmt;

import java.util.List;

public abstract class CompositeStatement extends Statement {

    public final List<Statement> statements;

    protected CompositeStatement(List<Statement> statements) {
        this.statements = statements;
    }
}
