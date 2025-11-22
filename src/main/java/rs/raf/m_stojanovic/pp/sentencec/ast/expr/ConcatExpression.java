package rs.raf.m_stojanovic.pp.sentencec.ast.expr;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Atom;

import java.util.List;

public class ConcatExpression implements Expression {

    public final List<Atom> atoms;

    public ConcatExpression(List<Atom> atoms) {
        this.atoms = atoms;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return null;
    }
}
