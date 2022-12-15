package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                List<Integer[]> intervals = getIntervals(task.input, task.targetY);

                intervals.sort((i1, i2) -> {
                    if (Objects.equals(i1[0], i2[0])) {
                        return Integer.compare(i1[1], i2[1]);
                    }
                    return Integer.compare(i1[0], i2[0]);
                });

                task.result = getBlockedCells(intervals) - getBeaconsOn(task.input, task.targetY);
            }

            private static List<Integer[]> getIntervals(List<Sensor> sensors, int targetY) {
                List<Integer[]> intervals = new ArrayList<>();

                sensors.forEach(sensor -> {
                    int rowRange = (sensor.range - Math.abs(sensor.y - targetY));

                    if (rowRange < 0) {
                        return;
                    }

                    int from = sensor.x - rowRange;
                    int to = sensor.x + rowRange;

                    intervals.add(new Integer[]{from, to});
                });

                return intervals;
            }

            private static long getBeaconsOn(List<Sensor> sensors, int targetY) {
                return sensors.stream()
                        .filter(s -> s.beaconY == targetY)
                        .map(s -> s.beaconX)
                        .distinct()
                        .count();
            }

            private static long getBlockedCells(List<Integer[]> intervals) {
                long result = 0L;
                int to = intervals.get(0)[0] - 1;

                for (Integer[] interval : intervals) {
                    if (to >= interval[1]) {
                        continue;
                    }

                    if (interval[0] > to) {
                        result += (interval[1] - interval[0] + 1);
                    } else {
                        result += interval[1] - to;
                    }

                    to = interval[1];
                }

                return result;
            }
        },

        FIND_ONLY_POSSIBLE_BEACON_POSITION {
            @Override
            public void solve(Day15Task task) {
                for (Sensor sensor : task.input) {
                    if (check(task, sensor, sensor.range + 1,0)
                            || check(task, sensor, -sensor.range - 1,0)
                            || check(task, sensor, 0,sensor.range + 1)
                            || check(task, sensor, 0,-sensor.range - 1)) {
                         return;
                    }
                    for (int step = 1; step <= sensor.range; step++) {
                        if (check(task, sensor, sensor.range - step + 1, step)
                                || check(task, sensor, sensor.range - step + 1, -step)
                                || check(task, sensor, -sensor.range + step - 1, step)
                                || check(task, sensor, -sensor.range + step -1, -step)) {
                            return;
                        }
                    }
                }
            }

            private static boolean check(Day15Task task, Sensor sensor, int addX, int addY) {
                int x = sensor.x + addX;
                int y = sensor.y + addY;

                if (x < 0 || y < 0 || x > task.targetY * 2 || y > task.targetY * 2) {
                    return false;
                }

                if (task.input.stream().noneMatch(s -> s.dist(x, y) <= s.range)) {
                    task.result = ((long) x) * 4_000_000L + y;
                    return true;
                }

                return false;
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
