package rs.raf.m_stojanovic.pp.sentencec.ast;

import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Parameter;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Sentence;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Word;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.CallExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.ParameterExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.WordExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.stmt.*;

public interface Visitor<R> {
    /// Line
    R visitLine(Line line);

    /// Statement
    R visitCallStatement(CallStatement callStatement);
    R visitEndStatement(EndStatement endStatement);
    R visitNullStatement(NullStatement nullStatement);
    R visitParameterStatement(ParameterStatement parameterStatement);
    R visitSentenceStatement(SentenceStatement sentenceStatement);
    R visitWordStatement(WordStatement wordStatement);

    /// Expression
    R visitCallExpression(CallExpression expression);
    R visitParameterExpression(ParameterExpression expression);
    R visitWordExpression(WordExpression expression);

    /// Atom
    R visitParameter(Parameter parameter);
    R visitSentence(Sentence sentence);
    R visitWord(Word word);
}
