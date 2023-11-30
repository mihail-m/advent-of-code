package aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.base.Task;

public class Day21Task extends Task<List<String[]>, Long> {

    private Day21Task(List<String[]> input) {
        super(input);
        this.result = 0L;
    }

    public enum Solution implements SolutionStrategy<Day21Task> {

        FIND_ROOT_NUMBER {
            @Override
            public void solve(Day21Task task) {
                Map<String, Node> tree = build(task.input);
                calculate(tree.get("root"), tree);
                task.result = Math.round(tree.get("root").num);
            }
        },

        FIND_MY_NUMBER {
            @Override
            public void solve(Day21Task task) {
                Map<String, Node> tree = build(task.input);

                double left = 0;
                double right = Double.MAX_VALUE;

                while (Math.abs(left - right) > 0.001) {
                    double mid = left + (right - left) / 2.0;

                    tree.get("humn").num = mid;
                    calculate(tree.get(tree.get("root").left), tree);
                    calculate(tree.get(tree.get("root").right), tree);

                    task.result = Math.round(mid);

                    if (tree.get(tree.get("root").left).num > tree.get(tree.get("root").right).num) {
                        left = mid;
                    } else {
                        right = mid;
                    }
                }
            }
        };

        private static Map<String, Node> build(List<String[]> input) {
            Map<String, Node> mapping = new HashMap<>();
            for (String[] line : input) {
                Node node = new Node();
                if (line.length == 2) {
                    node.num = Long.parseLong(line[1]);
                } else {
                    node.left = line[1];
                    node.op = Operation.fromString(line[2]);
                    node.right = line[3];
                }
                mapping.put(line[0], node);
            }
            return mapping;
        }

        private static void calculate(Node node, Map<String, Node> tree) {
            if (node.left == null || node.right == null) {
                return;
            }
            calculate(tree.get(node.left), tree);
            calculate(tree.get(node.right), tree);
            doOperation(node, tree);
        }

        private static void doOperation(Node node, Map<String, Node> tree) {
            switch (node.op) {
                case ADD -> node.num = tree.get(node.left).num + tree.get(node.right).num;
                case SUBTRACT -> node.num = tree.get(node.left).num - tree.get(node.right).num;
                case MULTIPLY -> node.num = tree.get(node.left).num * tree.get(node.right).num;
                case DIVIDE -> node.num = tree.get(node.left).num / tree.get(node.right).num;
            }
        }

        private static class Node {
            public double num;
            public Operation op;
            public String left, right;
        }

        public enum Operation {
            ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/");

            final String val;

            Operation(String val) {
                this.val = val;
            }


            @Override
            public String toString() {
                return this.val;
            }

            public static Operation fromString(String text) {
                for (Operation op : Operation.values()) {
                    if (op.val.equals(text)) {
                        return op;
                    }
                }
                return null;
            }
        }
    }

    public static Builder<Day21Task> builder(List<String[]> input) {
        return new Builder<>(() -> new Day21Task(input));
    }
}
