package rs.raf.m_stojanovic.pp.sentencec.ast.expr;

import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Sentence;

import java.util.List;

public class CallExpression implements Expression {

    public final Sentence sentence;
    public final List<Expression> arguments;

    public CallExpression(Sentence sentence, List<Expression> arguments) {
        this.sentence = sentence;
        this.arguments = arguments;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitCallExpression(this);
    }
}
