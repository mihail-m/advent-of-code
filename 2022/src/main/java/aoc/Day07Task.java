package aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.base.Task;

public class Day07Task extends Task<List<String>, Long> {

    private Day07Task(List<String> input) {
        super(input);
        this.result = 0L;
    }

    public enum Solution implements SolutionStrategy<Day07Task> {

        FIND_DIRECTORIES_UNDER_100000_SIZE_SUM {
            @Override
            public void solve(Day07Task task) {
                buildFileSystemTree(task.input)
                        .forEach((__, dir) -> {
                            if (dir.size <= 100000L) {
                                task.result += dir.size;
                            }
                        });
            }
        },

        FIND_DIRECTORY_TO_DELETE {
            @Override
            public void solve(Day07Task task) {
                Map<String, Dir> fileSystem = buildFileSystemTree(task.input);

                long totalTakenSpace = fileSystem.get(ROOT_DIR).size;

                task.result = totalTakenSpace;
                fileSystem.forEach((__, dir) -> {
                    if (SYSTEM_SIZE - (totalTakenSpace - dir.size) >= NEEDED_SPACE) {
                        task.result = Math.min(task.result, dir.size);
                    }
                });
            }
        };

        private static final String ROOT_DIR = "/";

        private static final char COMMAND_SYMBOL = '$';

        private static final String CD_COMMAND = "cd";

        private static final String BACK = "..";

        private static final long SYSTEM_SIZE = 70000000;

        private static final long NEEDED_SPACE = 30000000;

        private static Map<String, Dir> buildFileSystemTree(List<String> consoleOutput) {
            Map<String, Dir> fileSystem = new HashMap<>();

            Dir currentDir = new Dir(ROOT_DIR, ROOT_DIR);
            fileSystem.put(ROOT_DIR, currentDir);

            List<String> content = new ArrayList<>();
            for (String line : consoleOutput) {
                if (line.charAt(0) == COMMAND_SYMBOL) {
                    currentDir.addAndClearContent(content);

                    String[] info = line.split(" ");
                    if (info[1].equals(CD_COMMAND)) {
                        currentDir = doCD(currentDir, info[2], fileSystem);
                    }
                } else {
                    content.add(line);
                }
            }
            currentDir.addAndClearContent(content);

            addSubDirectoriesSizes(fileSystem.get(ROOT_DIR), fileSystem);

            return fileSystem;
        }

        private static Dir doCD(Dir curDir, String cdDirName, Map<String, Dir> fileSystem) {
            if (cdDirName.equals(BACK)) {
                return fileSystem.get(curDir.parent);
            } else {
                String newDirName = getDirName(curDir.name, cdDirName);
                if (!fileSystem.containsKey(newDirName)) {
                    fileSystem.put(newDirName, new Dir(newDirName, curDir.name));
                }
                return fileSystem.get(newDirName);
            }
        }

        private static void addSubDirectoriesSizes(Dir curDir, Map<String, Dir> fileSystem) {
            for (String subDir : curDir.subDirectories) {
                addSubDirectoriesSizes(fileSystem.get(subDir), fileSystem);
                curDir.size += fileSystem.get(subDir).size;
            }
        }

        private static String getDirName(String parentName, String dirName) {
            if (parentName.equals(ROOT_DIR)) {
                if (dirName.equals(ROOT_DIR)) {
                    return ROOT_DIR;
                }
                return parentName + dirName;
            }
            return parentName + "/" + dirName;
        }

        private static class Dir {

            private static final String DIR = "dir";

            public String parent;

            public String name;
            public long size;

            public Set<String> files;
            public Set<String> subDirectories;

            public Dir(String name, String parent) {
                this.parent = parent;

                this.name = name;
                this.size = 0;

                this.files = new HashSet<>();
                this.subDirectories = new HashSet<>();
            }

            public void addAndClearContent(List<String> content) {
                for (String item : content) {
                    String[] info = item.split(" ");
                    if (DIR.equals(info[0])) {
                        this.subDirectories.add(getDirName(this.name, info[1]));
                    } else if (!this.files.contains(info[1])){
                        this.files.add(info[1]);
                        this.size += Integer.parseInt(info[0]);
                    }
                }
                content.clear();
            }
        }
    }

    public static Builder<Day07Task> builder(List<String> consoleOutput) {
        return new Builder<>(() -> new Day07Task(consoleOutput));
    }
}
