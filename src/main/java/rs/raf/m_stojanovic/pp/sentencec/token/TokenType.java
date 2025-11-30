package rs.raf.m_stojanovic.pp.sentencec.token;

public enum TokenType {
    /*
        Valid programs:
            - sentence %posses($x) = this; is; my; $x; end;
            - problem;
            - %posses(problem);
     */
    WORD(""),
    SENTENCE("sentence"),
    PARAMETER(""),
    COMMA(","),
    SEMICOLON(";"),
    ID(""),
    PAREN_O("("),
    PAREN_C(")"),
    ASSIGN("="),
    END("end"),
    EOL("\1");

    private final String word;

    TokenType(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
