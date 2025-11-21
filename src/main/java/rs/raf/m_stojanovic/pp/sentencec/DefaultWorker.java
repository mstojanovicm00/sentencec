package rs.raf.m_stojanovic.pp.sentencec;

import rs.raf.m_stojanovic.pp.sentencec.lexer.Lexer;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

import java.util.List;

public class DefaultWorker implements Worker {

    private int lineNumber = 0;

    @Override
    public WorkResult work(String code) {
        if (code.equalsIgnoreCase("exit;"))
            return new WorkResult.GoodResult("", 0);

        ++this.lineNumber;

        // Lexer
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.scanTokens();
        tokens.forEach(System.out::println);

        return new WorkResult.GoodResult(code);
    }
}
