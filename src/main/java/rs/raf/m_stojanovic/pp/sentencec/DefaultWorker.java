package rs.raf.m_stojanovic.pp.sentencec;

import rs.raf.m_stojanovic.pp.sentencec.ast.Line;
import rs.raf.m_stojanovic.pp.sentencec.interpreter.Interpreter;
import rs.raf.m_stojanovic.pp.sentencec.lexer.Lexer;
import rs.raf.m_stojanovic.pp.sentencec.parser.Parser;
import rs.raf.m_stojanovic.pp.sentencec.semantic.DeclarationVisitor;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

import java.util.List;

public class DefaultWorker implements Worker {

    private int lineNumber = 0;

    @Override
    public WorkResult work(String code) {
        if (code.equalsIgnoreCase("exit;"))
            return new WorkResult.GoodResult("", 0);

        ++this.lineNumber;

        System.out.println();

        // Lexer
        Lexer lexer = new Lexer(code, this.lineNumber);
        List<Token> tokens;
        try {
            tokens = lexer.scanTokens();
        } catch (RuntimeException e) {
            return new WorkResult.BadResult(e);
        }
        tokens.forEach(System.out::println);

        System.out.println();

        // Parser
        Parser parser = new Parser(code, tokens);
        Line line;
        try {
            line = parser.parse();
        } catch (RuntimeException e) {
            return new WorkResult.BadResult(e);
        }
        line.print(System.out);

        System.out.println();

        // Declarations
        DeclarationVisitor declarationVisitor = new DeclarationVisitor();
        try {
            declarationVisitor.visitLine(line);
        } catch (RuntimeException e) {
            return new WorkResult.BadResult(e);
        }

        // Interpreter
        Interpreter interpreter = new Interpreter();
        StringBuilder sb = interpreter.visitLine(line);

        return new WorkResult.GoodResult(sb.toString());
    }
}
