package rs.raf.m_stojanovic.pp.sentencec.ast.printer;

import rs.raf.m_stojanovic.pp.sentencec.ast.Line;
import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Parameter;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Sentence;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Word;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.CallExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.Expression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.ParameterExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.WordExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.stmt.*;

public class PrintVisitor implements Visitor<CompositeString> {
    @Override
    public CompositeString visitLine(Line line) {
        CompositeString cs = new CompositeString("LINE");
        for (Statement s : line.statements)
            cs.addChild(s.accept(this));
        return cs;
    }

    @Override
    public CompositeString visitCallStatement(CallStatement callStatement) {
        CompositeString cs = new CompositeString("CALL");
        cs.addChild(callStatement.call.accept(this));
        return cs;
    }

    @Override
    public CompositeString visitEndStatement(EndStatement endStatement) {
        return new CompositeString("END");
    }

    @Override
    public CompositeString visitNullStatement(NullStatement nullStatement) {
        return new CompositeString("NULL");
    }

    @Override
    public CompositeString visitParameterStatement(ParameterStatement parameterStatement) {
        CompositeString cs = new CompositeString("PARAMETER");
        cs.addChild(parameterStatement.expression.accept(this));
        return cs;
    }

    @Override
    public CompositeString visitSentenceStatement(SentenceStatement sentenceStatement) {
        CompositeString cs = new CompositeString("SENTENCE");
        CompositeString p = new CompositeString("PARAMS");
        for (Parameter param : sentenceStatement.parameters)
            p.addChild(param.accept(this));
        CompositeString s = new CompositeString("BODY");
        for (Statement stmt : sentenceStatement.statements)
            s.addChild(stmt.accept(this));
        cs.addChild(p);
        cs.addChild(s);
        return cs;
    }

    @Override
    public CompositeString visitWordStatement(WordStatement wordStatement) {
        CompositeString cs = new CompositeString("WORD");
        cs.addChild(wordStatement.word.accept(this));
        return cs;
    }

    @Override
    public CompositeString visitCallExpression(CallExpression expression) {
        CompositeString cs = new CompositeString("C");
        cs.addChild(expression.sentence.accept(this));
        for (Expression a : expression.arguments)
            cs.addChild(a.accept(this));
        return cs;
    }

    @Override
    public CompositeString visitParameterExpression(ParameterExpression expression) {
        CompositeString cs = new CompositeString("P");
        cs.addChild(expression.parameter.accept(this));
        return cs;
    }

    @Override
    public CompositeString visitWordExpression(WordExpression expression) {
        CompositeString cs = new CompositeString("W");
        cs.addChild(expression.word.accept(this));
        return cs;
    }

    @Override
    public CompositeString visitParameter(Parameter parameter) {
        return new CompositeString(parameter.token.getLexeme());
    }

    @Override
    public CompositeString visitSentence(Sentence sentence) {
        return new CompositeString(sentence.token.getLexeme());
    }

    @Override
    public CompositeString visitWord(Word word) {
        return new CompositeString(word.token.getLexeme());
    }
}
