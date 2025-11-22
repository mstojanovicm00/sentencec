package rs.raf.m_stojanovic.pp.sentencec.ast.stmt;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.ParameterExpression;

public class ParameterStatement extends Statement {

    public final ParameterExpression expression;

    public ParameterStatement(ParameterExpression expression) {
        this.expression = expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitParameterStatement(this);
    }
}
