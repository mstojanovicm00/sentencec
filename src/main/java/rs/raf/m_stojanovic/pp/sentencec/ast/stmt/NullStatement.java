package rs.raf.m_stojanovic.pp.sentencec.ast.stmt;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

public class NullStatement extends Statement {

    public final Token token;

    public NullStatement(Token token) {
        this.token = token;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitNullStatement(this);
    }
}
