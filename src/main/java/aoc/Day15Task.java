package aoc;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import aoc.base.Task;

public class Day15Task extends Task<List<Day15Task.Sensor>, Long> {

    private final int targetY;

    private Day15Task(List<Sensor> input, int targetY) {
        super(input);
        this.result = 0L;
        this.targetY = targetY;
    }

    public enum Solution implements SolutionStrategy<Day15Task> {

        FIND_NON_COVERED_CELLS_ROW {
            @Override
            public void solve(Day15Task task) {
                task.result = getBlockedCells(getIntervals(task)) - getBeaconsOn(task);
            }

            private static List<Integer[]> getIntervals(Day15Task task) {
                return task.input.stream()
                        .filter(s -> (s.range - Math.abs(s.y - task.targetY)) < 0)
                        .map(s -> {
                            int range = s.range - Math.abs(s.y - task.targetY);
                            return new Integer[]{s.x - range, s.x + range};
                        })
                        .sorted(Comparator.comparingInt(i -> i[0]))
                        .collect(Collectors.toList());
            }

            private static long getBeaconsOn(Day15Task task) {
                return task.input.stream()
                        .filter(s -> s.beaconY == task.targetY)
                        .map(s -> s.beaconX)
                        .distinct()
                        .count();
            }

            private static long getBlockedCells(List<Integer[]> intervals) {
                AtomicInteger to = new AtomicInteger(intervals.get(0)[0] - 1);
                return intervals.stream()
                        .mapToInt(interval -> to.get() < interval[1]
                                ? (interval[1] - Math.max(to.getAndSet(interval[1]), interval[0] + 1))
                                : 0)
                        .sum();
            }
        },

        FIND_ONLY_POSSIBLE_BEACON_POSITION {
            @Override
            public void solve(Day15Task task) {
                for (Sensor sensor : task.input) {
                    for (int step = 0; step <= sensor.range + 1; step++) {
                        if (check(task, sensor, step)) {
                            return;
                        }
                    }
                }
            }

            private static boolean check(Day15Task task, Sensor sensor, int step) {
                return     check(task, sensor.x + sensor.range - step + 1, sensor.y + step)
                        || check(task, sensor.x + sensor.range - step + 1, sensor.y - step)
                        || check(task, sensor.x - sensor.range + step - 1, sensor.y + step)
                        || check(task, sensor.x - sensor.range + step - 1, sensor.y - step);
            }

            private static boolean check(Day15Task task, int x, int y) {
                if (x < 0 || y < 0 || x > task.targetY * 2 || y > task.targetY * 2) {
                    return false;
                }
                if (task.input.stream().anyMatch(s -> s.dist(x, y) <= s.range)) {
                    return false;
                }
                task.result = ((long) x) * 4_000_000L + y;
                return true;
            }
        }
    }

    public static class Sensor {
        int x, y;
        int beaconX, beaconY;
        int range;

        public Sensor(int x, int y, int beaconX, int beaconY) {
            this.x = x;
            this.y = y;
            this.beaconX = beaconX;
            this.beaconY = beaconY;
            this.range = Math.abs(x - beaconX) + Math.abs(y - beaconY);
        }

        public int dist(int x, int y) {
            return Math.abs(this.x - x) + Math.abs(this.y - y);
        }
    }

    public static Builder<Day15Task> builder(List<Sensor> input, int targetY) {
        return new Builder<>(() -> new Day15Task(input, targetY));
    }
}
