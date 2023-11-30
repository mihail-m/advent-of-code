package aoc;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import aoc.base.Task;

public class Day14Task extends Task<List<StringBuilder>, Integer> {

    private Day14Task(List<StringBuilder> input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day14Task> {

        FIND_FALLEN_UNITS_OF_SAND {
            @Override
            public void solve(Day14Task task) {
                findSand(task, false);
            }
        },

        FIND_UNITS_OF_SAND_WITH_FLOOR {
            @Override
            public void solve(Day14Task task) {
                findSand(task, true);
            }
        };

        private static final Node SAND_HOLE = new Node(0, 500);

        private static void findSand(Day14Task task, boolean hasFloor) {
            AtomicBoolean onMap = new AtomicBoolean(true);

            while (onMap.get()) {
                Node node = sandFall(task.input, SAND_HOLE.copy(), null, onMap, hasFloor);

                if (onMap.get()) {
                    task.result++;
                    task.input.get(node.row).setCharAt(node.col, 'o');
                }

                if (node.at(SAND_HOLE)) {
                    break;
                }
            }
        }

        private static Node sandFall(List<StringBuilder> cave, Node cur, Node prev, AtomicBoolean onMap, boolean hasFloor) {
            if (!cur.equals(prev)) {
                for (Node.Move move : Node.Move.values()) {
                    Node newNode = cur.doMove(move);
                    if (!validMove(cave, newNode, onMap, hasFloor) && onMap.get()) {
                        continue;
                    }
                    if (onMap.get()) {
                        return sandFall(cave, newNode, cur, onMap, hasFloor);
                    }
                    break;
                }
            }
            return cur;
        }

        private static boolean validMove(List<StringBuilder> cave, Node node, AtomicBoolean onMap, boolean hasFloor) {
            if (node.row >= 0 && node.col >= 0 && node.row < cave.size() && node.col < cave.get(0).length()) {
                return cave.get(node.row).charAt(node.col) == '.';
            }
            onMap.set(hasFloor);
            return false;
        }

        private static class Node {

            public enum Move {
                DOWN(1, 0),
                DOWN_LEFT(1, -1),
                DOWN_RIGHT(1, 1);

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

            public Node doMove(Node.Move move) {
                return new Node(this.row + move.row, this.col + move.col);
            }

            public Node copy() {
                return new Node(this.row, this.col);
            }

            public boolean at(Node node) {
                return this.row == node.row && this.col == node.col;
            }
        }
    }

    public static Builder<Day14Task> builder(List<StringBuilder> input) {
        return new Builder<>(() -> new Day14Task(input));
    }
}
