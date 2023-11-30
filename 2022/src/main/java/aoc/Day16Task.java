package aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import aoc.base.Task;

public class Day16Task extends Task<Map<String, Day16Task.Node>, Long> {

    private Day16Task(Map<String, Node> input) {
        super(input);
        this.result = 0L;
    }

    public enum Solution implements SolutionStrategy<Day16Task> {

        FIND_MAX_PRESSURE {
            @Override
            public void solve(Day16Task task) {
                Map<String, Map<Integer, Map<String, Pressure>>> dp = new HashMap<>();
                for (Node node : task.input.values()) {
                    dp.put(node.name, new HashMap<>());
                    dp.get(node.name).put(0, Map.of("", new Pressure(0, "")));
                    dp.get(node.name).put(1, Map.of("", new Pressure(0, "")));
                }

                for (int timeLeft = 2; timeLeft <= 30; timeLeft++) {
                    System.out.print("Iteration for timeLeft = " + timeLeft + " Iteration time: ");
                    long timer = System.currentTimeMillis();
                    for (Node node : task.input.values()) {

                        if (!dp.get(node.name).containsKey(timeLeft)) {
                            dp.get(node.name).put(timeLeft, new HashMap<>());
                        }

                        for (String nextNodeName : node.tunnels) {
                            int finalTimeLeft = timeLeft;

                            dp.get(nextNodeName).get(timeLeft - 1).forEach((name, pressure) -> {
                                Pressure newPressure = pressure.open(0, "");
                                if (dp.get(node.name).get(finalTimeLeft).containsKey(newPressure.opened)) {
                                    dp.get(node.name).get(finalTimeLeft).get(newPressure.opened).setValue(newPressure.value);
                                } else {
                                    dp.get(node.name).get(finalTimeLeft).put(newPressure.opened, newPressure);
                                }
                            });

                            if (node.pressure == 0) {
                                continue;
                            }

                            dp.get(nextNodeName).get(timeLeft - 2).forEach((name, pressure) -> {
                                if (pressure.opened.contains(node.name)) {
                                    return;
                                }
                                Pressure newPressure = pressure.open((finalTimeLeft - 1) * node.pressure, node.name);
                                if (dp.get(node.name).get(finalTimeLeft).containsKey(newPressure.opened)) {
                                    dp.get(node.name).get(finalTimeLeft).get(newPressure.opened).setValue(newPressure.value);
                                } else {
                                    dp.get(node.name).get(finalTimeLeft).put(newPressure.opened, newPressure);
                                }
                            });
                        }
                    }
                    System.out.println(System.currentTimeMillis() - timer + " millis.");
                }

                task.result = dp.get(START)
                        .get(30)
                        .values()
                        .stream()
                        .mapToLong(p -> p.value)
                        .max().orElse(0L);
            }
        },

        FIND_MAX_PRESSURE_WITH_ELEPHANT {
            @Override
            public void solve(Day16Task task) {
                Map<String, Integer> mapping = new HashMap<>();
                AtomicInteger num = new AtomicInteger(0);
                task.input.keySet().stream().sorted().forEach(key -> mapping.put(key, num.getAndIncrement()));

                int[][] dist = new int[task.input.size()][task.input.size()];
                for (int i = 0; i < task.input.size(); i++) {
                    for (int j = 0; j < task.input.size(); j++) {
                        dist[i][j] = Integer.MAX_VALUE;
                    }
                    dist[i][i] = 0;
                }
                task.input.forEach((n1, node) -> {
                    node.tunnels.forEach(n2 -> {
                        dist[mapping.get(n1)][mapping.get(n2)] = 1;
                        dist[mapping.get(n2)][mapping.get(n1)] = 1;
                    });
                });
                for (int k = 0; k < task.input.size(); k++) {
                    for (int i = 0; i < task.input.size(); i++) {
                        for (int j = 0; j < task.input.size(); j++) {
                            if (dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE) {
                                continue;
                            }
                            dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                        }
                    }
                }

                Set<String> meaningfulValves = task.input.values()
                        .stream()
                        .filter(node -> node.pressure > 0)
                        .map(node -> node.name)
                        .collect(Collectors.toSet());

                Map<String, Long> mem = new HashMap<>();
                task.result = dfsMe(START, 26, meaningfulValves, dist, mapping, task.input, mem);
            }

            private long dfsElephant(String node, long timeLeft, Set<String> valvesLeft,
                                     int[][] dist, Map<String, Integer> mapping, Map<String, Node> graph) {

                long bestResult = 0;

                for (String valve : valvesLeft) {
                    int distToValve = dist[mapping.get(node)][mapping.get(valve)];

                    if (distToValve >= timeLeft) {
                        continue;
                    }

                    long currentResult = graph.get(valve).pressure * (timeLeft - distToValve - 1);

                    currentResult += dfsElephant(valve,
                            timeLeft - distToValve - 1,
                            valvesLeft.stream()
                                    .filter(v -> !v.equals(valve))
                                    .collect(Collectors.toSet()),
                            dist, mapping, graph);

                    bestResult = Math.max(bestResult, currentResult);
                }

                return bestResult;
            }

            private long dfsMe(String node, long timeLeft, Set<String> valvesLeft, int[][] dist,
                               Map<String, Integer> mapping, Map<String, Node> graph, Map<String, Long> mem) {

                long bestResult = 0;

                for (String valve : valvesLeft) {
                    int distToValve = dist[mapping.get(node)][mapping.get(valve)];

                    if (distToValve >= timeLeft) {
                        continue;
                    }

                    long currentResult = graph.get(valve).pressure * (timeLeft - distToValve - 1);

                    currentResult += dfsMe(valve,
                            timeLeft - distToValve - 1,
                            valvesLeft.stream()
                                    .filter(v -> !v.equals(valve))
                                    .collect(Collectors.toSet()),
                            dist, mapping, graph, mem);

                    bestResult = Math.max(bestResult, currentResult);

                }

                String memKey = valvesLeft.stream().sorted().collect(Collectors.joining("_"));
                if (!mem.containsKey(memKey)) {
                    mem.put(memKey, dfsElephant(START, 26, valvesLeft, dist, mapping, graph));
                }
                return Math.max(bestResult, mem.get(memKey));
            }
        };

        private static final String START = "AA";

        private static class Pressure {

            long value;
            String opened;

            public Pressure(long value, String opened) {
                this.value = value;
                this.opened = opened;
            }

            public Pressure open(long value, String... names) {
                Set<String> s = Arrays.stream(opened.split("_")).collect(Collectors.toSet());
                s.addAll(Arrays.asList(names));
                return new Pressure(this.value + value, s.stream()
                        .filter(str -> !str.isEmpty() && !str.isBlank())
                        .sorted()
                        .collect(Collectors.joining("_")));
            }

            public void setValue(long value) {
                this.value = Math.max(this.value, value);
            }
        }
    }

    public static class Node {

        public String name;
        public long pressure;
        public List<String> tunnels;

        public Node(String name, int pressure, List<String> tunnels) {
            this.name = name;
            this.pressure = pressure;
            this.tunnels = tunnels;
        }
    }

    public static Builder<Day16Task> builder(Map<String, Node> input) {
        return new Builder<>(() -> new Day16Task(input));
    }
}
