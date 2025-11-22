package rs.raf.m_stojanovic.pp.sentencec.token;

public enum TokenType {
    /*
        Valid programs:
            - This;
            - Is;
            - My;
            - sentence %posses($x) = This_is_my_$x; end;
            - Problem;
            - %posses(Problem);
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
    SNAKE("_"),
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
