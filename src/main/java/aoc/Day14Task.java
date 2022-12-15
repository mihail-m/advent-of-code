package aoc;

import java.util.List;
import java.util.function.Predicate;

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
                task.result = findSand(task.input, false);
            }
        },

        FIND_UNITS_OF_SAND_WITH_FLOOR {
            @Override
            public void solve(Day14Task task) {
                task.result = findSand(task.input, true);
            }
        };

        private static final Node SAND_HOLE = new Node(0, 500);

        private static int findSand(List<StringBuilder> cave, boolean whenNotOnMap) {
            int ans = 0;

            while (true) {
                boolean onMap = true;

                Node prev = null;
                Node cur = new Node(SAND_HOLE.row, SAND_HOLE.col);

                while (!cur.equals(prev)) {
                    prev = cur;

                    for (Node.Move move : Node.Move.values()) {
                        Node newNode = cur.doMove(move);

                        if (!getInBoundsPredicate(cave).test(newNode)) {
                            onMap = whenNotOnMap;
                            if (!onMap) {
                                break;
                            }
                            continue;
                        }
                        if (cave.get(newNode.row).charAt(newNode.col) != '.') {
                            continue;
                        }

                        cur = newNode;
                        break;
                    }
                }

                if (onMap) {
                    ans++;
                    cave.get(cur.row).setCharAt(cur.col, 'o');
                } else {
                    break;
                }

                if (cur.col == SAND_HOLE.col && cur.row == SAND_HOLE.row) {
                    break;
                }
            }

            return ans;
        }

        private static Predicate<Node> getInBoundsPredicate(List<StringBuilder> map) {
            return (node) -> node.row >= 0
                    && node.col >= 0
                    && node.row < map.size()
                    && node.col < map.get(0).length();
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
        }
    }

    public static Builder<Day14Task> builder(List<StringBuilder> input) {
        return new Builder<>(() -> new Day14Task(input));
    }
}
