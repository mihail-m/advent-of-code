package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import aoc.base.Task;

public class Day13Task extends Task<List<String>, Integer> {

    private Day13Task(List<String> input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day13Task> {

        FIND_PAIRS_IN_ORDER {
            @Override
            public void solve(Day13Task task) {
                int pairIndex = 1;
                for (int i = 0; i < task.input.size(); i += 3) {
                    Object left = buildList(task.input.get(i), new AtomicInteger(1));
                    Object right = buildList(task.input.get(i + 1), new AtomicInteger(1));

                    task.result += check(left, right) < 0 ? pairIndex : 0;

                    pairIndex++;
                }
            }
        },

        FIND_DIVIDER_PACKETS {
            @Override
            public void solve(Day13Task task) {
                List<Object> packets = new ArrayList<>();

                Object div1Obj = buildList(DIVIDER_1, new AtomicInteger(1));
                packets.add(div1Obj);

                Object div2Obj = buildList(DIVIDER_2, new AtomicInteger(1));
                packets.add(div2Obj);

                for (String line : task.input) {
                    if (!line.isEmpty()) {
                        packets.add(buildList(line, new AtomicInteger(1)));
                    }
                }

                packets.sort(Solution::check);

                int div1Index = 0;
                int div2Index = 0;

                for (int i = 0; i < packets.size(); i++) {
                    if (packets.get(i) == div1Obj) {
                        div1Index = i + 1;
                    }
                    if (packets.get(i) == div2Obj) {
                        div2Index = i + 1;
                    }
                }

                task.result = div1Index * div2Index;
            }
        };

        private static final String DIVIDER_1 = "[[2]]";
        private static final String DIVIDER_2 = "[[6]]";

        Object buildList(String input, AtomicInteger i) {
            List<Object> list = new ArrayList<>();

            while (i.get() < input.length()) {
                char c = input.charAt(i.getAndIncrement());
                if (c == '[') {
                    Object o = buildList(input, i);
                    list.add(o);
                }
                else if (c == ']') {
                    break;
                }
                else if (c != ',') {
                    int num = nextInt(input, i.get() - 1);
                    list.add(num);
                    i.addAndGet(String.valueOf(num).length() - 1);
                }
            }

            return list;
        }

        private static int nextInt(String input, int i) {
            int num = 0;
            while (i < input.length() && Character.isDigit(input.charAt(i))) {
                num = num * 10 + (input.charAt(i) - '0');
                i++;
            }
            return num;
        }

        @SuppressWarnings("unchecked")
        private static int check(Object l1, Object l2) {
            if (l1 instanceof Integer) {
                if (l2 instanceof Integer) {
                    return ((Integer) l1).compareTo((Integer) l2);
                }
                return check(List.of(l1), l2);
            }

            if (l2 instanceof Integer) {
                return check(l1, List.of(l2));
            }

            int i = 0;
            int j = 0;

            List<Object> left = (List<Object>) l1;
            List<Object> right = (List<Object>) l2;

            while (i < left.size() && j < right.size()) {
                int checkVal = check(left.get(i++), right.get(j++));
                if (checkVal == 0) {
                    continue;
                }
                return checkVal;
            }

            return Integer.compare(left.size(), right.size());
        }
    }

    public static Builder<Day13Task> builder(List<String> input) {
        return new Builder<>(() -> new Day13Task(input));
    }
}
