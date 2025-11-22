package rs.raf.m_stojanovic.pp.sentencec.interpreter;

import rs.raf.m_stojanovic.pp.sentencec.ast.Line;
import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Parameter;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Sentence;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Word;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.CallExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.ParameterExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.WordExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.stmt.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter implements Visitor<StringBuilder> {

    private Map<String, Parameter.ParameterInUse> parameters;
    private static final Map<String, List<Parameter>> sentenceParameters = new HashMap<>();
    private static final Map<String, List<Statement>> statements = new HashMap<>();

    @Override
    public StringBuilder visitLine(Line line) {
        StringBuilder result = null;
        for (Statement stmt : line.statements)
            result = stmt.accept(this);
        return result;
    }

    @Override
    public StringBuilder visitCallStatement(CallStatement callStatement) {
        this.parameters = new HashMap<>();
        StringBuilder res = callStatement.call.accept(this);
        this.parameters = null;
        return res;
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
        return parameterStatement.expression.accept(this);
    }

    @Override
    public StringBuilder visitSentenceStatement(SentenceStatement sentenceStatement) {
        sentenceParameters.put(sentenceStatement.sentence.reference.getLexeme(), new ArrayList<>());
        for (Parameter p : sentenceStatement.parameters)
            sentenceParameters.get(sentenceStatement.sentence.reference.getLexeme()).add(p);
        statements.put(sentenceStatement.sentence.reference.getLexeme(), sentenceStatement.statements);
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitWordStatement(WordStatement wordStatement) {
        return wordStatement.word.accept(this);
    }

    @Override
    public StringBuilder visitCallExpression(CallExpression expression) {
        List<Parameter> params = sentenceParameters.get(expression.sentence.reference.getLexeme());
        if (params.size() == expression.arguments.size()) {
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < expression.arguments.size(); i++)
                this.parameters.put(params.get(i).token.getLexeme(),
                        new Parameter.ParameterInUse(
                                params.get(i).token,
                                expression.arguments.get(i).accept(this)));
            for (Statement stmt : statements.get(expression.sentence.reference.getLexeme())) {
                if (stmt instanceof CallStatement)
                    res.append(stmt.accept(this));
                else if (stmt instanceof ParameterStatement) {
                    ParameterStatement parameterStatement = (ParameterStatement) stmt;
                    Parameter.ParameterInUse piu = this.parameters.get(
                            parameterStatement.expression.parameter.reference.getLexeme());
                    if (piu != null)
                        res.append(piu.accept(this));
                } else if (stmt instanceof SentenceStatement)
                    stmt.accept(this);
                else if (stmt instanceof WordStatement)
                    res.append(stmt.accept(this));
            }
            return res;
        }
        throw new RuntimeException("Wrong number of arguments " +
                expression.sentence.paramNames.size() + " != " + expression.arguments.size());
    }

    @Override
    public StringBuilder visitParameterExpression(ParameterExpression expression) {
        return expression.parameter.accept(this);
    }

    @Override
    public StringBuilder visitWordExpression(WordExpression expression) {
        return expression.word.accept(this);
    }

    @Override
    public StringBuilder visitParameter(Parameter parameter) {
        if (parameter instanceof Parameter.ParameterInUse)
            return ((Parameter.ParameterInUse) parameter).argument.append(" ");
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitSentence(Sentence sentence) {
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitWord(Word word) {
        return new StringBuilder(word.token.getLexeme()).append(" ");
    }
}
