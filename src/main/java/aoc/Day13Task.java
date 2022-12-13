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
                    PacketData left = new PacketData(task.input.get(i));
                    PacketData right = new PacketData(task.input.get(i + 1));

                    task.result += left.compareTo(right) < 0 ? pairIndex : 0;

                    pairIndex++;
                }
            }
        },

        FIND_DIVIDER_PACKETS {
            @Override
            public void solve(Day13Task task) {
                List<PacketData> packets = new ArrayList<>();
                packets.add(DIVIDER_1);
                packets.add(DIVIDER_2);

                task.input.stream()
                        .filter(line -> !line.isEmpty())
                        .forEach(line -> packets.add(new PacketData(line)));

                packets.sort(PacketData::compareTo);

                task.result = (packets.indexOf(DIVIDER_1) + 1) * (packets.indexOf(DIVIDER_2) + 1);
            }
        };

        private static final PacketData DIVIDER_1 = new PacketData("[[2]]");
        private static final PacketData DIVIDER_2 = new PacketData("[[6]]");

        private static class PacketData implements Comparable<PacketData> {

            private final Object packetData;

            public PacketData(String input) {
                this.packetData = buildList(input, new AtomicInteger(1));
            }

            @Override
            public int compareTo(PacketData pd) {
                return compare(this.packetData, pd.packetData);
            }

            private Object buildList(String input, AtomicInteger i) {
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

            private int nextInt(String input, int i) {
                int num = 0;
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    num = num * 10 + (input.charAt(i) - '0');
                    i++;
                }
                return num;
            }

            @SuppressWarnings("unchecked")
            private int compare(Object l1, Object l2) {
                if (l1 instanceof Integer) {
                    if (l2 instanceof Integer) {
                        return ((Integer) l1).compareTo((Integer) l2);
                    }
                    return compare(List.of(l1), l2);
                }

                if (l2 instanceof Integer) {
                    return compare(l1, List.of(l2));
                }

                int i = 0;
                int j = 0;

                List<Object> left = (List<Object>) l1;
                List<Object> right = (List<Object>) l2;

                while (i < left.size() && j < right.size()) {
                    int checkVal = compare(left.get(i++), right.get(j++));
                    if (checkVal == 0) {
                        continue;
                    }
                    return checkVal;
                }

                return Integer.compare(left.size(), right.size());
            }
        }
    }

    public static Builder<Day13Task> builder(List<String> input) {
        return new Builder<>(() -> new Day13Task(input));
    }
}
