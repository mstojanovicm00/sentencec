package rs.raf.m_stojanovic.pp.sentencec.ast.atom;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

import java.util.ArrayList;
import java.util.List;

public class Sentence extends Atom {
    public final List<String> paramNames = new ArrayList<>();
    public final List<Token> paramTokens = new ArrayList<>();

    public Sentence(Token token) {
        super(token);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitSentence(this);
    }
}
