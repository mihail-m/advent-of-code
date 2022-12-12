package aoc;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import aoc.base.Task;

public class Day12Task extends Task<List<String>, Integer> {

    private Day12Task(List<String> input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day12Task> {

        FIND_SHORTEST_PATH {
            @Override
            public void solve(Day12Task task) {
                task.result = bfs(task.input, getStart(task.input, 'S'), 'E',
                        (node, nextNode) -> getHeight(node, task.input) + 1 < getHeight(nextNode, task.input));
            }
        },

        FIND_BEST_TRAIL {
            @Override
            public void solve(Day12Task task) {
                task.result = bfs(task.input, getStart(task.input, 'E'), 'a',
                        (node, nextNode) -> getHeight(node, task.input) - 1 > getHeight(nextNode, task.input));
            }
        };

        private static int bfs(List<String> map, Node start, char end, BiPredicate<Node, Node> canPass) {
            Queue<Node> q = new ArrayDeque<>();
            q.add(start);

            Map<Integer, Integer> dist = new HashMap<>();
            dist.put(getEncoder(map).apply(start), 0);

            int ans = Integer.MAX_VALUE;

            while (!q.isEmpty()) {
                Node node = q.poll();

                for (Node.Move move : Node.Move.values()) {
                    Node nextNode = node.doMove(move);

                    if (!validNextNode(map, node, nextNode, dist, canPass)) {
                        continue;
                    }

                    q.add(nextNode);
                    dist.put(getEncoder(map).apply(nextNode), dist.get(getEncoder(map).apply(node)) + 1);

                    if (map.get(nextNode.row).charAt(nextNode.col) == end) {
                        ans = Math.min(ans, dist.get(getEncoder(map).apply(nextNode)));
                    }
                }
            }

            return ans;
        }

        private static boolean validNextNode(List<String> map,
                Node node, Node next,
                Map<Integer, Integer> dist,
                BiPredicate<Node, Node> canPass) {

            return getInBoundsPredicate(map).test(next)
                    && !dist.containsKey(getEncoder(map).apply(next))
                    && !canPass.test(node, next);
        }

        private static Node getStart(List<String> map, char s) {
            Node start = new Node(0, 0);
            for (int i = 0; i < map.size(); i++) {
                for (int j = 0; j < map.get(i).length(); j++) {
                    if (map.get(i).charAt(j) == s) {
                        start = new Node(i, j);
                    }
                }
            }
            return start;
        }

        private static Predicate<Node> getInBoundsPredicate(List<String> map) {
            return (node) -> node.row >= 0
                    && node.col >= 0
                    && node.row < map.size()
                    && node.col < map.get(0).length();
        }

        private static Function<Node, Integer> getEncoder(List<String> map) {
            return (node) -> node.row * map.get(0).length() + node.col;
        }

        private static char getHeight(Node node, List<String> map) {
            if (map.get(node.row).charAt(node.col) == 'S') {
                return 'a';
            }
            if (map.get(node.row).charAt(node.col) == 'E') {
                return 'z';
            }
            return map.get(node.row).charAt(node.col);
        }

        private static class Node {

            public enum Move {
                UP(-1, 0),
                DOWN(1, 0),
                LEFT(0, -1),
                RIGHT(0, 1);

                private final int row, col;

                Move(int row, int col) {
                    this.row = row;
                    this.col = col;
                }
            }

            public final int row, col;

            public Node(int r, int c) {
                this.row = r;
                this.col = c;
            }

            public Node doMove(Move move) {
                return new Node(this.row + move.row, this.col + move.col);
            }
        }
    }

    public static Builder<Day12Task> builder(List<String> input) {
        return new Builder<>(() -> new Day12Task(input));
    }
}
