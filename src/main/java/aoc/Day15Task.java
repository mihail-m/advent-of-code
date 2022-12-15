package aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import aoc.base.Task;

public class Day15Task extends Task<List<Day15Task.Sensor>, Long> {

    private final int targetY;

    private Day15Task(List<Sensor> input, int targetY) {
        super(input);
        this.result = 0L;
        this.targetY = targetY;
    }

    public enum Solution implements SolutionStrategy<Day15Task> {

        FIND_NON_COVERED_CELLS_ROW_2000000 {
            @Override
            public void solve(Day15Task task) {
                List<Integer[]> intervals = new ArrayList<>();
                Set<Integer> beacons = new HashSet<>();

                task.input.forEach(sensor -> {
                    if (sensor.beaconY == task.targetY) {
                        beacons.add(sensor.beaconX);
                    }

                    int rowRange = (sensor.range - Math.abs(sensor.y - task.targetY));

                    if (rowRange < 0) {
                        return;
                    }

                    int from = sensor.x - rowRange;
                    int to = sensor.x + rowRange;

                    intervals.add(new Integer[]{from, to});
                });

                intervals.sort((i1, i2) -> {
                    if (Objects.equals(i1[0], i2[0])) {
                        return Integer.compare(i1[1], i2[1]);
                    }
                    return Integer.compare(i1[0], i2[0]);
                });

                int to = intervals.get(0)[0] - 1;
                for (Integer[] interval : intervals) {
                    if (to >= interval[1]) {
                        continue;
                    }

                    if (interval[0] > to) {
                        task.result += (interval[1] - interval[0] + 1);
                    } else {
                        task.result += interval[1] - to;
                    }

                    to = interval[1];
                }

                task.result -= beacons.size();
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

            private static boolean check(Day15Task task, Sensor sensor, int range, int step) {
                int x = sensor.x + range;
                int y = sensor.y + step;
                if (x < 0 || y < 0 || x > task.targetY * 2 || y > task.targetY * 2) {
                    return false;
                }
                if (task.input.stream().noneMatch(s -> s.dist(x, y) <= s.range)) {
                    System.out.println(x + " " + y);
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
            //System.out.println(x+" "+y+" "+beaconX+" "+beaconY);
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
