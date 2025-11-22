package rs.raf.m_stojanovic.pp.sentencec.parser;

import rs.raf.m_stojanovic.pp.sentencec.ast.Line;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Parameter;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Sentence;
import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Word;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.CallExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.Expression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.ParameterExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.expr.WordExpression;
import rs.raf.m_stojanovic.pp.sentencec.ast.stmt.*;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;
import rs.raf.m_stojanovic.pp.sentencec.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final String code;
    private final List<Token> tokens;
    private int current = 0;

    public Parser(String code, List<Token> tokens) {
        this.code = code;
        this.tokens = tokens;
    }

    public Line parse() {
        return this.parseLine();
    }

    private Line parseLine() {
        return new Line(this.code, this.parseBody());
    }

    private List<Statement> parseBody() {
        List<Statement> statements = new ArrayList<>();
        do {
            statements.add(this.parseStatement());
            this.consume(TokenType.SEMICOLON, "';' expected at the end of the statement");
        } while (this.check(TokenType.SEMICOLON));
        return statements;
    }

    private Statement parseStatement() {
        Token first = this.advance();
        switch (first.getType()) {
            case WORD:
                if (this.check(TokenType.SEMICOLON))
                    return new WordStatement(new WordExpression(new Word(first)));
                throw this.error(first, "The word at the beginning of the line must be "
                        + "followed by ';'");
            case SENTENCE:
                Token id = this.consume(TokenType.ID, "The symbol 'sentence' must be " +
                        "followed by the sentence name");
                this.consume(TokenType.PAREN_O, "No parameter parenthesis for a sentence");
                List<Parameter> params = this.parseParameters();
                this.consume(TokenType.ASSIGN, "Sentence block not started");
                List<Statement> stats = this.parseSentence(1);
                return new SentenceStatement(new Sentence(id), params, stats);
            case PARAMETER:
                if (this.check(TokenType.SEMICOLON))
                    return new ParameterStatement(new ParameterExpression(new Parameter(first)));
                throw this.error(first, "The parameter at the beginning of the line must be "
                        + "followed by ';'");
            case SEMICOLON:
                return new NullStatement(first);
            case END:
                return new EndStatement();
            case ID:
                this.consume(TokenType.PAREN_O, "No argument parenthesis for a sentence call");
                List<Expression> exprs = this.parseArguments();
                return new CallStatement(new CallExpression(new Sentence(first), exprs));
            case PAREN_O:
                throw this.error(first, "'(' not expected at this context");
            case PAREN_C:
                throw this.error(first, "')' not expected at this context");
            case ASSIGN:
                throw this.error(first, "'=' not expected at this context");
            case EOL:
                break;
        }
        return null;
    }

    private List<Expression> parseArguments() {
        List<Expression> expressions = new ArrayList<>();
        Token next;
        do {
            expressions.add(this.parseArgument());
            next = this.consumeOneOf("',' or ')' expected at this context", TokenType.COMMA, TokenType.PAREN_C);
        } while (next.getType() != TokenType.PAREN_C);
        return expressions;
    }

    private Expression parseArgument() {
        Token first = this.advance();
        switch (first.getType()) {
            case ID:
                this.consume(TokenType.PAREN_O, "No argument parenthesis for a sentence call");
                List<Expression> exprs = this.parseArguments();
                this.consume(TokenType.PAREN_C, "Argument parenthesis not closed");
                return new CallExpression(new Sentence(first), exprs);
            case WORD:
                return new WordExpression(new Word(first));
            default:
                throw this.error(first, "Symbol not expected at this context");
        }
    }

    private List<Statement> parseSentence(int end) {
        List<Statement> statements = new ArrayList<>();
        int last = end - 1;
        do {
            statements.add(this.parseStatement());
            if (statements.get(statements.size() - 1) instanceof EndStatement)
                --end;
        } while (end > last);
        return statements;
    }

    private List<Parameter> parseParameters() {
        List<Parameter> params = new ArrayList<>();
        Token next;
        do {
            Token token = this.consume(TokenType.PARAMETER, "Parameter expected");
            Parameter param = new Parameter(token);
            params.add(param);
            next = this.consumeOneOf("',' or ')' expected at this context", TokenType.COMMA, TokenType.PAREN_C);
        } while (next.getType() != TokenType.PAREN_C);
        return params;
    }

    private boolean match(TokenType... types) {
        for (TokenType t : types) {
            if (this.check(t)) { this.advance(); return true; }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (this.check(type)) return this.advance();
        throw this.error(this.peek(), message);
    }

    private Token consumeOneOf(String message, TokenType... types) {
        for (TokenType t : types) if (this.check(t)) return this.advance();
        throw this.error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (this.isAtEnd()) return type == TokenType.EOL;
        return this.peek().getType() == type;
    }

    private boolean checkNext(TokenType type) {
        if (this.current + 1 >= this.tokens.size()) return false;
        return this.tokens.get(this.current + 1).getType() == type;
    }

    private Token advance() {
        if (!this.isAtEnd()) this.current++;
        return this.previous();
    }

    private boolean isAtEnd() { return this.peek().getType() == TokenType.EOL; }

    private Token peek() { return this.tokens.get(this.current); }

    private Token previous() { return this.tokens.get(this.current - 1); }

    private RuntimeException error(Token token, String message) {
        String where = token.getType() == TokenType.EOL ? " at end" : " at '" + token.getLexeme() + "'";
        return new RuntimeException("Parse error" + where + ": " + message +
                " (line: " + token.getRepl() + ", col: " + token.getPos() + ")");
    }
}
