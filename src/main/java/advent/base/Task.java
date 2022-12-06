package advent.base;

import java.util.concurrent.Callable;

public class Task {

    public interface SolutionStrategy<T extends Task> {
        public void solve(T solution);
    }

    public static class Builder<T extends Task> {
        private final T solution;
        public Builder(Callable<T> solutionConstructor) {
            try {
                this.solution = solutionConstructor.call();
            } catch (Throwable t) {
                throw new IllegalArgumentException("Solution constructor error.", t);
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
