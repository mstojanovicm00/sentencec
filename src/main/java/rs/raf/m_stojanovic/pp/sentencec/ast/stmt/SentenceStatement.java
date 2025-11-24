package rs.raf.m_stojanovic.pp.sentencec.ast.stmt;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Parameter;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Sentence;

import java.util.List;

public class SentenceStatement extends CompositeStatement {

    public final Sentence sentence;
    public final List<Parameter> parameters;

    public SentenceStatement(Sentence sentence, List<Parameter> parameters, List<Statement> statements) {
        super(statements);
        this.sentence = sentence;
        this.parameters = parameters;
        this.sentence.sentenceStatement = this;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitSentenceStatement(this);
    }
}
