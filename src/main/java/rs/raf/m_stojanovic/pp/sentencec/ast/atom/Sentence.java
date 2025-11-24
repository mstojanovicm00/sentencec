package rs.raf.m_stojanovic.pp.sentencec.ast.atom;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.stmt.SentenceStatement;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

public class Sentence extends Atom {

    public SentenceStatement sentenceStatement;

    public Sentence(Token token) {
        super(token);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitSentence(this);
    }
}
