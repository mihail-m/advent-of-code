package aoc;

import java.util.ArrayList;
import java.util.List;

import aoc.base.Task;

public class Day20Task extends Task<List<Long>, Long> {

    private Day20Task(List<Long> input) {
        super(input);
        this.result = 0L;
    }

    public enum Solution implements SolutionStrategy<Day20Task> {

        FIND_GROVE_COORDINATES_SUM {
            @Override
            public void solve(Day20Task task) {
                List<Element> list = getElementList(task.input);
                mix(list);
                task.result = getCoordinates(list);
            }
        },

        FIND_GROVE_COORDINATES_SUM_WITH_KEY {

            private static final long DECRYPTION_KEY = 811589153;

            private static final long MIX_TIMES = 10;

            @Override
            public void solve(Day20Task task) {
                task.input.replaceAll(num -> num * DECRYPTION_KEY);
                List<Element> list = getElementList(task.input);
                for (int rep = 0; rep < MIX_TIMES; rep++) {
                    mix(list);
                }
                task.result = getCoordinates(list);
            }
        };

        static class Element {
            final long num;
            final int index;
            public Element(long n, int i) {
                this.num = n;
                this.index = i;
            }
        }

        static void mix(List<Element> list) {
            int i = 0;
            int curEl = 0;
            while (curEl < list.size()) {
                while (list.get(i).index != curEl) {
                    i = (i + 1) % list.size();
                }
                Element el = list.remove(i);
                int newPos = (int) ((i + el.num) % list.size());
                if (newPos < 0) {
                    newPos = list.size() + newPos;
                }
                list.add(newPos, el);
                curEl++;
            }
        }

        static long getCoordinates(List<Element> list) {
            int zeroPos = 0;
            for (; zeroPos < list.size(); zeroPos++) {
                if (list.get(zeroPos).num == 0) {
                    break;
                }
            }

            return list.get((zeroPos + 1000) % list.size()).num
                    + list.get((zeroPos + 2000) % list.size()).num
                    + list.get((zeroPos + 3000) % list.size()).num;
        }

        static List<Element> getElementList(List<Long> input) {
            List<Element> list = new ArrayList<>();
            for (int i = 0; i < input.size(); i++) {
                list.add(new Element(input.get(i), i));
            }
            return list;
        }
    }

    public static Builder<Day20Task> builder(List<Long> input) {
        return new Builder<>(() -> new Day20Task(input));
    }
}
