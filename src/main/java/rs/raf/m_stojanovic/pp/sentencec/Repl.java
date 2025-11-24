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

    public final void loop() {
        while (true) {
            String line = this.read();
            Worker.WorkResult result = this.worker.work(line);
            this.write(result.get(), result.getOut());
            if (result.map() == 0)
                break;
        }
    }

    private String read() {
        System.out.print(this.lineBegin + " ");
        return scanner.nextLine();
    }

    private void write(String message, PrintStream out) {
        if (!message.isBlank())
            out.println(message.trim());
    }
}
