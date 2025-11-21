package rs.raf.m_stojanovic.pp.sentencec;

import java.io.PrintStream;
import java.util.Scanner;

public class Repl {

    private final String lineBegin;
    private final Scanner scanner = new Scanner(System.in);
    private final Worker worker = new DefaultWorker();

    public Repl(String lineBegin) {
        this.lineBegin = lineBegin;
    }

    public final int loop() {
        int status = 0;
        while (true) {
            String line = this.read();
            Worker.WorkResult result = this.worker.work(line);
            this.write(result.get(), result.getOut());
            status = result.map();
            if (status > 0)
                this.write("There was a problem compiling the statement: " + line, System.err);
            if (status >= 0)
                break;
        }
        return status;
    }

    private String read() {
        System.out.print(lineBegin + " ");
        return scanner.nextLine();
    }

    private void write(String message, PrintStream out) {
        out.println(message);
    }
}
