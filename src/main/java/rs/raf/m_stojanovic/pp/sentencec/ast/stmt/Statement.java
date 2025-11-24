package rs.raf.m_stojanovic.pp.sentencec.ast.stmt;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;

public abstract class Statement {

    public boolean outer = true;

    public abstract <R> R accept(Visitor<R> visitor);
}
