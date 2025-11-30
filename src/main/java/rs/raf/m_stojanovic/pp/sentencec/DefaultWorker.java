package rs.raf.m_stojanovic.pp.sentencec;

import rs.raf.m_stojanovic.pp.sentencec.ast.Line;
import rs.raf.m_stojanovic.pp.sentencec.interpreter.Interpreter;
import rs.raf.m_stojanovic.pp.sentencec.lexer.Lexer;
import rs.raf.m_stojanovic.pp.sentencec.parser.Parser;
import rs.raf.m_stojanovic.pp.sentencec.semantic.DeclarationVisitor;
import rs.raf.m_stojanovic.pp.sentencec.token.Token;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DefaultWorker implements Worker {

    private int lineNumber = 0;
    private int printNumber = 0;

    private final boolean manyPrints = false;

    @Override
    public WorkResult work(String code) {
        if (code.isBlank())
            return new WorkResult.GoodResult("", -1);

        if (code.equalsIgnoreCase("exit;"))
            return new WorkResult.GoodResult("", 0);

        ++this.lineNumber;

        // Lexer
        Lexer lexer = new Lexer(code, this.lineNumber);
        List<Token> tokens;
        try {
            tokens = lexer.scanTokens();
        } catch (RuntimeException e) {
            return new WorkResult.BadResult(e);
        }
        this.doOnCondition(x -> this.manyPrints, x -> tokens.forEach(System.out::println));

        this.doOnCondition(x -> this.manyPrints, System.out::println);

        // Parser
        Parser parser = new Parser(code, tokens);
        Line line;
        try {
            line = parser.parse();
        } catch (RuntimeException e) {
            return new WorkResult.BadResult(e);
        }
        this.doOnCondition(x -> this.manyPrints, x -> line.print(System.out));

        this.doOnCondition(x -> this.manyPrints, x -> System.out.println());

        // Declarations
        DeclarationVisitor declarationVisitor = new DeclarationVisitor();
        try {
            declarationVisitor.declareAll(line);
        } catch (RuntimeException e) {
            return new WorkResult.BadResult(e);
        }

        String res;
        try {
            res = Interpreter.run(new Interpreter(line));
        } catch (RuntimeException e) {
            return new WorkResult.BadResult(e);
        }

        if (res.isBlank())
            return new WorkResult.GoodResult("");
        ++this.printNumber;
        return new WorkResult.GoodResult("[" + this.printNumber + "] " + res);
    }

    private void doOnCondition(Predicate<Void> condition, Consumer<Void> consumer) {
        if (condition.test(null)) {
            consumer.accept(null);
        }
    }
}
