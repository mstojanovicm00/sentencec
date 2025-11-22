package rs.raf.m_stojanovic.pp.sentencec.semantic;

import rs.raf.m_stojanovic.pp.sentencec.ast.Line;
import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Atom;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Parameter;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Sentence;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Word;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.CallExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.ParameterExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.WordExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.stmt.*;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

public class DeclarationVisitor implements Visitor<Void> {

    private SymbolTable symbolTable;

    public void begin() {
        this.symbolTable = new SymbolTable();
    }

    public void end() {
        this.symbolTable.destroy();
        this.symbolTable = null;
    }

    @Override
    public Void visitLine(Line line) {
        this.begin();
        for (Statement stmt : line.statements)
            stmt.accept(this);
        this.end();
        return null;
    }

    @Override
    public Void visitCallStatement(CallStatement callStatement) {
        callStatement.call.accept(this);
        return null;
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
        parameterStatement.expression.accept(this);
        return null;
    }

    @Override
    public Void visitSentenceStatement(SentenceStatement sentenceStatement) {
        this.declareAtom(sentenceStatement.sentence.token.getLexeme(), sentenceStatement.sentence);
        this.symbolTable.enterScope();
        for (Parameter parameter : sentenceStatement.parameters)
            this.declareAtom(parameter.token.getLexeme(), parameter);
        for (Statement stmt : sentenceStatement.statements)
            stmt.accept(this);
        this.symbolTable.exitScope();
        return null;
    }

    @Override
    public Void visitWordStatement(WordStatement wordStatement) {
        this.declareAtom(wordStatement.word.word.token.getLexeme(), wordStatement.word.word);
        return null;
    }

    @Override
    public Void visitCallExpression(CallExpression expression) {
        expression.sentence.accept(this);
        return null;
    }

    @Override
    public Void visitParameterExpression(ParameterExpression expression) {
        expression.parameter.accept(this);
        return null;
    }

    @Override
    public Void visitWordExpression(WordExpression expression) {
        expression.word.accept(this);
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

    public void declareAtom(String name, Atom atom) {
        atom.reference = this.symbolTable.declare(name, atom.token);
    }
}
