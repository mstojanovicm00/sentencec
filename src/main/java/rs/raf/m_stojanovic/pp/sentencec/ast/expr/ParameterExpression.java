package rs.raf.m_stojanovic.pp.sentencec.ast.expr;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Parameter;

public class ParameterExpression implements Expression {

    public final Parameter parameter;

    public ParameterExpression(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitParameterExpression(this);
    }
}
