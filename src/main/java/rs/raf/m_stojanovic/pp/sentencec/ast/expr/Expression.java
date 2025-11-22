package rs.raf.m_stojanovic.pp.sentencec.ast.expr;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;

public interface Expression {
    <R> R accept(Visitor<R> visitor);
}
