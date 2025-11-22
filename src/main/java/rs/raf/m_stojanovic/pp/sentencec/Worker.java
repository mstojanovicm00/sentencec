package rs.raf.m_stojanovic.pp.sentencec;

import java.io.PrintStream;
import java.util.Optional;

public interface Worker {
    WorkResult work(String code);

    abstract class WorkResult {
        private final PrintStream out;
        private final Optional<Integer> exitStatus;

        protected WorkResult(PrintStream out) {
            this.out = out;
            this.exitStatus = Optional.empty();
        }

        protected WorkResult(PrintStream out, Integer exitStatus) {
            this.out = out;
            this.exitStatus = Optional.ofNullable(exitStatus);
        }

        public int map() {
            return this.exitStatus.orElse(-1);
        }

        public abstract String get();

        public PrintStream getOut() {
            return out;
        }

        public static class GoodResult extends WorkResult {
            private final String message;

            public GoodResult(String message) {
                super(System.out);
                this.message = message;
            }

            public GoodResult(String message, Integer exitStatus) {
                super(System.out, exitStatus);
                this.message = message;
            }

            @Override
            public String get() {
                return this.message;
            }
        }

        public static class BadResult extends WorkResult {
            private final Exception exception;

            public BadResult(Exception exception) {
                super(System.err);
                this.exception = exception;
            }

            public BadResult(Exception exception, Integer exitStatus) {
                super(System.err, exitStatus);
                this.exception = exception;
            }

            @Override
            public String get() {
                return exception.getMessage();
            }
        }
    }
}
