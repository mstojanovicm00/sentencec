package rs.raf.m_stojanovic.pp.sentencec.ast.atom;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

public class Word extends Atom {
    public Word(Token token) {
        super(token);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitWord(this);
    }

    @Override
    public String toString() {
        return this.token.getLexeme();
    }
}
