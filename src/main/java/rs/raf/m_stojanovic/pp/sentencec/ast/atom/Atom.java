package rs.raf.m_stojanovic.pp.sentencec.ast.atom;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

public abstract class Atom {

    public final Token token;

    protected Atom(Token token) {
        this.token = token;
    }

    public abstract <R> R accept(Visitor<R> visitor);
}
