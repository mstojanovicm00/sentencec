package rs.raf.m_stojanovic.pp.sentencec.interpreter;

import rs.raf.m_stojanovic.pp.sentencec.ast.Line;
import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;

import java.util.concurrent.atomic.AtomicReference;

public class Interpreter implements Runnable {

    public static String run(Interpreter interpreter) {
        Thread thread = new Thread(interpreter);
        thread.start();
        try {
            thread.join();
            if (interpreter.exception.get() != null)
                throw interpreter.exception.get();
            return interpreter.result;
        } catch (InterruptedException ignored) {
            return interpreter.result;
        }
    }

    private final Line line;
    private String result;

    private AtomicReference<RuntimeException> exception = new AtomicReference<>();

    public Interpreter(Line line) {
        this.line = line;
    }

    @Override
    public void run() {
        Visitor<StringBuilder> visitor = new RunVisitor();
        try {
            StringBuilder result = visitor.visitLine(this.line);
            this.result = result.toString().trim();
        } catch (RuntimeException e) {
            this.exception.set(e);
        }
    }
}
