package advent.base;

import java.util.concurrent.Callable;

public class Task<InputType, ResultType> {

    protected InputType input;

    protected ResultType result;

    protected Task(InputType input) {
        this.input = input;
    }

    public ResultType getResult() {
        return this.result;
    }

    public interface SolutionStrategy<T extends Task<?, ?>> {
        void solve(T solution);
    }

    public static class Builder<T extends Task<?, ?>> {

        private final T solution;

        public Builder(Callable<T> taskConstructor) {
            try {
                this.solution = taskConstructor.call();
            } catch (Throwable t) {
                throw new IllegalArgumentException("Task constructor error.", t);
            }
        }

        public Builder<T> solve(SolutionStrategy<T> task) {
            task.solve(this.solution);
            return this;
        }

        public T build() {
            return this.solution;
        }
    }
}
