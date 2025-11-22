package rs.raf.m_stojanovic.pp.sentencec.ast.stmt;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;

public class EndStatement extends Statement {
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitEndStatement(this);
    }
}
