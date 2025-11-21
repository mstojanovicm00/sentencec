package rs.raf.m_stojanovic.pp.sentencec.lexer;

import rs.raf.m_stojanovic.pp.sentencec.token.Token;
import rs.raf.m_stojanovic.pp.sentencec.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lexer {

    private static final Map<String, TokenType> KEYWORDS = Map.ofEntries(
            Map.entry("sentence", TokenType.SENTENCE)
    );

    private final ScannerModel model;
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String source) {
        this.source = source;
        this.model = new ScannerModel(this.source);
    }

    public List<Token> scanTokens() {
        while (!this.model.isAtEnd()) {
            this.model.beginToken();
            scanToken();
        }
        this.tokens.add(new Token("\0", TokenType.EOF, this.source, this.model.getCol()));
        return this.tokens;
    }

    private void scanToken() {
        char c = this.model.advance();
        switch (c) {
            case '(':
                this.addToken(TokenType.PAREN_O);
                break;
            case ')':
                this.addToken(TokenType.PAREN_C);
                break;
            case ';':
                this.addToken(TokenType.SEMICOLON);
                break;
            case '=':
                this.addToken(TokenType.ASSIGN);
                break;
            case '_':
                this.addToken(TokenType.SNAKE);
                break;
            case '$':
                this.identifier(TokenType.PARAMETER);
                break;
            case '%':
                this.identifier(TokenType.ID);
                break;
            default:
                this.identifier(null);
        }
    }

    private void identifier(TokenType proposed) {
        while (isIdentPart(this.model.peek()))
            this.model.advance();
        String text = this.source.substring(this.model.getStartIdx(), this.model.getCur());
        if (proposed == null) {
            TokenType type = KEYWORDS.getOrDefault(text.toLowerCase(), TokenType.WORD);
            if (type == TokenType.WORD)
                this.addToken(TokenType.WORD, text, text.toLowerCase());
            else
                this.addToken(type, text);
            return;
        }
        switch (proposed) {
            case ID:
                this.addToken(TokenType.ID, "%" + text);
                break;
            case PARAMETER:
                this.addToken(TokenType.PARAMETER, "$" + text);
                break;
        }
    }

    private boolean isIdentStart(char c) { return Character.isLetter(c); }
    private boolean isIdentPart(char c)  { return isIdentStart(c) || Character.isDigit(c); }

    private void addToken(TokenType type, String lexeme, String literal) {
        if (type.getWord().isEmpty()) {
            this.tokens.add(new Token(lexeme, literal, type, this.source, this.model.getCol()));
            return;
        }
        throw new RuntimeException("Something's wrong, check your code!");
    }

    private void addToken(TokenType type, String lexeme) {
        if (type.getWord().isEmpty()) {
            this.tokens.add(new Token(lexeme, type, this.source, this.model.getCol()));
            return;
        }
        throw new RuntimeException("Something's wrong, check your code!");
    }

    private void addToken(TokenType type) {
        this.tokens.add(new Token(type.getWord(), type, this.source, this.model.getCol()));
    }

    private RuntimeException error(String msg) {
        String near = source.substring(this.model.getStartIdx(), Math.min(this.model.getCur(), source.length()));
        return new RuntimeException(
                "LEXER ERROR > " + msg + " at "
                        + this.model.getStartLine() + ":"
                        + this.model.getStartCol() + " near '" + near + "'");
    }

}
