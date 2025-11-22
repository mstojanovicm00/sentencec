package rs.raf.m_stojanovic.pp.sentencec.ast.expr;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Word;

public class WordExpression implements Expression {

    public final Word word;

    public WordExpression(Word word) {
        this.word = word;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitWordExpression(this);
    }
}
