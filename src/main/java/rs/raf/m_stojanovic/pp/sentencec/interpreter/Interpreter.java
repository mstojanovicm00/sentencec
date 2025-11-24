package rs.raf.m_stojanovic.pp.sentencec.interpreter;

import rs.raf.m_stojanovic.pp.sentencec.ast.Line;
import rs.raf.m_stojanovic.pp.sentencec.ast.Visitor;

public class Interpreter implements Runnable {

    public static String run(Interpreter interpreter) {
        Thread thread = new Thread(interpreter);
        thread.start();
        try {
            thread.join();
            return interpreter.result;
        } catch (InterruptedException ignored) {
            return interpreter.result;
        }
    }

    private final Line line;
    private String result;

    public Interpreter(Line line) {
        this.line = line;
    }

    @Override
    public void run() {
        Visitor<StringBuilder> visitor = new RunVisitor();
        StringBuilder result = visitor.visitLine(this.line);
        this.result = result.toString().trim();
    }
}
