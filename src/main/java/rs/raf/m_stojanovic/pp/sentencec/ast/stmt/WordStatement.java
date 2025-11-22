package rs.raf.m_stojanovic.pp.sentencec.ast.stmt;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.WordExpression;

public class WordStatement extends Statement {

    public final WordExpression word;

    public WordStatement(WordExpression word) {
        this.word = word;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitWordStatement(this);
    }
}
