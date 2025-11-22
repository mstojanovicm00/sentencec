package rs.raf.m_stojanovic.pp.sentencec.lexer;

import rs.raf.m_stojanovic.pp.sentencec.token.Token;
import rs.raf.m_stojanovic.pp.sentencec.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lexer {

    private static final Map<String, TokenType> KEYWORDS = Map.ofEntries(
            Map.entry("sentence", TokenType.SENTENCE),
            Map.entry("end", TokenType.END)
    );

    private final ScannerModel model;
    private final String source;
    private final int lineNumber;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String source, int line) {
        this.source = source;
        this.model = new ScannerModel(this.source);
        this.lineNumber = line;
    }

    public List<Token> scanTokens() {
        while (!this.model.isAtEnd()) {
            this.model.beginToken();
            scanToken();
        }
        this.tokens.add(new Token("\0", TokenType.EOL, this.source, this.model.getCol()));
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
            case ',':
                this.addToken(TokenType.COMMA);
                break;
            case ';':
                this.addToken(TokenType.SEMICOLON);
                break;
            case '=':
                this.addToken(TokenType.ASSIGN);
                break;
            case '$':
                this.identifier(TokenType.PARAMETER);
                break;
            case '%':
                this.identifier(TokenType.ID);
                break;
            case ' ':
            case '\t':
                break;
            default:
                this.identifier(null);
        }
    }

    private void identifier(TokenType proposed) {
        while (isIdentPart(this.model.peek()))
            this.model.advance();
        if (Character.isDigit(this.model.peek()))
            throw this.error("Digit found");
        String text = this.source.substring(this.model.getStartIdx(), this.model.getCur());
        if (proposed == null || proposed == TokenType.WORD) {
            TokenType type = KEYWORDS.getOrDefault(text.toLowerCase(), TokenType.WORD);
            if (type == TokenType.WORD)
                this.addToken(TokenType.WORD, text, text.toLowerCase());
            else
                this.addToken(type, text);
            return;
        }
        switch (proposed) {
            case ID:
                this.addToken(TokenType.ID, text);
                break;
            case PARAMETER:
                this.addToken(TokenType.PARAMETER, text);
                break;
        }
    }

    private boolean isIdentStart(char c) { return Character.isLetter(c); }
    private boolean isIdentPart(char c)  { return isIdentStart(c); }

    private void addToken(TokenType type, String lexeme, String literal) {
        this.tokens.add(new Token(lexeme, literal, type, this.source, this.model.getStartCol()));
    }

    private void addToken(TokenType type, String lexeme) {
        this.tokens.add(new Token(lexeme, type, this.source, this.model.getStartCol()));
    }

    private void addToken(TokenType type) {
        this.tokens.add(new Token(type.getWord(), type, this.source, this.model.getStartCol()));
    }

    private RuntimeException error(String msg) {
        String near = source.substring(this.model.getStartIdx(), Math.min(this.model.getCur(), source.length()));
        return new RuntimeException(
                "LEXER ERROR > " + msg + " at "
                        + this.model.getStartLine() + ":"
                        + this.model.getStartCol() + " near '" + near + "'");
    }

}
