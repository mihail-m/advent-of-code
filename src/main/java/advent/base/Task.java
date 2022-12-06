package advent.base;

import java.util.concurrent.Callable;

public class Task {

    public interface SolutionStrategy<T extends Task> {
        public void solve(T solution);
    }

    public static class Builder<T extends Task> {

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
