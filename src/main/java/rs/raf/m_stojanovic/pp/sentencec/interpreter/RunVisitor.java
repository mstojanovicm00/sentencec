package rs.raf.m_stojanovic.pp.sentencec.interpreter;

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

import java.util.ArrayList;
import java.util.List;

public class RunVisitor implements Visitor<StringBuilder> {
    @Override
    public StringBuilder visitLine(Line line) {
        StringBuilder result = new StringBuilder();
        for (Statement stmt : line.statements)
            result = stmt.accept(this);
        return result;
    }

    @Override
    public StringBuilder visitCallStatement(CallStatement callStatement) {
        return callStatement.call.accept(this);
    }

    @Override
    public StringBuilder visitEndStatement(EndStatement endStatement) {
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitNullStatement(NullStatement nullStatement) {
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitParameterStatement(ParameterStatement parameterStatement) {
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitSentenceStatement(SentenceStatement sentenceStatement) {
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitWordStatement(WordStatement wordStatement) {
        return wordStatement.word.accept(this);
    }

    @Override
    public StringBuilder visitCallExpression(CallExpression expression) {
        Sentence s = (Sentence) expression.sentence.reference;
        int params = s.sentenceStatement.parameters.size();
        int args = expression.arguments.size();
        if (params != args)
            throw new RuntimeException("Wrong number of arguments for calling a sentence");
        List<CalledSentence.Argument> arguments = new ArrayList<>();
        for (int i = 0; i < params; i++) {
            Parameter parameter = s.sentenceStatement.parameters.get(i);
            Expression argument = expression.arguments.get(i);
            CalledSentence.Argument arg = new CalledSentence.Argument(parameter, argument.accept(this));
            arguments.add(arg);
        }
        CalledSentence cs = new CalledSentence(this, arguments,
                s.sentenceStatement.statements);
        return cs.run();
    }

    @Override
    public StringBuilder visitParameterExpression(ParameterExpression expression) {
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitWordExpression(WordExpression expression) {
        return new StringBuilder(expression.word.toString()).append(" ");
    }

    @Override
    public StringBuilder visitParameter(Parameter parameter) {
        return new StringBuilder(parameter.toString());
    }

    @Override
    public StringBuilder visitSentence(Sentence sentence) {
        return new StringBuilder(sentence.toString());
    }

    @Override
    public StringBuilder visitWord(Word word) {
        return new StringBuilder(word.toString());
    }
}
