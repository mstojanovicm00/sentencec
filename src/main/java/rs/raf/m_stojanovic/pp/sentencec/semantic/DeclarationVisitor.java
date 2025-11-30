package rs.raf.m_stojanovic.pp.sentencec.semantic;

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

public class DeclarationVisitor implements Visitor<Void> {

    private final SymbolTable symbolTable = new SymbolTable();

    public void declareAll(Line line) {
        this.visitLine(line);
    }

    @Override
    public Void visitLine(Line line) {
        for (Statement stmt : line.statements) {
            this.symbolTable.enterScope();
            stmt.outer = true;
            stmt.accept(this);
            this.symbolTable.exitScope();
        }
        return null;
    }

    @Override
    public Void visitCallStatement(CallStatement callStatement) {
        return callStatement.call.accept(this);
    }

    @Override
    public Void visitEndStatement(EndStatement endStatement) {
        return null;
    }

    @Override
    public Void visitNullStatement(NullStatement nullStatement) {
        return null;
    }

    @Override
    public Void visitParameterStatement(ParameterStatement parameterStatement) {
        return parameterStatement.expression.accept(this);
    }

    @Override
    public Void visitSentenceStatement(SentenceStatement sentenceStatement) {
        this.symbolTable.enterScope();
        for (Parameter param : sentenceStatement.parameters)
            this.symbolTable.declare(param.token.getLexeme(), param);
        for (Statement stmt : sentenceStatement.statements) {
            stmt.outer = false;
            stmt.accept(this);
        }
        this.symbolTable.exitScope();
        Sentence sentence = sentenceStatement.sentence;
        this.symbolTable.declare(sentence.token.getLexeme(), sentence);
        if (sentenceStatement.outer)
            SymbolTable.declareGlobal(sentence.token.getLexeme(), sentence);
        return null;
    }

    @Override
    public Void visitWordStatement(WordStatement wordStatement) {
        wordStatement.word.accept(this);
        if (wordStatement.outer)
            SymbolTable.declareGlobal(wordStatement.word.word.token.getLexeme(), wordStatement.word.word);
        return null;
    }

    @Override
    public Void visitCallExpression(CallExpression expression) {
        expression.sentence.accept(this);
        for (Expression arg : expression.arguments) {
            if (arg instanceof WordExpression) {
                WordExpression wordExpression = (WordExpression) arg;
                wordExpression.word.accept(this);
            } else
                arg.accept(this);
        }
        return null;
    }

    @Override
    public Void visitParameterExpression(ParameterExpression expression) {
        return expression.parameter.accept(this);
    }

    @Override
    public Void visitWordExpression(WordExpression expression) {
        Word word = expression.word;
        String name = word.token.getLexeme();
        this.symbolTable.declare(name, word);
        return null;
    }

    @Override
    public Void visitParameter(Parameter parameter) {
        parameter.reference = this.symbolTable.lookup(parameter.token.getLexeme());
        return null;
    }

    @Override
    public Void visitSentence(Sentence sentence) {
        sentence.reference = this.symbolTable.lookup(sentence.token.getLexeme());
        return null;
    }

    @Override
    public Void visitWord(Word word) {
        word.reference = this.symbolTable.lookup(word.token.getLexeme());
        return null;
    }
}
