package aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.base.Task;

public class Day19Task extends Task<List<Day19Task.Blueprint>, Long> {

    private Day19Task(List<Blueprint> input) {
        super(input);
        this.result = 0L;
    }

    public enum Solution implements SolutionStrategy<Day19Task> {

        FIND_QUALITY_LEVEL_SUM {
            @Override
            public void solve(Day19Task task) {
                for (int i = 0; i < task.input.size(); i++) {
                    long timer = System.currentTimeMillis();

                    Blueprint blueprint = task.input.get(i);
                    DPState start = new DPState(0, 0, 0, 0, 1, 0, 0, 0);
                    Map<String, Long> mem = new HashMap<>();
                    long result = ((long) i + 1) * dpRec(start, 24, blueprint, mem, 0);
                    task.result += result;

                    System.out.printf("Result for blueprint %s is: %s (took %s millis to calculate)%n",
                            i + 1, result, System.currentTimeMillis() - timer);
                }
            }
        },

        FIND_FIRST_3_BLUEPRINTS_PRODUCT {
            @Override
            public void solve(Day19Task task) {
                task.result = 1L;
                for (int i = 0; i < Math.min(task.input.size(), 3); i++) {
                    long timer = System.currentTimeMillis();

                    Blueprint blueprint = task.input.get(i);
                    DPState start = new DPState(0, 0, 0, 0, 1, 0, 0, 0);
                    Map<String, Long> mem = new HashMap<>();
                    long result = dpRec(start, 32, blueprint, mem, 0);
                    task.result *= result;

                    System.out.printf("Result for blueprint %s is: %s (took %s millis to calculate)%n",
                            i + 1, result, System.currentTimeMillis() - timer);
                }
            }
        };

        private static long dpRec(DPState state, int timeLeft, Blueprint blueprint, Map<String, Long> mem, int d) {
            if (mem.containsKey(state.encode(timeLeft))) {
                return mem.get(state.encode(timeLeft));
            }

            if (timeLeft == 0) {
                mem.put(state.encode(timeLeft), (long) state.geode);
                return state.geode;
            }

            if (d > 25 && state.geode == 0) {
                return 0;
            }

            long result = 0;

            if (state.ore >= blueprint.geodeRobotOreCost && state.obsidian >= blueprint.geodeRobotObsidianCost) {
                DPState next = state.nextMinute()
                        .createGeodeRobot(blueprint.geodeRobotOreCost, blueprint.geodeRobotObsidianCost);
                result = Math.max(result, dpRec(next, timeLeft - 1, blueprint, mem, d + 1));
            } else {
                if (state.ore >= blueprint.obsidianRobotOreCost
                        && state.clay >= blueprint.obsidianRobotClayCost
                        && state.obsidianRobots < blueprint.obsidianLimit) {
                    DPState next = state.nextMinute()
                            .createObsidianRobot(blueprint.obsidianRobotOreCost, blueprint.obsidianRobotClayCost);
                    result = Math.max(result, dpRec(next, timeLeft - 1, blueprint, mem, d + 1));
                }

                if (state.ore >= blueprint.clayRobotOreCost && state.clayRobots < blueprint.clayLimit) {
                    DPState next = state.nextMinute().createClayRobot(blueprint.clayRobotOreCost);
                    result = Math.max(result, dpRec(next, timeLeft - 1, blueprint, mem, d + 1));
                }

                if (state.ore >= blueprint.oreRobotOreCost && state.oreRobots < blueprint.oreLimit) {
                    DPState next = state.nextMinute().createOreRobot(blueprint.oreRobotOreCost);
                    result = Math.max(result, dpRec(next, timeLeft - 1, blueprint, mem, d + 1));
                }

                result = Math.max(result, dpRec(state.nextMinute(), timeLeft - 1, blueprint, mem, d + 1));
            }

            mem.put(state.encode(timeLeft), result);
            return result;
        }

        private static class DPState {

            public int ore;
            public int clay;
            public int obsidian;
            public int geode;
            public int oreRobots;
            public int clayRobots;
            public int obsidianRobots;
            public int geodeRobots;

            public DPState(int ore, int clay, int obsidian, int geode,
                    int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots) {
                this.ore = ore;
                this.clay = clay;
                this.obsidian = obsidian;
                this.geode = geode;
                this.oreRobots = oreRobots;
                this.clayRobots = clayRobots;
                this.obsidianRobots = obsidianRobots;
                this.geodeRobots = geodeRobots;
            }

            public DPState nextMinute() {
                return new DPState(this.ore + this.oreRobots, this.clay + this.clayRobots,
                        this.obsidian + this.obsidianRobots, this.geode + this.geodeRobots,
                        this.oreRobots, this.clayRobots, this.obsidianRobots, this.geodeRobots);
            }

            public DPState createOreRobot(int oreCost) {
                return new DPState(this.ore - oreCost, this.clay, this.obsidian, this.geode,
                        this.oreRobots + 1, this.clayRobots, this.obsidianRobots, this.geodeRobots);
            }

            public DPState createClayRobot(int oreCost) {
                return new DPState(this.ore - oreCost, this.clay, this.obsidian, this.geode,
                        this.oreRobots, this.clayRobots + 1, this.obsidianRobots, this.geodeRobots);
            }

            public DPState createObsidianRobot(int oreCost, int clayCost) {
                return new DPState(this.ore - oreCost, this.clay - clayCost, this.obsidian, this.geode,
                        this.oreRobots, this.clayRobots, this.obsidianRobots + 1, this.geodeRobots);
            }

            public DPState createGeodeRobot(int oreCost, int obsidianCost) {
                return new DPState(this.ore - oreCost, this.clay, this.obsidian - obsidianCost, this.geode,
                        this.oreRobots, this.clayRobots, this.obsidianRobots, this.geodeRobots + 1);
            }

            public String encode(int timeLeft) {
                return timeLeft + "_" + this.ore + "_" + this.clay + "_" + this.obsidian + "_"
                        + this.oreRobots + "_" + this.clayRobots + "_" + this.obsidianRobots + "_" + this.geodeRobots;
            }
        }
    }

    public static class Blueprint {

        public int oreRobotOreCost;
        public int clayRobotOreCost;
        public int obsidianRobotOreCost;
        public int obsidianRobotClayCost;
        public int geodeRobotOreCost;
        public int geodeRobotObsidianCost;

        public int oreLimit;
        public int clayLimit;
        public int obsidianLimit;

        public Blueprint(int oreRobotOreCost, int clayRobotOreCost, int obsidianRobotOreCost,
                int obsidianRobotClayCost, int geodeRobotOreCost, int geodeRobotObsidianCost) {
            this.oreRobotOreCost = oreRobotOreCost;
            this.clayRobotOreCost = clayRobotOreCost;
            this.obsidianRobotOreCost = obsidianRobotOreCost;
            this.obsidianRobotClayCost = obsidianRobotClayCost;
            this.geodeRobotOreCost = geodeRobotOreCost;
            this.geodeRobotObsidianCost = geodeRobotObsidianCost;

            this.oreLimit = oreRobotOreCost;
            this.oreLimit = Math.max(oreLimit, clayRobotOreCost);
            this.oreLimit = Math.max(oreLimit, obsidianRobotOreCost);
            this.oreLimit = Math.max(oreLimit, geodeRobotOreCost);
            this.clayLimit = obsidianRobotClayCost;
            this.obsidianLimit = this.geodeRobotObsidianCost;
        }
    }

    public static Builder<Day19Task> builder(List<Blueprint> input) {
        return new Builder<>(() -> new Day19Task(input));
    }
}
