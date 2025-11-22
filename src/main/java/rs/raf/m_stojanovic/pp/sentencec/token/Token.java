package rs.raf.m_stojanovic.pp.sentencec.token;

import java.util.Optional;

public class Token {

    public static boolean areEqual(Token t1, Token t2) {
        if (t1 == null && t2 == null)
            return true;
        if (t1 == null || t2 == null)
            return false;
        return t1.lexeme.equals(t2.lexeme);
    }

    private final String lexeme;
    private final Optional<String> literal;
    private final TokenType type;

    private final String repl;
    private final int pos;

    public Token(String lexeme, String literal, TokenType type, String repl, int pos) {
        this.lexeme = lexeme;
        this.literal = Optional.ofNullable(literal);
        this.type = type;
        this.repl = repl;
        this.pos = pos;
    }

    public Token(String lexeme, TokenType type, String repl, int pos) {
        this.lexeme = lexeme;
        this.literal = Optional.empty();
        this.type = type;
        this.repl = repl;
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "Token{" +
                "lexeme='" + lexeme + '\'' +
                ", literal=" + literal +
                ", type=" + type +
                ", repl='" + repl + '\'' +
                ", pos=" + pos +
                '}';
    }

    public String getLexeme() {
        return lexeme;
    }

    public Optional<String> getLiteral() {
        return literal;
    }

    public TokenType getType() {
        return type;
    }

    public String getRepl() {
        return repl;
    }

    public int getPos() {
        return pos;
    }
}
