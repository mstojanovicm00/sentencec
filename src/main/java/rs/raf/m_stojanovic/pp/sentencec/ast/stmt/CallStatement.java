package rs.raf.m_stojanovic.pp.sentencec.ast.stmt;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.CallExpression;

public class CallStatement extends Statement {

    public final CallExpression call;

    public CallStatement(CallExpression call) {
        this.call = call;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitCallStatement(this);
    }
}
