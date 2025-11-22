package rs.raf.m_stojanovic.pp.sentencec.ast.atom;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

public class Parameter extends Atom {
    public Parameter(Token token) {
        super(token);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitParameter(this);
    }

    public static class ParameterInUse extends Parameter {
        public final StringBuilder argument;

        public ParameterInUse(Token token, StringBuilder argument) {
            super(token);
            this.argument = argument;
        }
    }
}
