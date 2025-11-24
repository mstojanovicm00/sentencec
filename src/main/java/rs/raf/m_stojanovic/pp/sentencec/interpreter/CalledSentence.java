package rs.raf.m_stojanovic.pp.sentencec.interpreter;

import rs.raf.m_stojanovic.pp.sentencec.ast.atom.Parameter;
import rs.raf.m_stojanovic.pp.sentencec.ast.stmt.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CalledSentence {

    private final RunVisitor runVisitor;
    private final Map<String, Argument> arguments;
    private final List<Statement> statements;

    public CalledSentence(RunVisitor runVisitor, List<Argument> arguments, List<Statement> statements) {
        this.runVisitor = runVisitor;
        this.arguments = new HashMap<>();
        for (Argument arg : arguments)
            this.arguments.put(arg.parameter.toString(), arg);
        this.statements = statements;
    }

    public StringBuilder run() {
        StringBuilder result = new StringBuilder();
        for (Statement stmt : this.statements) {
            if (stmt instanceof WordStatement
                    || stmt instanceof CallStatement
                    || stmt instanceof SentenceStatement)
                result.append(stmt.accept(this.runVisitor));
            else if (stmt instanceof ParameterStatement) {
                ParameterStatement ps = (ParameterStatement) stmt;
                Parameter par;
                par = (Parameter) Objects.requireNonNullElseGet(
                        ps.expression.parameter.reference, () -> ps.expression.parameter);
                Argument argument = this.arguments.get(par.toString());
                result.append(argument.value);
            }
            result.append(" ");
        }
        return new StringBuilder(result.toString().trim().replaceAll("\\s+", " "));
    }

    public static class Argument {

        private final Parameter parameter;
        private final StringBuilder value;

        public Argument(Parameter parameter, StringBuilder value) {
            this.parameter = parameter;
            this.value = value;
        }
    }

}
